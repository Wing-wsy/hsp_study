

linux IP地址：192.168.10.90

### 1. java

```markdown
路径：/usr/local/java/my-project

- http://47.76.68.216:9999/doc.html#/home

启动JAR包命令：nohup java -jar redpig-boot.jar --spring.profiles.active=prod &
```

### 2. mysql

```markdown
路径：/usr/local/mysql  端口：3306
```

### 3. redis

```markdown

```

### 4. rabbitmq

```markdown
路径：/usr/lib/rabbitmq/bin  

执行如下命令,启动rabbitmq中的插件管理
	rabbitmq-plugins enable rabbitmq_management
	
启动RabbitMQ的服务
	systemctl start rabbitmq-server
	systemctl restart rabbitmq-server
	systemctl stop rabbitmq-server
```

### 5. nginx

```markdown
路径：/usr/local/nginx
```

### 6. zookeeper

```markdown
占用端口：2181
官网下载地址：https://archive.apache.org/dist/zookeeper/
安装博客1：https://blog.csdn.net/he_xin2009/article/details/136914117
安装博客2：https://www.cnblogs.com/liuhenghui/articles/13435854.html

路径：/usr/local/zookeeper/bin
启动服务：进入 bin目录 执行 ./zkServer.sh start 命令进行启动
./zkServer.sh status
进入客户端命令：./zkCli.sh
执行：ls /
```

### 7. kafka

```markdown
安装博客：https://blog.csdn.net/qq_42923937/article/details/136458173

linux服务器搭建kafka并集成springboot【亲测成功的博客】：https://blog.csdn.net/m0_51423610/article/details/130766095

路径：/usr/local/kafka_2.12
日志路径：/usr/local/kafka_2.12/log

启动命令：nohup /usr/local/kafka_2.12/bin/kafka-server-start.sh /usr/local/kafka_2.12/config/server.properties &

停止命令：./kafka-server-stop.sh


#####

问题：启动报错内存不足
解决方案：
vim kafka-server-start.sh

 export KAFKA_HEAP_OPTS="-Xmx1G -Xms1G"
修改为
 export KAFKA_HEAP_OPTS="-Xmx256M -Xms128M"
```

创建生产者 topic 和 消费者 topic

```markdown
/usr/local/kafka_2.12/bin
# 生产者
./kafka-console-producer.sh --broker-list 192.168.10.90:9092 --topic test_topic

# 消费者
./kafka-console-consumer.sh --bootstrap-server 192.168.10.90:9092 --topic test_topic

```









