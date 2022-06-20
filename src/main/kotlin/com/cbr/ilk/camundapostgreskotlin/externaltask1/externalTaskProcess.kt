package com.cbr.ilk.camundapostgreskotlin.externaltask1

import com.cbr.ilk.camundapostgreskotlin.jdbc.JDBCRecord
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine
import org.camunda.bpm.engine.variable.Variables
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


private val processEngine: ProcessEngine? = null

@Component
class externalTaskProcess : ExternalTaskHandler {
    private val logger = LoggerFactory.getLogger(externalTaskProcess::class.java)

    override fun execute(extTask: ExternalTask, extTaskService: ExternalTaskService) {
        var processes = JDBCRecord ()
        val processesList = processes.startProcesses(100)
        processesList.forEach { process ->
            logger.info("New_handled_process= " +
                    "message_uuid=${process.message_uuid}, " +
                    "created=${process.created} " +
                    "payload=${process.payload} " +
                    "message_type=${process.message_type}")
//            val processInstance: ProcessInstance = processEngine().runtimeService
//                .startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
//                    process.message_type.toString(), //processDefinitionKey
//                    process.message_uuid.toString(), //businessKey
//                    Variables.createVariables().putValueTyped("payload", Variables.stringValue(process.payload)
//                    )
//                )
        var pi = processEngine?.runtimeService?.startProcessInstanceByKey(PROCESS_DEFINITION_KEY)
            extTaskService.complete(extTask)
            logger.info("ProcessInstanceId = {}", pi?.processInstanceId)
        }




    }

    companion object {
        private const val PROCESS_DEFINITION_KEY =  "Process_select1"//"SampleExternal"
    }
}

//@Component
//class ProcessExternalTask : JavaDelegate {
//
//    private val logger = LoggerFactory.getLogger(ProcessExternalTask::class.java)
//    private val PROCESS_KEY = "Process_027hatc"

//    override fun execute(execution: DelegateExecution?) {
//        var processes = JDBCRecord ()
//        val processesList = processes.startProcesses()
//        processesList.forEach { process ->
//            logger.info("New_handled_process= " +
//                    "message_uuid=${process.message_uuid}, " +
//                    "created=${process.created} " +
//                    "payload=${process.payload} " +
//                    "message_type=${process.message_type}")
//
//                execution!!.processEngineServices.runtimeService.startProcessInstanceByKey(
//                    PROCESS_KEY,
////                    execution?.processInstanceId,
//                    process.message_type.toString(), //processDefinitionKey
//                    process.message_uuid.toString(), //businessKey
//                    Variables.createVariables().putValueTyped("payload", Variables.stringValue(process.payload)) //variables
//                )
//
//        }
//        logger.info("ProcessInstanceId = {}", execution?.processInstanceId)
//    }
//}