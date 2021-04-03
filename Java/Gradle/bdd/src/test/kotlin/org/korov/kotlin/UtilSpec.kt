import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals
import org.korov.kotlin.Util


class UtilFeature : Spek({

    Feature("test util") {
        Scenario("test length") {
            var value: String = ""
            When("aaa") {
                value = "aaa"
            }

            Then("should equal 3") {
                assertEquals(3, Util.length(value), "length error")
            }
        }

        Scenario("test length1") {
            var value: String = ""
            Given("aaaa") {
                value = "aaaa"
            }

            Then("should equal 4") {
                assertEquals(4, Util.length(value), "length error")
            }
        }
    }
})
