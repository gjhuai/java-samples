package cn.tubez.kafka.util;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * 用于获取连接配置<br>
 * 这些配置信息可以在官网查询，因为我这里用的是新的API，与之前的SCALA不一样，所以需要查看新的文档(英文文档，官方的，推荐)<br>
 * 生产者属性配置:
 * <a><http://kafka.apache.org/documentation.html#producerconfigs/a> 消费者属性配置:
 * <a>http://kafka.apache.org/documentation.html#newconsumerconfigs</a>
 * 
 * @author Administrator
 *
 */
public class PropertiesUtil {
	
	private final static String kafkaServer = "192.168.0.207:9092";
	public final static String TOPIC = "test";

	/**
	 * 获取生产者的连接配置
	 * 
	 * @return
	 */
	public static KafkaProducer<String, String> getProducer() {
		KafkaProducer<String, String> kp;
		Properties props = new Properties();
		// 连接的集群，以逗号相隔。其实只写一个也可以，它会自动从zookeeper中寻找集群中的其它服务，但是建议多写几个，避免出现单点故障
		props.put("bootstrap.servers", kafkaServer);
		// 发送完消息是确认模式
		// 0：不进行消息接收确认，即Client端发送完成后不会等待Broker的确认
		// 1：由Leader确认，Leader接收到消息后会立即返回确认信息
		// all：集群完整确认，Leader会等待所有in-sync的follower节点都确认收到消息后，再返回确认信息
		// 我们可以根据消息的重要程度，设置不同的确认模式。默认为1
		props.put("acks", "1");
		// 发送失败时Producer端的重试次数，默认为0，即不重试
		props.put("retries", 0);
		// 当同时有大量消息要向同一个分区发送时，Producer端会将消息打包后进行批量发送。如果设置为0，则每条消息都独立发送。默认为16384字节
		/* props.put("batch.size", 16384); */
		// 发送消息前等待的毫秒数，与batch.size配合使用。在消息负载不高的情况下，配置linger.ms能够让Producer在发送消息前等待一定时间，以积累更多的消息打包发送，达到节省网络资源的目的。默认为0，单位毫秒
		// 它与batch.size中两个只能选择一个
		props.put("linger.ms", 0); 
		// 序列化器
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kp = new KafkaProducer<String, String>(props);
		return kp;
	}

	/**
	 * 获取消费者的连接配置<br>
	 * 部分配置与生产者一样的，就不写注释了
	 * 
	 * @return
	 */
	public static KafkaConsumer<String, String> getConsumer() {
		KafkaConsumer<String, String> kc;
		Properties props = new Properties();
		// 同生产者
		props.put("bootstrap.servers", kafkaServer);
		// 消费组，名字可以随便定义。
		// 注意:同一个group下的多个Consumer不会拉取到重复的消息，不同group下的Consumer则会保证拉取到每一条消息。注意，同一个group下的consumer数量不能超过分区数。
		props.put("group.id", "1");
		// 是否自动提交offset的偏移量，默认为true。也代表着确认数据，所以同一个组的其它消费者就无法再获取该条信息了
		props.put("enable.auto.commit", "true");
		// 自动提交的间隔时间，单位毫秒，默认为5000
		props.put("auto.commit.interval.ms", "1000");
		// 连接超时，单位毫秒
		props.put("session.timeout.ms", "30000");
		// 同生产者，属于序列化的类
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		// 每次获取消息的大小，单位字节，消费者会等待消息到了一定的大小，一次性取出，默认为1，代表一条取一次(没有试过)
		/* props.put("fetch.min.bytes", 1); */
		// 每次从单个分区中拉取的消息最大尺寸（byte），默认为1M
		/* props.put("max.partition.fetch.bytes", 1024 * 1024); */

		kc = new KafkaConsumer<String, String>(props);
		return kc;
	}
}
