package lsd.format

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import lsd.format.json.objectMapper
import org.approvaltests.Approvals.*
import org.approvaltests.core.Options
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.net.URISyntaxException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream


internal class PrettyPrinterShould {
    private val options = Options().forFile().withExtension(".json")

    @AfterEach
    fun setup() {
        unmockkAll()
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `format json`() {
        verify(prettyPrint(readDocument("/source/flattened.json")), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `format json from bytes`() {
        verify(prettyPrint(readDocument("/source/flattened.json").toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun `format json from object`() {
        verify(prettyPrint(ExampleObject(2)), options)
    }

    @Test
    fun `format empty string`() {
        verify(prettyPrint(""), options)
    }

    @Test
    fun `format blank string`() {
        verify(prettyPrint(" "), options)
    }

    @Test
    fun `format null value`() {
        verify(prettyPrint(null), options)
    }

    @Test
    fun `format empty json byte array`() {
        verify(prettyPrint("".toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun `format blank json byte array`() {
        verify(prettyPrint(" ".toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun `format array of objects`() {
        verify(prettyPrint(arrayOf(Any())), options)
    }

    @Test
    fun `format empty array of objects`() {
        verify(prettyPrint(arrayOf<Any>()), options)
    }

    @Test
    fun `handle array of objects`() {
        verify(prettyPrint(arrayOf(ExampleObject(1), ExampleObject(2))), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `format top level json array`() {
        verify(prettyPrint(readDocument("/source/flattenedTopLevelArray.json")), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `format xml`() {
        verifyXml(prettyPrint(readDocument("/source/flattened.xml")))
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `format xml with multiline header`() {
        verify(prettyPrint(readDocument("/source/flattenedMultilineHeader.xml")))
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun `return original if neither json nor xml`() {
        verify(prettyPrint(readDocument("/source/source.txt")))
    }

    @Test
    fun `convert byte array field to string`() {
        val objects = byteArrayExamples()
            .map { ExampleObjectWithBytes(it) }
            .map { prettyPrint(it) }
            .toArray()
        verifyAll("an object with bytes[] field.", objects)
    }

    @Test
    fun `handle serialisation failure`() {
        mockkStatic("lsd.format.json.ObjectMapperCreatorKt")

        every { objectMapper } throws Exception("Blah")

        verify(prettyPrint(ExampleObject()), options)
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

    @Throws(IOException::class, URISyntaxException::class)
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
