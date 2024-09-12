### 准备工作

##### 1. 安装包准备

安装包上传到 `/usr/local`

```markdown
# es
elasticsearch-8.1.0-linux-x86_64.tar.gz
# logstash
logstash-8.1.0-linux-x86_64.tar.gz
# filebeat
filebeat-8.1.0-linux-x86_64.tar.gz
# kibana
kibana-8.1.0-linux-x86_64.tar.gz
```

##### 2. 安装包功能说明

- **filebeat**
  - 收集不同服务器的日志数据，上报给 logstash【如果不需要数据过滤，filebeat可以直接上传到 es】
- **logstash**
  - 拿到 filebeat 上传过来的数据，进行数据过滤操作，然后提交到 es
- **es**
  - 相当于数据库，保存全部的日志数据，可以进行数据搜索
- **kibana**
  - 可视化工具，将 es 中的数据进行展示

> 如果收集的日志特别庞大，建议加入 kafka 作为中间缓冲层。

***

### 1. elasticsearch

```markdown
# 1 解压
- tar -zxvf elasticsearch-8.1.0-linux-x86_64.tar.gz
# 2 创建日志文件目录
- cd elasticsearch-8.1.0/
- mkdir data
- cd data
- mkdir logs
# 3 处于安全考虑 es 的运行不允许以root 用户执行，所以需要先要创建用户
- sudo adduser elasticsearch
# 4 将解压的 es 目录赋予新创建的用户
- sudo chown -R elasticsearch:elasticsearch /usr/local/elasticsearch-8.1.0
# 5 修改配置 
- cd /usr/local/elasticsearch-8.1.0/config
- vim elasticsearch.yml 【修改的内容看下面配置】
# 6 切换用户
- su elasticsearch
# 7 启动服务
- cd /usr/local/elasticsearch-8.1.0
- ./bin/elasticsearch      【前台启动】
- ./bin/elasticsearch -d   【后台启动】
# 8 停止命令：
- jps           # 查出端口
- kill -9 2237  # 杀死进程
  
# 无法启动解决方案
- 1 启动失败提示内存不够：
- vim /etc/sysctl.conf
 在下面增加：vm.max_map_count=262144
 刷新配置：sysctl -p

- 修改 jvm.options 配置文件
- cd /usr/local/elasticsearch-8.1.0/config
- vim jvm.options
```

elasticsearch.yml【单节点配置】

```yaml
cluster.name: my-application # my-application名称可自定义
node.name: node-1   # node-1名称可自定义
path.data: /usr/local/elasticsearch-8.1.0/data
path.logs: /usr/local/elasticsearch-8.1.0/data/logs # 上面创建好的日志目录
network.host: 0.0.0.0 # 表示允许所有访问，可设置指定IP
http.port: 9200
cluster.initial_master_nodes: ["node-1"]
# 下面两行配置不使用安全验证，需要则自行配置
xpack.security.enabled: false
xpack.security.http.ssl:
  enabled: false
```

***

### 2. logstash

```markdown
# 1 解压
- tar -zxvf logstash-8.1.0-linux-x86_64.tar.gz
# 2 创建目录
- cd /usr/local/logstash-8.1.0
- mkdir sync
# 3 新建启动配置
- cd sync
- vim filebeat_to_logstash.conf 【修改的内容看下面配置】
# 4 启动服务
- cd /usr/local/logstash-8.1.0/bin
- ./logstash -f /usr/local/logstash-8.1.0/sync/filebeat_to_logstash.conf 【前台启动】
- nohup ./logstash -f /usr/local/logstash-8.1.0/sync/filebeat_to_logstash.conf & 【后台启动】

```

filebeat_to_logstash.conf【单节点配置】【下面的配置可以按照自己的方式进行过滤】

```markdown
input {
  beats {
    port => 5044
  }
}

filter {
        grok {
                match => {
"message" => "\[%{DATA:time_local}\] \[%{NOTSPACE:uri_name}\]\[%{NOTSPACE:type}\],userId=%{NOTSPACE:userId},Object=\^%{DATA:body}\^"
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
    # 打印日志到控制台【生产不建议打印】
    stdout {
        codec=>rubydebug
    }
}

```

上面配置对应日志收集的格式必须以下格式：

```markdown
[2024-09-07 17:21:20] [getRecommendItems][params],userId=123456,Object=^{"itemCode":"AMC","languageCode":"CH","sessionId":"76fb244765f44a4a939516718b60e571"}^
[2024-09-07 00:22:01] [getRecommendItems][return],userId=123456,Object=^{"code":1,"data":{},"message":"SUCCESSED"}^
```

***

### 3 filebeat

```markdown
# 1 解压
- tar -zxvf filebeat-8.1.0-linux-x86_64.tar.gz
# 2 重命名
- mv filebeat-8.1.0-linux-x86_64 filebeat-8.1.0
# 3 修改配置文件
- vim filebeat.yml 【修改的内容看下面配置】
# 4 启动服务
- ./filebeat -e      # 前台启动 -e 表示打印日志，也可以不要
```

vim filebeat.yml

```markdown
# 1）这里改成true 表示启动该功能
enabled: true  

# 2）这里填写监控的日志路径
paths:
  - /usr/local/filebeat-8.1.0/log/*.log 

# 3）注释 es 的配置（下面两行注释掉）
#output.elasticsearch:
  #hosts: ["localhost:9200"]
  
# 4）打开 logstash 注释
output.logstash:
  hosts: ["47.76.68.216:5044"] 【正确填写 logstash 所在服务器的IP地址】
 
# 5）自定义标识
filebeat.inputs:
	fields:
    document_type: mexico-test-client 【该表示提供给logstash区分创建对应的es索引】

```

**基于CentOS的方式后台启动filebeat**

```markdown
# cd
cd /etc/systemd/system
# 创建文件
vim filebeat.service 【新增下面内容】
# 重新加载systemd以读取新的服务文件
systemctl daemon-reload
# 检查Filebeat服务状态
systemctl status filebeat
# 启动Filebeat服务状态
systemctl start filebeat
# 停止Filebeat服务状态
systemctl stop filebeat
```

**filebeat.service**

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



***

### 4 kibana

```markdown
# 1 解压
- tar -zxvf kibana-8.1.0-linux-x86_64.tar.gz
# 2 修改配置文件
- cd /usr/local/kibana-8.1.0/config
- vim kibana.yml 【修改的内容看下面配置】
# 3 给之前es创建的用户赋权
- cd /usr/local
- chown elasticsearch:elasticsearch -R kibana-8.1.0/
# 4 切换用户
- su elasticsearch
# 5 启动服务
- cd /usr/local/kibana-8.1.0
- ./bin/kibana      【前台启动】
- nohup /usr/local/kibana-8.1.0/bin/kibana &   【后台启动】
# 6 浏览器访问服务
http://8.218.31.154:5601/app/home#/

查看启动进程：ps aux | grep kibana
```

vim kibana.yml

```markdown
# kibana地址，注意修改为自己的服务器地址（需要取消注释）
server.host: "172.31.0.2"
# elasticsearch地址，注意修改为自己的es服务器地址（需要取消注释）
elasticsearch.hosts: ["http://172.31.0.2:9200"]
# 国际化地址修改为中文（需要取消注释）
i18n.locale: "zh-CN"
```
