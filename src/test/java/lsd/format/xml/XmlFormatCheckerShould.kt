package lsd.format.xml

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class XmlFormatCheckerShould {

    @Test
    fun `identify valid xml`() {
        assertTrue(isValidXml("<a></a>"))
    }

    @Test
    fun `identify invalid xml`() {
        assertFalse(isValidXml("<>"))
    }

    @Test
    fun `identify empty value as invalid xml`() {
        assertFalse(isValidXml(""))
    }
}
