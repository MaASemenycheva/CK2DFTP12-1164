package com.cbr.ilk.camundapostgreskotlin

import com.cbr.ilk.camundapostgreskotlin.unhandledProcesses.GetUnhandledProcesses
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.sql.DriverManager
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@EnableProcessApplication
@SpringBootApplication
class Main

fun main(args: Array<String>) {

    DriverManager.getConnection(
        "jdbc:postgresql://127.0.0.1/test", "postgres", "admin"
    ).use { conn ->
        // TODO DOESNT RECONNECT!!!
        Executors.newSingleThreadScheduledExecutor()
            .scheduleWithFixedDelay({
                println("Running callable")
                GetUnhandledProcesses.startProcess(conn, 1, LocalDateTime.now()) {
                    println(it)
                    true
                }
            }, 10, 10, TimeUnit.SECONDS)
    }
    SpringApplication.run(Main::class.java)
}
