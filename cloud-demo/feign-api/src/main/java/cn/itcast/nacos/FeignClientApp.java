package cn.itcast.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
public class FeignClientApp {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApp.class, args);
    }
}
