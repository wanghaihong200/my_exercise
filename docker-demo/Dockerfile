# 指定基础镜像
FROM centos:latest
LABEL authors="王海虹"
# 配置环境变量
ENV JAVA_DIR=/usr/local
ENV TZ=Asia/Shanghai

# 拷贝jdk和jar包
COPY ./jdk8.tar.gz $JAVA_HOME/
#COPY ./gateway.jar /tmp/gateway.jar
#COPY ./user-service.jar /tmp/user-service.jar
COPY ./order-service.jar /tmp/order-service.jar


# 安装JDK
RUN tar -xf ./jdk8.tar.gz \
    && mv ./jdk1.8.0_144 $JAVA_DIR/java8

ENV JAVA_HOME=$JAVA_DIR/java8
ENV PATH=$PATH:$JAVA_HOME/bin

# 暴露端口
#EXPOSE 8088
#EXPOSE 10010
EXPOSE 8087

# 入口，镜像中 java启动命令
#ENTRYPOINT java -jar -Dfile.encoding=UTF-8 -Dmaven.test.skip=true /tmp/gateway.jar
ENTRYPOINT java -jar -Dfile.encoding=UTF-8 -Dmaven.test.skip=true /tmp/order-service.jar
#ENTRYPOINT java -jar -Dfile.encoding=UTF-8 -Dmaven.test.skip=true /tmp/user-service.jar


