package cn.tubez.kafka;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import cn.tubez.kafka.util.PropertiesUtil;

/**
 * 生产者<br>
 * 每隔一秒，向kafka发送一条消息 
 * @author Administrator
 *
 */
public class ProducerTest {
	public static void main(String[] args) throws Exception {
		Producer<String, String> producer = PropertiesUtil.getProducer();
		int i = 0;
		while (true) {
			//第一个参数为订阅的topic(订阅主题)
			//第二个参数是作为key，TODO 内容
			//第三个参数是要发送的消息
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(PropertiesUtil.TOPIC, String.valueOf(i),"发送一条消息 " + i);
			
			//这样的发送是异步非阻塞的
			producer.send(record);
			
			
//------------------------------------------这个是同步等待发送结果的代码...开始------------------------------------------------------------
			/*Future<RecordMetadata> future = producer.send(record);
			通过get()方法同步等待到返回的结果
			RecordMetadata metadata = future.get();*/
			
			
//------------------------------------------这个是同步等待发送结果的代码...结束------------------------------------------------------------			

			
//------------------------------------------这个是异步有回调的代码...开始------------------------------------------------------------			
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception e) {
					if (e != null)
						e.printStackTrace();
					System.out.println("发送的分区为 " + metadata.partition() + ", 偏移量为: " + metadata.offset());
				}
			});
//------------------------------------------这个是异步有回调的代码...结束------------------------------------------------------------			

			i++;
//			if (i%10==0){
//				System.out.println(i);
//			}
			Thread.sleep(1000);
		}
	}
}
