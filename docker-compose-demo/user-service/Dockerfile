FROM java:8-alpine
LABEL authors="王海虹"

# 拷贝jar包到镜像目录中
COPY ./user-service.jar /tmp/user-service.jar

# 暴露端口
EXPOSE 8088

# 入口，java启动命令
ENTRYPOINT java -jar -Dfile.encoding=UTF-8 -Dmaven.test.skip=true /tmp/user-service.jar