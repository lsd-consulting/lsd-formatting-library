package lsd.format.xml

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class XmlFormatCheckerShould {

    @Test
    fun identifyValidXml() {
        assertTrue(isValidXml("<a></a>"))
    }

    @Test
    fun identifyInvalidXml() {
        assertFalse(isValidXml("<>"))
    }
}
