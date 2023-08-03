package lsd.format

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Objects.requireNonNull

internal class JsonConverterShould {

    @Test
    fun `parse json`() {
        val result = convertJsonStringToMap(readDocument("/source/pretty.json"))
        assertThat(result, hasEntry("key", listOf("value  1", "value 2", "value3")))
    }

    @Test
    fun `return empty map if not json`() {
        val result = convertJsonStringToMap(readDocument("/source/source.txt"))
        assertThat(result, `is`(anEmptyMap()))
    }

    @Test
    fun `handle empty string`() {
        val result = convertJsonStringToMap("")
        assertThat(result, `is`(anEmptyMap()))
    }

    @Test
    fun `handle missing value`() {
        val result = convertJsonStringToMap(null)
        assertThat(result, `is`(anEmptyMap()))
    }

    private fun readDocument(fileName: String) =
        Files.readString(Paths.get(requireNonNull(javaClass.getResource(fileName)).toURI()), UTF_8)
}
