package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
@EnableJpaRepositories
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
