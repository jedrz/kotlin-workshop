package pl.touk.kotlin


enum class Status {
    ORDER,
    PAYMENT,
    ORDER_AGAIN
}


enum class Attribute {
    REGISTERED,
    ORDERED,
    CREDIT_CARD
}


class Conditions(private val conditions: Collection<Condition>) {
    override fun toString(): String {
        return "Conditions(conditions=${pprint(conditions)})"
    }
}


fun conditions(init: ConditionsBuilder.() -> Unit): Conditions {
    val builder = ConditionsBuilder()
    builder.init()
    return builder.build()
}


class ConditionsBuilder {
    private val conditions: MutableList<Condition> = mutableListOf()

    fun condition(status: Status, init: RequirementsBuilder.() -> Unit) {
        val requirementsBuilder = RequirementsBuilder()
        requirementsBuilder.init()
        conditions.add(requirementsBuilder.buildCondition(status))
    }

    internal fun build() = Conditions(conditions)
}


class Condition(
        val status: Status,
        private val requirements: Collection<Requirement>
) {
    override fun toString(): String {
        return "Condition(status='$status', requirements=${pprint(requirements, elementPrefix = "\t")})"
    }
}


class RequirementsBuilder {
    private val requirements: MutableList<Requirement> = mutableListOf()

    fun present(attribute: Attribute) {
        requirements.add(AttributeRequirement(attribute, present = true))
    }

    fun absent(attribute: Attribute) {
        requirements.add(AttributeRequirement(attribute, present = false))
    }

    fun anyOf(init: RequirementsBuilder.() -> Unit) {
        val anyOfBuilder = RequirementsBuilder()
        anyOfBuilder.init()
        requirements.add(anyOfBuilder.buildAnyOfRequirement())
    }

    internal fun buildCondition(status: Status) = Condition(status, requirements)

    internal fun buildAnyOfRequirement() = AnyOfRequirement(requirements)
}


interface Requirement


class AttributeRequirement(private val attribute: Attribute, private val present: Boolean) : Requirement {
    override fun toString(): String {
        return "AttributeRequirement(attribute=$attribute, present=$present)"
    }
}


class AnyOfRequirement(private val requirements: Collection<Requirement>) : Requirement {
    override fun toString(): String {
        return "AnyOfRequirement(requirements=${pprint(requirements, elementPrefix = "\t\t")})"
    }
}

fun <T> pprint(c: Collection<T>, elementPrefix: String = ""): String {
    return c.joinToString(separator = "\n", prefix = "[\n", postfix = "]") { s ->
        elementPrefix + s
    }
}
