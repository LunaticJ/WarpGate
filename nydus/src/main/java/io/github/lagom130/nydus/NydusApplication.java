package io.github.lagom130.nydus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.lagom130.nydus.mapper")
public class NydusApplication {

    public static void main(String[] args) {
        SpringApplication.run(NydusApplication.class, args);
    }

}
