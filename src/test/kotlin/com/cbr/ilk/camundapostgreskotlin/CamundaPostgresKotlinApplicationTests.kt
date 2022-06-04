package com.cbr.ilk.camundapostgreskotlin

import junit.framework.TestCase.assertEquals
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.junit.Rule
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CamundaPostgresKotlinApplicationTests {


	@Rule
	var processEngineRule: ProcessEngineRule = ProcessEngineRule()

	@Test
	fun contextLoads() {
//		val processInstance: ProcessInstance =
//			processEngine().getRuntimeService().startProcessInstanceByKey("c861fecf-d927-11ec-9194-f0038c8a8ad9")
//		println("Started process instance $processInstance")
	}

	@org.junit.Test
	@Deployment(resources = ["process.bpmn"])
	fun testParsingAndDeployment() {
		// nothing is done here, as we just want to check for exceptions during deployment
	}

//	@Tag("slow")
//	@Test
//	fun testAddMaxInteger() {
//		assertEquals(2147483646, Integer.sum(2147183646, 300000))
//	}
}



