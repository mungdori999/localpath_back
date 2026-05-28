package com.mungdori.localpath;

import com.mungdori.localpath.adapter.config.LocalpathProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LocalpathProperties.class)
public class LocalpathApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalpathApplication.class, args);

    }

}
