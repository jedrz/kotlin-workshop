package pl.touk.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

fun addMaybe1(value: Any?) =
        if (value is Int) value.toLong() + 1 else value

class Test {

    @Test
    fun test() {
        assertThat(addMaybe1(1)).isEqualTo(2L)
    }
}