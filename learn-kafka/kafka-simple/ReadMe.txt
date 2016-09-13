如果要传递对象的话，序列化器要么使用
					org.apache.kafka.common.serialization.ByteArraySerializer
然后自己反序列化成为一个对象


或者自己实现
		org.apache.kafka.common.serialization.Serializer<T>
		
		实现这个接口，实现自定义的序列化器，也可以传递自己的对象
		
		
		
		
		

		
结构说明


│  pom.xml      
│  ReadMe.txt
│  
└─src
    ├─main
    │  ├─java
    │  │  └─zxj
    │  │      └─kafka1
    │  │          │  ComsumerTest.java    消费者代码
    │  │          │  ProducerTest.java    生产者代码
    │  │          │  
    │  │          └─util
    │  │                  PropertiesUtil.java  获取连接属性的工具类
    │  │                  
    │  └─resources
    └─test
        ├─java
        └─resources
        
