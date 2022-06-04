import org.apache.ibatis.logging.LogFactory
import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.impl.util.LogUtil
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests
import org.camunda.bpm.engine.variable.value.ObjectValue
//import org.joda.money.CurrencyUnit
//import org.joda.money.Money
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
class InMemoryH2Test {
    @Rule
    var rule = ProcessEngineRule()
    @Before
    fun setup() {
        AbstractAssertions.init(rule.processEngine)
    }

    /**
     * Just tests if the process definition is deployable.
     */
    @Test
//    @Deployment(resources = ["process.bpmn"])
    fun testParsingAndDeployment() {
        // nothing is done here, as we just want to check for exceptions during deployment
    }

    @Test
    @Deployment(resources = ["process.bpmn"])
    fun testProductDeserialize() {
//        val product: org.gradle.swiftpm.Product = org.gradle.swiftpm.Product()
//        product.setName("Praxishandbuch BPMN")
//        product.setPrice(Money.of(CurrencyUnit.EUR, BigDecimal("34.99")))
//        product.setDateCreated(LocalDate.parse("2015-03-01"))
        val pi = BpmnAwareTests.runtimeService().startProcessInstanceByKey(
            PROCESS_DEFINITION_KEY)

//        BpmnAwareAssertions.assertThat(pi).isWaitingAt("UserTask_1")
//        val jsonProduct =
//            BpmnAwareTests.runtimeService().getVariableTyped<ObjectValue>(pi.processInstanceId, "product", false)
//        Assertions.assertThat(jsonProduct.valueSerialized).contains("EUR 34.99").contains("2015-03-01")
//        val deserializedProduct = BpmnAwareTests.runtimeService().getVariable(pi.processInstanceId, "product")
//        Assertions.assertThat(deserializedProduct).isEqualToComparingFieldByField(product)
    }

    companion object {
        private const val PROCESS_DEFINITION_KEY = "extended-serialization"

        // enable more detailed logging
        init {
            LogUtil.readJavaUtilLoggingConfigFromClasspath() // process engine
            LogFactory.useJdkLogging() // MyBatis
        }
    }
}