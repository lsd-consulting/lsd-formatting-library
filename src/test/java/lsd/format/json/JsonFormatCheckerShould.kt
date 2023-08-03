package lsd.format.json

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JsonFormatCheckerShould {

    @Test
    fun `identify valid json`() {
        assertTrue(isValidJson("{}"))
    }

    @Test
    fun `identify valid json array`() {
        assertTrue(isValidJson("[]"))
    }

    @Test
    fun `identify invalid json array`() {
        assertFalse(isValidJson("{"))
    }

    @Test
    fun `identify empty value as invalid json`() {
        assertFalse(isValidJson(""))
    }
}
