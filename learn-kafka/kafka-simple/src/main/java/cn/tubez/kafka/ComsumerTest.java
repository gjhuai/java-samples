package cn.tubez.kafka;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.tubez.kafka.util.PropertiesUtil;

/**
 * 消费者
 * @author Administrator
 *
 */
public class ComsumerTest {

	public static void main(String[] args) throws Exception {
		KafkaConsumer<String, String> consumer = PropertiesUtil.getConsumer();
		//订阅的主题为test，这个可以同时订阅多个主题，Arrays.asList("test","test2")
		consumer.subscribe(Arrays.asList(PropertiesUtil.TOPIC));
		while (true) {
			//poll()方法是从kafka服务器中获取消息，里面的参数是超时时间，单位是毫秒
			//kafka中如果有消息，则会立即返回，但是如果没有消息，就会一直等待，如果到了超时时间，会直接返回一个空的结果集，如果不需要处理空结果，可以把这个值设置到最大
			ConsumerRecords<String, String> records = consumer.poll(1000);
			
			//这部分就是把kafka中的数据给取出并读出来了，这里可以获取到消息的偏移量
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("接收的分区为: " + record.partition() + ", 偏移量为: " + record.offset()
						+ ", 消息为: " + record.value());
			}
		}
	}
}
