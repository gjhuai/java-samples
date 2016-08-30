/**
 * Copyright 2015. NFLabs, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tubez.shiro.zookeeper.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro Cache implementation that wraps an Zookeeper instance.
 *
 * @author guanjianghuai
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class ZookeeperCache<K, V> implements Cache<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(ZookeeperCache.class);
	private ZookeeperHelper zkHelper;

	public ZookeeperCache(ZookeeperHelper zkHelper) {
		if (zkHelper == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.zkHelper = zkHelper;
	}

	/**
	 * Gets a value of an element which matches the given key.
	 *
	 * @param key
	 *            the key of the element to return.
	 * @return The value placed into the cache with an earlier put, or null if
	 *         not found or expired
	 */

	@Override
	public V get(K key) throws CacheException {
		LOG.debug("Getting object from cache {} for key {}", zkHelper.getSessionId(), key);
		return (V) zkHelper.get((String) key);
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key
	 *            the key.
	 * @param value
	 *            the value.
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		LOG.debug("Putting object from cache {} for key {}", zkHelper.getSessionId(), key);
		return (V) zkHelper.put((String) key, value);
	}

	@Override
	public V remove(K key) throws CacheException {
		LOG.debug("Removing object from cache {} for key {}", zkHelper.getSessionId(), key);
		return (V) zkHelper.remove(key.toString());
	}

	@Override
	public void clear() throws CacheException {
		LOG.debug("Removing object from cache {}", zkHelper.getSessionId());
		zkHelper.clear();
	}

	@Override
	public int size() {
		return zkHelper.size();
	}

	@Override
	public Set<K> keys() {
		return (Set<K>) zkHelper.keys();
	}

	@Override
	public Collection<V> values() {
		return (Collection<V>) zkHelper.values();
	}

}
