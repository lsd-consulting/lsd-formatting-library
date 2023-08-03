package lsd.format.json

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class JsonFormatCheckerShould {

    @Test
    fun identifyValidJson() {
        assertTrue(isValidJson("{}"))
    }

    @Test
    fun identifyValidJsonArray() {
        assertTrue(isValidJson("[]"))
    }

    @Test
    fun identifyInvalidJsonArray() {
        assertFalse(isValidJson("{"))
    }
}
