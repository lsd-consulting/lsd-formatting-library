package lsd.format

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import lsd.format.json.objectMapper
import org.approvaltests.Approvals.verify
import org.approvaltests.Approvals.verifyAll
import org.approvaltests.core.Options
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream


internal class PrinterShould {
    private val options = Options().forFile().withExtension(".json")

    @AfterEach
    fun setup() {
        unmockkAll()
    }

    @Test
    fun flattenJson() {
        verify(printFlat(readDocument("/source/pretty.json")), options)
    }

    @Test
    fun flattenJsonFromBytes() {
        verify(printFlat(readDocument("/source/pretty.json").toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun flattenJsonFromObject() {
        verify(printFlat(ExampleObject(2)), options)
    }

    @Test
    fun handleEmptyString() {
        verify(printFlat(""), options)
    }

    @Test
    fun handleBlankString() {
        verify(printFlat(" "), options)
    }

    @Test
    fun handleNullValue() {
        verify(printFlat(null), options)
    }

    @Test
    fun handleEmptyJsonByteArray() {
        verify(printFlat("".toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun handleBlankJsonByteArray() {
        verify(printFlat(" ".toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun handleArrayOfEmptyObjects() {
        verify(printFlat(arrayOf(Any())), options)
    }

    @Test
    fun handleEmptyArrayOfObjects() {
        verify(printFlat(arrayOf<Any>()), options)
    }

    @Test
    fun handleArrayOfObjects() {
        verify(printFlat(arrayOf(ExampleObject(1), ExampleObject(2))), options)
    }

    @Test
    fun formatTopLevelJsonArray() {
        verify(printFlat(readDocument("/source/prettyTopLevelArray.json")), options)
    }

    @Test
    fun formatXml() {
        verify(printFlat(readDocument("/source/pretty.xml")))
    }

    @Test
    fun returnOriginalIfNeitherJsonNorXml() {
        verify(printFlat(readDocument("/source/source.txt")))
    }

    @Test
    fun convertByteArrayFieldToString() {
        val objects = byteArrayExamples()
            .map { ExampleObjectWithBytes(it) }
            .map { printFlat(it) }
            .toArray()
        verifyAll("an object with bytes[] field.", objects)
    }

    @Test
    fun handleSerialisationFailure() {
        mockkStatic("lsd.format.json.ObjectMapperCreatorKt")

        every { objectMapper } throws Exception("Blah")

        verify(printFlat(ExampleObject()), options)
    }

    private fun byteArrayExamples(): Stream<ByteArray?> {
        return Stream.of(
            "".toByteArray(),
            " ".toByteArray(),
            "some regular text".toByteArray(),
            "{looks like json}".toByteArray(),
            "{\"name\":\"Bond\", \"age\":164, \"hungry\":true}".toByteArray(),
            "[{\"name\":\"Bond\", \"age\":164, \"hungry\":true}]".toByteArray(),
            "<xml><x>hello</x><y>goodbye</y></xml>".toByteArray(),
            "<looks like xml>".toByteArray(),
            null, ByteArray(0), byteArrayOf(1, 2, 3),
        )
    }

    private fun readDocument(fileName: String): String {
        return Files.readString(
            Paths.get(Objects.requireNonNull(javaClass.getResource(fileName)).toURI()),
            StandardCharsets.UTF_8
        )
    }

    private data class ExampleObject(
        var value: Int = 0
    )

    private data class ExampleObjectWithBytes(
        val value: ByteArray?
    )
}
