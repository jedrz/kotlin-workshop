package pl.touk.kotlin

import org.junit.Test

class ConditionsTest {

    @Test
    fun `should pprint`() {
        val conditions = conditions {

            condition("order") {
                present("registered")
                absent("ordered")
                absent("creditCard")
            }

            condition("payment") {
                present("registered")
                present("ordered")
                absent("creditCard")
            }

            condition("order again") {
                absent("ordered")
                anyOf {
                    present("registered")
                    present("credit card")
                }
            }
        }

        println(conditions)
    }
}