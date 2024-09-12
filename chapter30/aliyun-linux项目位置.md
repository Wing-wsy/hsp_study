yc-测试服务器

```markdown
外：8.217.253.19  内：172.31.0.2
root
SzycTest_New_168&SHyfb
```

我的服务器

```markdown
47.101.143.172(公)
172.22.242.114(私有)

远程工具连接阿里云
IP：47.101.143.172
端口：9988
用户名：root
登录密码：P2AN~gKm#{4
```

### 1. jdk

```markdown
=== 新服务器装jdk17 开始 ===
安装包位置：/Users/wing/architect/jdk安装包/linux/jdk-17_linux-x64_bin.tar.gz
将安装包上传linux 路径/usr/local/java
解压 tar -zxvf jdk-17_linux-x64_bin.tar.gz
配置环境变量:vim /etc/profile
使环境变量生效:source /etc/profile
java -version
=== 新服务器装jdk17 结束 ===


路径：/usr/local/java/my-project
/usr/local/java/jdk-17.0.11
/usr/local/java/jdk-17.0.11/bin/java

- http://47.76.68.216:9999/doc.html#/home

启动JAR包命令：nohup java -jar redpig-boot.jar --spring.profiles.active=prod &
```

### 2. mysql

```markdown
路径：/usr/local/mysql  端口：3306

查看启动状态：service mysqld status


url: jdbc:mysql://47.76.68.216:3306/smart?useUnicode=true&characterEncoding=utf-8
username: root
password: P@ssw0rd123!
driver-class-name: com.mysql.cj.jdbc.Driver
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
=== 新服务器装zookeeper单机 开始 ===
安装包位置：/Users/wing/architect/zookeeper资料/apache-zookeeper-3.9.1-bin.tar.gz
将安装包上传linux 路径/usr/local
解压 tar -zxvf apache-zookeeper-3.9.1-bin.tar.gz
重命名：mv apache-zookeeper-3.9.1-bin zookeeper
zookeeper 目录下创建 zookeeper 数据和日志的存放目录
cd /usr/local/zookeeper
mkdir data
mkdir logs
进入：/usr/local/zookeeper/conf/
重命名：mv zoo_sample.cfg zoo.cfg
修改配置文件（只修改 dataDir=/usr/local/zookeeper/data）
路径：/usr/local/zookeeper/bin
启动服务：进入 bin目录 
执行 ./zkServer.sh start 命令进行启动
./zkServer.sh status
进入客户端命令：./zkCli.sh
执行：ls /
jps 有 QuorumPeerMain 说明启动成功
=== 新服务器装zookeeper单机 结束 ===

占用端口：2181
官网下载地址：https://archive.apache.org/dist/zookeeper/
安装博客1：https://blog.csdn.net/he_xin2009/article/details/136914117
安装博客2：https://www.cnblogs.com/liuhenghui/articles/13435854.html

路径：/usr/local/zookeeper/bin
启动服务：进入 bin目录 
执行 ./zkServer.sh start 命令进行启动
./zkServer.sh status
进入客户端命令：./zkCli.sh
执行：ls /
```

### 7. kafka

```markdown
=== 新服务器装kafka 开始 ===
安装包位置：/Users/wing/architect/kafka资料/kafka_2.12-3.8.0.tgz
将安装包上传linux 路径/usr/local 【mv kafka_2.12-3.8.0.tgz /usr/local】
解压  tar -zxvf kafka_2.12-3.8.0.tgz 
重命名：mv kafka_2.12-3.8.0 kafka_2.12
kafka_2.12 目录下创建日志的存放目录
mkdir log #kafka日志
mkdir zookeeper
cd zookeeper
mkdir log  #zookeeper日志
修改配置文件
进入：/usr/local/kafka_2.12/config
vim server.properties 
-----
增加：
listeners=PLAINTEXT://172.31.0.2:9092
advertised.listeners=PLAINTEXT://8.217.253.19:9092

port=9092
host.name=172.31.0.2
advertised.host.name=172.31.0.2
log.dirs=/usr/local/kafka_2.12/log
zookeeper.connect=172.31.0.2:2181

注释：
# log.dirs=/tmp/kafka-logs
# zookeeper.connect=localhost:2181
-----
启动命令：nohup /usr/local/kafka_2.12/bin/kafka-server-start.sh /usr/local/kafka_2.12/config/server.properties &
jps 有 QuorumPeerMain 说明启动成功
=== 新服务器装kafka 结束 ===

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

### 8. elasticsearch

```markdown
官网下载地址：https://www.elastic.co/cn/downloads/elasticsearch
下载教程：https://www.jianshu.com/p/2cb29948fde1
安装博客：https://blog.csdn.net/weixin_38385580/article/details/132391576

cd /usr/local/elasticsearch-8.1.0
mkdir data
cd data
mkdir logs

路径：/usr/local/elasticsearch-8.1.0
日志路径：/usr/local/elasticsearch-8.1.0/data/logs

es自带jdk路径：/usr/local/elasticsearch-8.1.0/jdk/bin/java

端口号：9200
创建用户：sudo adduser elasticsearch
将解压的es 目录赋予新创建的用户
sudo chown -R elasticsearch:elasticsearch /usr/local/elasticsearch-8.1.0
切换用户：su elasticsearch
启动命令：
  cd /usr/local/elasticsearch-8.1.0
  1)./bin/elasticsearch      # 前台启动
  2）./bin/elasticsearch -d  # 后台启动
  
停止命令：
  1) jps      # 查出端口
  2） kill -9 2237 # 杀死进程
  
 1)启动失败提示内存不够：
 vim /etc/sysctl.conf
 在下面增加：vm.max_map_count=262144
 刷新配置：sysctl -p
 
 2)ssl错误
 vim elasticsearch.yml
 xpack.security.enabled: false
      
#xpack.security.enrollment.enabled: true

xpack.security.http.ssl:
  enabled: false
  
  
 ###################
 启动失败博客参考：https://blog.csdn.net/qq_37647812/article/details/140050683
```

elasticsearch-ik分词器

```markdown
github下载地址：https://github.com/infinilabs/analysis-ik/releases?page=5

慕课网安装教程：https://class.imooc.com/lesson/1231#mid=29323
路径：/usr/local/elasticsearch-8.1.0/plugins/ik
```

### 9. logstash

> 博客：https://cloud.tencent.com/developer/article/2222192?areaSource=102001.14&traceId=8Y4vM-tnVJcjkaPwbD5VV
>
> 优秀博客：https://blog.csdn.net/zjcjava/article/details/99258682?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7Ebaidujs_baidulandingword%7ECtr-4-99258682-blog-127319892.235%5Ev43%5Epc_blog_bottom_relevance_base7&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7Ebaidujs_baidulandingword%7ECtr-4-99258682-blog-127319892.235%5Ev43%5Epc_blog_bottom_relevance_base7&utm_relevant_index=9

#### 9.1 同步mysql数据到 es

##### 9.1.1 基础配置

```markdown
官网下载地址：https://www.elastic.co/cn/downloads/past-releases#logstash
下载教程：
安装博客：

解压：tar -zxvf logstash-8.1.0-linux-x86_64.tar.gz 
cd /usr/local/logstash-8.1.0
mkdir sync
/usr/local/logstash-8.1.0/sync/mysql-connector-java-8.0.30.jar
日志路径：

mkdir sync
cd sync
vim logstash-db-sync.conf

启动命令：
  cd /usr/local/logstash-8.1.0/bin
  1)./logstash -f /usr/local/logstash-8.1.0/sync/logstash-db-sync.conf 【前台启动】
  2）nohup ./logstash -f /usr/local/logstash-8.1.0/sync/logstash-db-sync.conf & 【后台启动】

  
停止命令：
  1) jps      # 查出端口
  2） kill -9 2237 # 杀死进程
  
```

logstash-db-sync.conf配置 可用(解决数据库和logstash时间差8小时问题)【/usr/local/logstash-8.1.0/sync/logstash-db-sync.conf】

```markdown
input {
    jdbc {
        # 设置 MySql/MariaDB 数据库url以及数据库名称
        jdbc_connection_string => "jdbc:mysql://192.168.10.90:3306/wing?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true"
        # 用户名和密码
        jdbc_user => "root"
        jdbc_password => "P@ssw0rd123!"
        # 数据库驱动所在位置，可以是绝对路径或者相对路径
        jdbc_driver_library => "/usr/local/logstash-8.1.0/sync/mysql-connector-java-8.0.30.jar"
        # 驱动类名
        jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
        # 开启分页
        jdbc_paging_enabled => "true"
        # 分页每页数量，可以自定义
        jdbc_page_size => "10000"
        # 执行的sql文件路径
        statement_filepath => "/usr/local/logstash-8.1.0/sync/smart.sql"
        # 设置定时任务间隔  含义：分、时、天、月、年，全部为*默认含义为每分钟跑一次任务
        schedule => "* * * * *"
        # 索引类型
        type => "_doc"
        # 是否开启记录上次追踪的结果，也就是上次更新的时间，这个会记录到 last_run_metadata_path 的文件
        use_column_value => true
        # 记录上一次追踪的结果值
        last_run_metadata_path => "/usr/local/logstash-8.1.0/sync/track_time"
        # 如果 use_column_value 为true， 配置本参数，追踪的 column 名，可以是自增id或者时间
        tracking_column => "update_date"
        # tracking_column 对应字段的类型
        tracking_column_type => "timestamp"
        # 是否清除 last_run_metadata_path 的记录，true则每次都从头开始查询所有的数据库记录
        clean_run => false
        # 数据库字段名称大写转小写
        lowercase_column_names => false
        jdbc_default_timezone => "Asia/Shanghai"
        #使用本地时区为local，否则sql_last_value如果是timestamp，时间会提前8小时
        #值可以是任何的：utc，local，默认值为 "utc"
        plugin_timezone => "local"
    }
}

filter {
    # 因为时区问题需要修正时间
    ruby {
        code => "event.set('timestamp', event.get('@timestamp').time.localtime + 8*60*60)"
    }
    ruby {
        code => "event.set('@timestamp',event.get('timestamp'))"
    }
    mutate {
        remove_field => ["timestamp"]
    }
    # 因为时区问题需要修正时间
    ruby {
        code => "event.set('create_time', event.get('create_time').time.localtime + 8*60*60)"
    }
    # 因为时区问题需要修正时间
    ruby {
        code => "event.set('update_time', event.get('update_time').time.localtime + 8*60*60)"
    }
    # 因为时区问题需要修正时间
    ruby {
        code => "event.set('update_date', event.get('update_date').time.localtime + 8*60*60)"
    }
    # 转换成日期格式
    ruby {
        code => "event.set('start_date', event.timestamp.time.localtime.strftime('%Y-%m-%d'))"
    }
    # 转换成日期格式
    ruby {
        code => "event.set('end_date', event.timestamp.time.localtime.strftime('%Y-%m-%d'))"
    }
    # 转换成日期格式
    ruby {
        code => "event.set('sign_date', event.timestamp.time.localtime.strftime('%Y-%m-%d'))"
    }
}

output {
    elasticsearch {
        # es地址
        hosts => ["192.168.10.90:9200"]
        # 同步的索引名
        index => "smart-index"
        # 设置_docID和数据相同
        document_id => "%{id}"
    }
    # 日志输出
    stdout {
        codec => json_lines
    }
}
```

smart.sql 【/usr/local/logstash-8.1.0/sync/smart.sql 】

```sql
select id,
       shop_id,
       user_id,
       title,
       images,
       content,
       liked,
       comments,
       create_time,
       update_time,
       update_date
from tb_blog
where update_date >= :sql_last_value
order by update_date  # 注意，这里加上了order by update_date logstash :sql_last_value 才能更新为最新
```

##### 9.1.2 **自定义模板配置中文分词** [没效果，后续研究]

```markdown
        # 定义模板名称
        template_name => "myik" 
        # 模板所在位置
        template => "/usr/local/logstash-8.1.0/sync/logstash-ik.json"
        # 重写模板
        template_overwrite => true
        # 默认为true，false关闭logstash自动管理模板功能，如果自定义模板，则设置为false
        manage_template => false
```

问题1

```markdown
1）如果es文档ID与数据库ID不一致，修改上面配置 
# 设置_docID和数据相同
 document_id => "%{id}"
```

问题2

```markdown
1）数据库数据删了，但是 logstash 保存在 es 中的数据还存在，如何处理？
不能手动在库删除，而是在代码中执行删除时，加一个删除es数据逻辑
```



***

#### 9.2 elk日志收集

> ELK搭建博客：https://blog.csdn.net/m0_51510236/article/details/130413227

##### 9.2.1 es

跟上面搭建好一样，不用修改

##### 9.2.2 logstash

cd /usr/local/logstash-8.1.0/sync

vim logstash-log-sync.conf

添加下面内容

```markdown
input {
  tcp {
    mode => "server"
    port => 4560  # 日志的输出来源，我们将暴露一个4560端口接收来自SpringBoot的日志
  }
}
filter {}
output {
  elasticsearch {
    action => "index"
    hosts => ["192.168.10.90:9200"]
    # 同步的索引名
    index => "test-log"
  }
}
```

启动服务

```markdown
启动命令：
  cd /usr/local/logstash-8.1.0/bin
  1)./logstash -f /usr/local/logstash-8.1.0/sync/logstash-log-sync.conf 【前台启动】
  2）nohup ./logstash -f /usr/local/logstash-8.1.0/sync/logstash-log-sync.conf & 【后台启动】
```

查看服务状态

jps

##### 9.2.3 kibana

看下面

##### 9.2.3.4 springboot

```xml
<!-- logstash -->
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>6.6</version>
</dependency>
```

然后我们来到 `application.yaml` 文件中添加下面几个配置：

```yaml
log:
  logstash-host: 47.76.68.216:4560
```

logback.xml

```xml
<!-- 读取SpringBoot配置文件获取logstash的地址和端口 -->
<springProperty scope="context" name="logstash-host" source="log.logstash-host"/>

<!-- LOGSTASH 配置 -->
<appender name="Logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
    <!-- 获取logstash地址作为输出的目的地 -->
    <destination>${logstash-host}</destination>
    <encoder chatset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder" />
</appender>

<root level="Info">
    <appender-ref ref="Logstash" />
</root>
```

启动服务打印log日志

kibana管理平台查看是否有数据

平台：http://47.76.68.216:5601/app/dev_tools#/console

窗口执行查询

```json
GET _cat/indices

GET test-log/_search
{
  "sort": [
    {
      "@timestamp": {
        "order": "desc"
      }
    }
  ]
}
```



#### 9.3 filebeat收集到logstash 案例1

/usr/local/logstash-8.1.0/sync/filebeat_to_logstash.conf

```conf
input {
    beats {
        port=>5044
        codec=>plain {
            charset=>"GBK"
        }
    }
}

filter {
    mutate {
    split=>["message","|"]
        add_field => {
            "field1" => "%{[message][0]}"
        }
        add_field => {
            "field2" => "%{[message][1]}"
        }
        # beats传递过来的一些附加数据并不是我们想要的，同时把message数据也去掉，只保留我们想要的数据即可
        remove_field => ["message"]
    }
    json {
        source => "field1"
        target => "field2"
    }
}

# 输出数据到控制台
output {
    stdout {
        codec=>rubydebug
    }
}

```

启动命令：
  cd /usr/local/logstash-8.1.0/bin
  1)./logstash -f /usr/local/logstash-8.1.0/sync/filebeat_to_logstash.conf【前台启动】



```markdown
67826e78|{"name1":"wing","age1":18,"hobby1":[1,2,3,4,5,6,7,8,9,10],"name2":"wing","age2":18,"hobby2":[1,2,3,4,5,6,7,8,9,10],"name3":"wing","age3":18,"hobby3":[1,2,3,4,5,6,7,8,9,10],"name4":"wing","age4":18,"hobby4":[1,2,3,4,5,6,7,8,9,10],"name5":"wing","age5":18,"hobby5":[1,2,3,4,5,6,7,8,9,10],"name6":"wing","age6":18,"hobby6":[1,2,3,4,5,6,7,8,9,10]}

67826e79|name-wing-age19
67826e70|name-wing-age20
```

***

#### 9.4 filebeat收集到logstash 案例2

/usr/local/logstash-8.1.0/sync/filebeat_to_logstash2.conf

```markdown
# Sample Logstash configuration for creating a simple
# Beats -> Logstash -> Elasticsearch pipeline.
 
input {
  beats {
    port => 5044
  }
}
 
filter {
	grok {
		match => [
			"message", "%{TIMESTAMP_ISO8601:timestamp_string}%{SPACE}%{GREEDYDATA:line}"
		]
	}
 
	date  {
		match => ["timestamp_string", "ISO8601"]
	}
 
	mutate {
		remove_field => [message, timestamp_string]
	}	
}
 
# 输出数据到控制台
output {
    stdout {
        codec=>rubydebug
    }
}
```

启动：./logstash -f /usr/local/logstash-8.1.0/sync/filebeat_to_logstash2.conf



nohup ./logstash -f /usr/local/logstash-8.1.0/sync/filebeat_to_logstash2.conf & 【后台启动】

测试数据：

```markdown
2019-09-09T13:00:00Z Whose woods these are I think I know.
2019-09-09T14:00:00Z His house is in the village, though;
2019-09-09T15:00:00Z He will not see me stopping here
2019-09-09T16:00:00Z To watch his woods fill up with snow.
2019-09-09T17:00:00Z My little horse must think it queer
2019-09-09T18:00:00Z To stop without a farmhouse near
2019-09-09T19:00:00Z Between the woods and frozen lake
2019-09-09T20:00:00Z The darkest evening of the year.
2019-09-09T21:00:00Z He gives his harness bells a shake
2019-09-09T22:00:00Z To ask if there is some mistake.
2019-09-09T23:00:00Z The only other sound's the sweep
2019-09-09T00:00:00Z Of easy wind and downy flake.
2019-09-09T01:00:00Z The woods are lovely, dark, and deep,
2019-09-09T02:00:00Z But I have promises to keep,
2019-09-09T03:00:00Z And miles to go before I sleep,
2019-09-09T04:00:00Z And miles to go before I sleep
```



***

#### 9.5 测试收集数据

```markdown
[2024-09-07 05:55:55] [getRecommendItems][params],userId=123456,Object=^{"itemCode":"AMC","languageCode":"CH","sessionId":"76fb244765f44a4a939516718b60e571"}^
[2024-09-07 17:22:01] [getRecommendItems][return],userId=123456,Object=^{"code":1,"data":{},"message":"SUCCESSED"}^

[2024-09-07 17:21:20] [getRecommendItems][params],userId=123456,Object=^{"itemCode":"AMC","languageCode":"CH","sessionId":"76fb244765f44a4a939516718b60e571"}^
[2024-09-07 17:22:01] [getRecommendItems][return],userId=123456,Object=^{"code":1,"data":{},"message":"SUCCESSED"}^

[2024-09-07 17:21:20] [getRecommendItems][params],userId=6987698798,Object=^{"itemCode":"AMC","languageCode":"CH","sessionId":"76fb244765f44a4a939516718b60e571"}^
[2024-09-07 66:66:01] [getRecommendItems][return],userId=123456,Object=^{"code":1,"data":{},"message":"SUCCESSED"}^

[2024-09-10 19:46:11] [createRepayCodeV2][params],userId=9fe3ca19193d40f8a0b63a4e89383c1e,Object=^{"channel":"oxxo_41","itemCode":"SPO","languageCode":"ESP","loanId":100002292,"loanSonId":4437130,"productItemCode":"SPAO","repayType":1,"sessionId":"76fb244765f44a4a939516718b60e570"}^

[2024-09-10 20:07:37] [createRepayCodeV2][return],userId=9fe3ca19193d40f8a0b63a4e89383c1e,Object=^{"code":0,"message":"订单已还"}^

[2024-09-10 07:21:18] [submitTimeLoan][params],userId=9fe3ca19193d40f8a0b63a4e89383c1e,Object=^{"cardId":930204,"isAgreeCreditChange":0,"itemCode":"SPO","languageCode":"ESP","productId":1644,"productItemCode":"DSAR","sessionId":"05da127031524e5faa67af17f744307c","status":1}^

[2024-09-10 07:21:20] [submitTimeLoan][return],userId=9fe3ca19193d40f8a0b63a4e89383c1e,Object=^{"code":1,"data":{"executionTime":120000,"timeOrderStatus":1},"message":"Operación exitosa"}^

[2024-09-07 66:66:01] [getRecommendItems][return],userId=123456,Object=^{"code":1,"data":{},"message":"SUCCESSED"}^
```

mexico

```markdown
input {
  beats {
    port => 5044
  }
}
 
filter {
        grok {
                match => {
"message" => "\[%{DATA:time_local}\] \[%{NOTSPACE:uri_name}\]\[%{NOTSPACE:type}\],userId=%{NOTSPACE:userId},Object=\^%{NOTSPACE:body}\^"
}
        }

        date  {
                match => ["timestamp_string", "ISO8601"]
        }

        mutate {
                remove_field => [message, timestamp_string]
        }
}
 
# 输出数据到控制台
output {
    stdout {
        codec=>rubydebug
    }
}



```



```markdown
output{
    if[fields][document_type] == "weblog" {
        stdout { codec => rubydebug }  
    }
}

output {
    elasticsearch {
        hosts => ["172.31.0.2:9200"]
        if[fields][document_type] == "mexico-test-client" {
        index => "mexico-test-client"
        }
    }
   
    stdout {
        codec=>rubydebug
    }
}

# 根据不同的filebeat创建不同的index名称
output {
  if [fields][document_type] == "mexico-test-client" {
    elasticsearch {
      index => "mexico-test-client_%{+YYYY.MM.dd}"
      hosts => ["172.31.0.2:9200"]
    }
  } else if [fields][document_type] == "mexico-prod-client-m1" {
    elasticsearch {
      index => "mexico-prod-client-m1_%{+YYYY.MM.dd}"
      hosts => ["172.31.0.2:9200"]
    }
  } 
  
    stdout {
        codec=>rubydebug
    }
}
```

es单查询条件

```markdown
GET mexico-prod-client-m1_2024.09.06/_search
{
  "query": {
    "match": {
      "userId": "76fb244765f44a4a939516718b60e888"
    }
  },
  "sort": [
    {
      "@timestamp": {
        "order": "desc"
      }
    }
  ]
  ,
    "_source": [
        "time_local",
        "userId",
        "uri_name",
        "type",
        "body",
        "event"
    ]
}
```

es多查询条件

```markdown
GET mexico-prod-client-m1_2024.09.06/_search
{
  "query": {
    "bool": {
      "must": [
        { "match": { "userId": "76fb244765f44a4a939516718b60e999" } }
      ],
      "filter": [
        { "term": { "type": "return" } }
      ]
    }
  },
  "sort": [
    {
      "@timestamp": {
        "order": "desc"
      }
    }
  ]
  ,
    "_source": [
        "time_local",
        "userId",
        "uri_name",
        "type",
        "body",
        "event"
    ]
}
```

#### 9.6 公司日志收集配置【完整】

```markdown
input {
  beats {
    port => 5044
  }
}

filter {
        grok {
                match => {
"message" => "\[%{DATA:time_local}\] \[%{NOTSPACE:uri_name}\]\[%{NOTSPACE:type}\],userId=%{NOTSPACE:userId},Object=\^%{NOTSPACE:body}\^"
}
        }

        date  {
                match => ["timestamp_string", "ISO8601"]
        }

        mutate {
                remove_field => [message, timestamp_string, agent, host, log, tags,input,sort,_source,ecs]
        }
}

output {
  if [fields][document_type] == "mexico-test-client" {
    elasticsearch {
      index => "mexico-test-client_%{+YYYY.MM}"
      hosts => ["172.31.0.7:9200"]
    }
  } else if [fields][document_type] == "mexico-prod-client-m1" {
    elasticsearch {
      index => "mexico-prod-client-m1_%{+YYYY.MM.dd}"
      hosts => ["172.31.0.7:9200"]
    }
  }

    stdout {
        codec=>rubydebug
    }
}

```



***

### 10 kibana

```markdown
官网下载地址：https://www.elastic.co/cn/downloads/kibana
安装博客：https://blog.csdn.net/m0_51510236/article/details/130413227
使用博客：https://www.cnblogs.com/chenqionghe/p/12503181.html

路径：/usr/local/kibana-8.1.0
日志路径：

tar -zxvf kibana-8.1.0-linux-x86_64.tar.gz 
修改配置：vim kibana.yml 
此时我们需要修改 config/kibana.yml 配置文件，修改的内容为：
# kibana地址，注意修改为自己的服务器地址（需要取消注释）
server.host: "172.31.0.2"
# elasticsearch地址，注意修改为自己的es服务器地址（需要取消注释）
elasticsearch.hosts: ["http://172.31.0.2:9200"]
# 国际化地址修改为中文（需要取消注释）
i18n.locale: "zh-CN"




端口号：5601
给之前es创建的用户赋权：chown elasticsearch:elasticsearch -R kibana-8.1.0/
切换用户：su elasticsearch

启动命令：
  cd /usr/local/kibana-8.1.0
  1)./bin/kibana      # 前台启动
  2）nohup /usr/local/kibana-8.1.0/bin/kibana &  # 后台启动
  
查看启动进程：ps aux | grep kibana
  
```

简单查询验证

```markdown
GET _search
{
  "query": {
    "match_all": {}
  }
}

GET _cat/indices

GET test-log/_search
{
  "sort": [
    {
      "@timestamp": {
        "order": "desc"
      }
    }
  ]
}
```







***

### 11 filebeat

```markdown
官网下载地址：https://www.elastic.co/cn/downloads/beats/filebeat
博客：https://blog.csdn.net/qq_52589631/article/details/131216188
后台启动博客：https://blog.csdn.net/zuihongyan518/article/details/136735162

cd /usr/local
解压文件：tar -zxvf filebeat-8.1.0-linux-x86_64.tar.gz
重命名：mv filebeat-8.1.0-linux-x86_64 filebeat-8.1.0
日志路径：/usr/local/filebeat-8.1.0/log

修改配置文件：filebeat.yml 见下



启动命令：
  cd /usr/local/filebeat-8.1.0
  1)./filebeat -e      # 前台启动 -e 表示打印日志，也可以不要
  2） # 后台启动
  
查看启动进程：ps aux | grep filebeat
  
```

基于CentOS的方式启动filebeat

```markdown
cd /etc/systemd/system
vim filebeat.service [新增下面内容]
# 重新加载systemd以读取新的服务文件
systemctl daemon-reload
# 检查Filebeat服务状态
systemctl status filebeat
# 启动Filebeat服务状态
systemctl start filebeat
# 停止Filebeat服务状态
systemctl stop filebeat
```

filebeat.service

```markdown
[Unit]
Description=Filebeat
After=network.target

[Service]
Type=simple
ExecStart=/usr/local/filebeat-8.1.0/filebeat -e -c /usr/local/filebeat-8.1.0/filebeat.yml

[Install]
WantedBy=multi-user.target
```





filebeat.yml 

```markdown
# 1）这里改成true 表示启动该功能
enabled: true  

# 2）这里填写监控的日志路径
paths:
  - /usr/local/filebeat-8.1.0/log/*.log 
    
# 3）es的配置注释掉
# ------------------------------ Logstash Output -------------------------------
filebeat.inputs:


- type: filestream

  enabled: true

  paths:
    - /usr/local/filebeat-8.1.0/log/*.log

  fields:
    document_type: mexico-prod-client-m1
    #level: debug

filebeat.config.modules:
  # Glob pattern for configuration loading
  path: ${path.config}/modules.d/*.yml

output.logstash:
  # The Logstash hosts
  hosts: ["8.217.253.19:5044"] # 4）打开这里的配置
```




