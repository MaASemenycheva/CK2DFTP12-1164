package com.cbr.ilk.camundapostgreskotlin.externaltask1

import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.DriverManager
import kotlin.system.measureTimeMillis

@Component
class externalTaskSelect1 : ExternalTaskHandler {
    private val logger = LoggerFactory.getLogger(externalTaskSelect1::class.java)

    override fun execute(extTask: ExternalTask, extTaskService: ExternalTaskService) {
        DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "admin"
        ).use { conn ->
            val elapsedTime = measureTimeMillis {
                conn.createStatement().use { statement ->
                    val row = statement.executeQuery(
                        "SELECT 1"
                    )

                }
            }
            logger.info("Time $elapsedTime")
        }




    }
}