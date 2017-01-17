package pl.touk.kotlin

import org.junit.Test

class ConditionsTest {

    @Test
    fun `should pprint`() {
        val conditions = conditions {

            condition(Status.ORDER) {
                present(Attribute.REGISTERED)
                absent(Attribute.ORDERED)
                absent(Attribute.CREDIT_CARD)
            }

            condition(Status.PAYMENT) {
                present(Attribute.REGISTERED)
                present(Attribute.ORDERED)
                absent(Attribute.CREDIT_CARD)
            }

            condition(Status.ORDER_AGAIN) {
                absent(Attribute.ORDERED)
                anyOf {
                    present(Attribute.REGISTERED)
                    present(Attribute.CREDIT_CARD)
                }
            }
        }

        println(conditions)
    }
}