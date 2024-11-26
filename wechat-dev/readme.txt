1) docker 安装 mysql8 命令【需要提前下载好镜像】
docker run -p 4406:3306 --name mysql8-imooc \
-v /Users/wing/architect/docker/mysql8.3.0/log:/var/log/mysql \
-v /Users/wing/architect/docker/mysql8.3.0/data:/var/lib/mysql \
-v /Users/wing/architect/docker/mysql8.3.0/conf:/etc/mysql/conf.d \
-v /Users/wing/architect/docker/mysql8.3.0/mysql-files:/var/lib/mysql-files \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:8.3.0 \
--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

2）docker 安装 redis7 命令【需要提前下载好镜像】
docker run -p 5379:6379 --name redis-imooc \
-v /Users/wing/architect/docker/redis7/data:/data \
-v /Users/wing/architect/docker/redis7/conf/redis.conf:/etc/redis/redis.conf \
-d redis:7.2.4 \
redis-server /etc/redis/redis.conf

3-1）docker 下载 nacos 命令
docker pull nacos/nacos-server:v2.3.1-slim

3-2）docker 安装 nacos 命令【需要提前下载好镜像】
docker run --name nacos-imooc \
-e MODE=standalone \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
-p 7848:7848 \
-d nacos/nacos-server:v2.3.1-slim

3-3）访问nacos
http://127.0.0.1:8848/nacos

4-1）拉取minio镜像
docker pull minio/minio

4-2）启动minio
docker run -p 8000:8000 -p 8001:8001 \
--name minio-imooc \
-d --restart=always \
-e "MINIO_ROOT_USER=imooc" \
-e "MINIO_ROOT_PASSWORD=imooc123456" \
-v /Users/wing/architect/docker/minio/wechat/data:/data \
minio/minio server /data --console-address ":8001" -address ":8000"

5-1）拉取rabbitmq镜像
docker pull rabbitmq:3.13.2-management

5-2）启动rabbitmq
docker run --name rabbitmq-imooc \
-p 5681:5671 \
-p 5682:5672 \
-p 4379:4369 \
-p 15681:15671 \
-p 15682:15672 \
-p 25682:25672 \
--restart always \
-d rabbitmq:3.13.2-management

运行成功访问管理界面：
http://127.0.0.1:15682/

6-1）拉取 zookeeper 镜像
docker pull zookeeper:3.9.2

6-2）启动 zookeeper
docker run --name zookeeper-imooc  \
-p 3191:2181 \
--restart always \
-v /Users/wing/architect/docker/zookeeper3.9.2/data:/data \
-v /Users/wing/architect/docker/zookeeper3.9.2/conf:/conf \
-v /Users/wing/architect/docker/zookeeper3.9.2/logs:/datalog \
-d zookeeper:3.9.2


============ 发布 gateway ============
-- 打包（注意后面有个小点）
docker build -f DockerFile -t yc/gateway:v1.0 .

-- 注意要创建这个文件夹
/home/wechat-dev/gateway/mydata

-- 运行
docker run \
--name gateway \
-p 1000:1000 \
-v /home/wechat-dev/gateway/mydata:/mydata \
-d yc/gateway:v1.0

-- 看启动日志
 docker logs gateway

 ============ 发布 auth ============
-- 打包（注意后面有个小点）
docker build -f DockerFile -t yc/auth-service:v1.0 .

-- 注意要创建这个文件夹
/home/wechat-dev/auth-service/mydata

-- 运行
docker run \
--name auth-service \
-p 88:88 \
-v /home/wechat-dev/auth-service/mydata:/mydata \
-d yc/auth-service:v1.0


-- 增删监听
stat -w /abc/imooc
-- 修改监听
get -w /abc
-- 子节点监听
ls -w /abc