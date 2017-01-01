package pl.touk.kotlin

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

data class Developer(val firstName: String)

class DataClassTest {

    @Test
    fun `should be mocked`() {
        val dev = mock<Developer>()
        whenever(dev.firstName).thenReturn("name")

        assertThat(dev.firstName).isEqualTo("name")

        wit
    }
}