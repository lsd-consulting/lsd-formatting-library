package lsd.format.xml

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class XmlPrettyPrinterShould {
    @Test
    fun `indent xml`() {
        val result =
            indentXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?><parent attribute=\"abc\"><child>value</child></parent>")
        assertThat(result, notNullValue())
        assertThat(
            result,
            `is`("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<parent attribute=\"abc\">\n  <child>value</child>\n</parent>\n")
        )
    }

    @Test
    fun `handle empty string`() {
        val result = indentXml(null)
        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun `handle non xml`() {
        val result = indentXml("blah")
        assertThat(result, `is`(nullValue()))
    }
}