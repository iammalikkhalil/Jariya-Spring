package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
