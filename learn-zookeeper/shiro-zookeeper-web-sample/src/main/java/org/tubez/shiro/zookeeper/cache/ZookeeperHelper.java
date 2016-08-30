package org.tubez.shiro.zookeeper.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.lang.SerializationUtils;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperHelper {
	private static final Logger LOG = LoggerFactory.getLogger(ZookeeperHelper.class);
	private ZooKeeper zookeeperClient;
	private final String zookeeperBasePath;

	public ZookeeperHelper(ZooKeeper zookeeperClient, String path) {
		this.zookeeperClient = zookeeperClient;
		zookeeperBasePath = path;
	}

	public long getSessionId() {
		return zookeeperClient.getSessionId();
	}

	public Object get(String key) throws CacheException {
		LOG.debug("Getting object from cache {} for key {}", getSessionId(), key);
		if (key == null) {
			LOG.debug("Invalid key, abort");
			return null;
		}
		if (!isCacheExist(key)) {
			LOG.debug("Couldnt find the path, abort");
			return null;
		}
		return getData(key);
	}

	public Object put(String key, Object value) throws CacheException {
		if (!isBaseDirectoryExist()) {
			LOG.debug("The Zookeeper base dir doesnt exist, creating it.");
			createDirectory(zookeeperBasePath);
		}
		if (!isCacheExist(key)) {
			LOG.debug("The session dir doesnt exist, creating it.");
			createDirectory(getDataPath(key));
		}
		Object previous = getData(key);
		persistData(key, value);
		/**
		 * I am not sure about this, I checked ehCache and they return the
		 * previous entry. I guess this should be okay...
		 */
		return previous;
	}

	public Object remove(String key) throws CacheException {
		if (!isCacheExist(key)) {
			LOG.debug("The session dir doesnt exist, abort.");
			return null;
		}
		try {
			Object previous = getData(key);
			zookeeperClient.delete(getDataPath(key), -1);
			return previous;
		} catch (InterruptedException | KeeperException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return null;
		}
	}

	public void clear() throws CacheException {
		if (!isBaseDirectoryExist()) {
			LOG.debug("The Zookeeper base dir doesnt exist, abort.");
			return;
		}
		try {
			zookeeperClient.delete(zookeeperBasePath, -1);
		} catch (InterruptedException | KeeperException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
		}
	}

	public int size() {
		try {
			List<String> childrens = zookeeperClient.getChildren(zookeeperBasePath, null);
			int size = 0;
			for (String child : childrens) {
				size += zookeeperClient.exists(getDataPath(child), false).getDataLength();
			}
			return size;
		} catch (KeeperException | InterruptedException e) {
			return 0;
		}
	}

	public Set<String> keys() {
		if (!isBaseDirectoryExist()) {
			LOG.debug("The Zookeeper base dir doesnt exist, abort.");
			return Collections.emptySet();
		}

		try {
			List<String> childrens = (List<String>) zookeeperClient.getChildren(zookeeperBasePath, null);
			if (!CollectionUtils.isEmpty(childrens)) {
				return Collections.unmodifiableSet(new LinkedHashSet<String>(childrens));
			} else {
				return Collections.emptySet();
			}
		} catch (InterruptedException | KeeperException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return Collections.emptySet();
		}
	}

	public Collection<Object> values() {
		if (!isBaseDirectoryExist()) {
			LOG.debug("The Zookeeper base dir doesnt exist, abort.");
			return Collections.emptySet();
		}
		try {
			List<String> childrens = (List<String>) zookeeperClient.getChildren(zookeeperBasePath, null);
			if (!CollectionUtils.isEmpty(childrens)) {
				List<Object> values = new ArrayList<Object>(childrens.size());
				for (String key : childrens) {
					Object value = get(key);
					if (value != null) {
						values.add(value);
					}
				}
				return Collections.unmodifiableList(values);
			} else {
				return Collections.emptyList();
			}
		} catch (InterruptedException | KeeperException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	public ZooKeeper getZookeeperClient() {
		return zookeeperClient;
	}

	public String getZookeeperBasePath() {
		return zookeeperBasePath;
	}

	private String getDataPath(String key) {
		StringJoiner joiner = new StringJoiner("/");
		joiner.add(zookeeperBasePath).add(key);
		return joiner.toString();
	}

	private boolean isCacheExist(String key) {
		try {
			return !(zookeeperClient.exists(getDataPath(key), false) == null);
		} catch (KeeperException | InterruptedException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return false;
		}
	}

	private boolean isBaseDirectoryExist() {
		try {
			return !(zookeeperClient.exists(zookeeperBasePath, false) == null);
		} catch (KeeperException | InterruptedException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return false;
		}
	}

	private void createDirectory(String path) {
		try {
			zookeeperClient.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException | InterruptedException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
		}
	}

	private Object getData(String key) {
		try {
			byte[] result = zookeeperClient.getData(getDataPath(key), false, new Stat());
			if (result == null) {
				return null;
			}
			return SerializationUtils.deserialize(result);
		} catch (KeeperException | InterruptedException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
			return null;
		}
	}

	private void persistData(String key, Object value) {
		try {
			zookeeperClient.setData(getDataPath(key), SerializationUtils.serialize((Serializable) value), -1);
		} catch (KeeperException | InterruptedException e) {
			// throw new CacheException(e);
			LOG.error("Error: {}", e.getMessage());
		}
	}

}
