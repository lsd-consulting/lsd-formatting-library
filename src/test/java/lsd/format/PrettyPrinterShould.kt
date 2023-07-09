package lsd.format

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import lsd.format.json.objectMapper
import org.approvaltests.Approvals
import org.approvaltests.Approvals.verify
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
    fun formatJson() {
        verify(prettyPrintJson(readDocument("/source/source.json")), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun formatJsonFromBytes() {
        verify(prettyPrintJson(readDocument("/source/source.json").toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun formatJsonFromObject() {
        verify(prettyPrintJson(ExampleObject(2)), options)
    }

    @Test
    fun formatEmptyString() {
        verify(prettyPrintJson(""), options)
    }

    @Test
    fun formatNullValue() {
        verify(prettyPrintJson(null), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun formatJsonInString() {
        verify(prettyPrintJson(readDocument("/source/source.json")), options)
    }

    @Test
    fun formatJsonInEmptyString() {
        verify(prettyPrintJson(""), options)
    }

    @Test
    fun formatJsonInNullValue() {
        verify(prettyPrintJson(null), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun formatJsonInByteArray() {
        verify(prettyPrintJson(readDocument("/source/source.json").toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun formatEmptyJsonByteArray() {
        verify(prettyPrintJson("".toByteArray(StandardCharsets.UTF_8)), options)
    }

    @Test
    fun formatArrayOfObjects() {
        verify(prettyPrintJson(arrayOf<Any>(Any())), options)
    }

    @Test
    fun formatEmptyArrayOfObjects() {
        verify(prettyPrintJson(arrayOf<Any>()), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun formatTopLevelJsonArray() {
        verify(prettyPrintJson(readDocument("/source/topLevelArray.json")), options)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun formatXml() {
        Approvals.verifyXml(prettyPrintJson(readDocument("/source/source.xml")))
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun returnOriginalIfNeitherJsonNorXml() {
        verify(prettyPrintJson(readDocument("/source/source.txt")))
    }

    @Test
    fun serialiseObjectToJson() {
        verify(prettyPrintJson(ExampleObject(2)), options)
    }

    @Test
    fun convertByteArrayFieldToString() {
        val objects = byteArrayExamples()
            .map { ExampleObjectWithBytes(it) }
            .map { prettyPrintJson(it) }
            .toArray()
        Approvals.verifyAll("an object with bytes[] field.", objects)
    }

    @Test
    fun handleSerialisationFailure() {
        mockkStatic("lsd.format.json.ObjectMapperCreatorKt")

        every { objectMapper } throws Exception("Blah")

        verify(prettyPrintJson(ExampleObject()), options)
    }

    private fun byteArrayExamples(): Stream<ByteArray?> {
        return Stream.of(
            "".toByteArray(),
            " ".toByteArray(),
            "some regular text".toByteArray(),
            "{looks like json}".toByteArray(),
            "{\"name\":\"Bond\", \"age\":164, \"hungry\":true}".toByteArray(),
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
