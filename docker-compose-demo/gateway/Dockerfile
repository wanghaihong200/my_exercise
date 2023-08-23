FROM java:8-alpine
LABEL authors="王海虹"

# 拷贝jar包到镜像目录中
COPY ./gateway.jar /tmp/gateway.jar

# 暴露端口
EXPOSE 10010

# 入口，java启动命令
ENTRYPOINT java -jar -Dfile.encoding=UTF-8 -Dmaven.test.skip=true /tmp/gateway.jar