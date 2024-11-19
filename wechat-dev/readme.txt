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
-d redis:7.2.4
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
