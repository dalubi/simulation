package com.ices.simulation;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.ices.simulation.dao.mapper")
public class SimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulationApplication.class, args);
    }

}
