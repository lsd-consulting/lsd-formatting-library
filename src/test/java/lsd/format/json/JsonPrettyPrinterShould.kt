package lsd.format.json

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class JsonPrettyPrinterShould {
    @Test
    fun `indent json`() {
        val result = indentJson("{\"key\":\"value\"}")
        assertThat(result, notNullValue())
        assertThat(result, `is`("{\n  \"key\": \"value\"\n}"))
    }

    @Test
    fun `inline array elements`() {
        val result = indentJson("{\"array\":[\"a1\",\"a2\",\"a3\"]}")
        assertThat(result, notNullValue())
        assertThat(result, `is`("{\n  \"array\": [ \"a1\", \"a2\", \"a3\" ]\n}"))
    }

    @Test
    fun `handle empty string`() {
        val result = indentJson(null)
        assertThat(result, nullValue())
    }

    @Test
    fun `handle non json`() {
        val result = indentJson("< >")
        assertThat(result, nullValue())
    }

    @Test
    fun `handle top level array`() {
        val result = indentJson("[{\"key1\":\"value1\", \"key2\":\"value2\"}]\n")
        assertThat(result, notNullValue())
        assertThat(result, equalTo("[ {\n  \"key1\": \"value1\",\n  \"key2\": \"value2\"\n} ]"))
    }

    @Test
    fun `handle broken top level array`() {
        val result = indentJson("[{\"key1\":\"value1\", \"key2\":\"value2\"}[][]]\n")
        assertThat(result, nullValue())
    }
}
