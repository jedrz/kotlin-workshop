package pl.touk.kotlin


class Conditions(private val conditions: Collection<Condition>) {
    override fun toString(): String {
        return pprint(conditions)
    }
}


fun conditions(init: ConditionsBuilder.() -> Unit): Conditions {
    val builder = ConditionsBuilder()
    builder.init()
    return builder.build()
}


class ConditionsBuilder {
    private val conditions: MutableList<Condition> = mutableListOf()

    fun condition(checkpoint: String, init: RequirementsBuilder.() -> Unit) {
        val requirementsBuilder = RequirementsBuilder()
        requirementsBuilder.init()
        conditions.add(requirementsBuilder.buildCondition(checkpoint))
    }

    internal fun build() = Conditions(conditions)
}


class Condition(
        val checkpoint: String,
        private val requirements: Collection<Requirement>
) {
    override fun toString(): String {
        return "Condition(checkpoint='$checkpoint', requirements=${pprint(requirements, elementPrefix = "\t")})"
    }
}


class RequirementsBuilder {
    private val requirements: MutableList<Requirement> = mutableListOf()

    fun present(name: String) {
        requirements.add(AttributeRequirement(name, present = true))
    }

    fun absent(name: String) {
        requirements.add(AttributeRequirement(name, present = false))
    }

    fun anyOf(init: RequirementsBuilder.() -> Unit) {
        val anyOfBuilder = RequirementsBuilder()
        anyOfBuilder.init()
        requirements.add(anyOfBuilder.buildAnyOfRequirement())
    }

    internal fun buildCondition(checkpoint: String) = Condition(checkpoint, requirements)

    internal fun buildAnyOfRequirement() = AnyOfRequirement(requirements)
}


interface Requirement


class AttributeRequirement(private val name: String, private val present: Boolean) : Requirement {
    override fun toString(): String {
        return "AttributeRequirement(name='$name', present=$present)"
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