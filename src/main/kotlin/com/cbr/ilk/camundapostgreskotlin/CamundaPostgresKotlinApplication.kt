package com.cbr.ilk.camundapostgreskotlin

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import com.cbr.ilk.camundapostgreskotlin.jdbc.JDBCRecord

@EnableProcessApplication
@SpringBootApplication
class CamundaPostgresKotlinApplication

fun main(args: Array<String>) {

//		var processes = JDBCRecord ()
//		val processesList = processes.startProcesses()
//		processesList.forEach { process ->
//				println("Unhandled_process= " +
//						"message_uuid=${process.message_uuid} " +
//						"created=${process.created} " +
//						"payload=${process.payload} " +
//						"message_type=${process.message_type}")
//			}
	SpringApplication.run(CamundaPostgresKotlinApplication::class.java)
}


