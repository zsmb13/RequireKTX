package co.zsmb.requirektx

import androidx.core.bundle.Bundle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class BundlePrimitivesTest : RoboTest() {

    private val testBundle = Bundle().apply {
        putBoolean("boolean", true)
        putByte("byte", 1.toByte())
        putChar("char", 'a')
        putDouble("double", 1.0)
        putFloat("float", 1f)
        putLong("long", 1L)
        putInt("int", 1)
        putShort("short", 1.toShort())

        // Example for invalid types
        putString("string", "a")
    }

    @Test
    fun testBoolean() {
        assertEquals(true, testBundle.getBooleanOrNull("boolean"))
        assertEquals(null, testBundle.getBooleanOrNull("string"))
        assertEquals(null, testBundle.getBooleanOrNull("invalid"))

        assertEquals(true, testBundle.requireBoolean("boolean"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireBoolean("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireBoolean("string")
        }
    }

    @Test
    fun testByte() {
        assertEquals(1.toByte(), testBundle.getByteOrNull("byte"))
        assertEquals(null, testBundle.getByteOrNull("string"))
        assertEquals(null, testBundle.getByteOrNull("invalid"))

        assertEquals(1.toByte(), testBundle.requireByte("byte"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireByte("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireByte("string")
        }
    }

    @Test
    fun testChar() {
        assertEquals('a', testBundle.getCharOrNull("char"))
        assertEquals(null, testBundle.getCharOrNull("string"))
        assertEquals(null, testBundle.getCharOrNull("invalid"))

        assertEquals('a', testBundle.requireChar("char"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireChar("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireChar("string")
        }
    }

    @Test
    fun testDouble() {
        assertEquals(1.0, testBundle.getDoubleOrNull("double"))
        assertEquals(null, testBundle.getDoubleOrNull("string"))
        assertEquals(null, testBundle.getDoubleOrNull("invalid"))

        assertEquals(1.0, testBundle.requireDouble("double"), 0.0)
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireDouble("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireDouble("string")
        }
    }

    @Test
    fun testFloat() {
        assertEquals(1f, testBundle.getFloatOrNull("float"))
        assertEquals(null, testBundle.getFloatOrNull("string"))
        assertEquals(null, testBundle.getFloatOrNull("invalid"))

        assertEquals(1f, testBundle.requireFloat("float"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireFloat("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireFloat("string")
        }
    }

    @Test
    fun testInt() {
        assertEquals(1, testBundle.getIntOrNull("int"))
        assertEquals(null, testBundle.getIntOrNull("string"))
        assertEquals(null, testBundle.getIntOrNull("invalid"))

        assertEquals(1, testBundle.requireInt("int"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireInt("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireInt("string")
        }
    }

    @Test
    fun testLong() {
        assertEquals(1L, testBundle.getLongOrNull("long"))
        assertEquals(null, testBundle.getLongOrNull("string"))
        assertEquals(null, testBundle.getLongOrNull("invalid"))

        assertEquals(1L, testBundle.requireLong("long"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireLong("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireLong("string")
        }
    }

    @Test
    fun testShort() {
        assertEquals(1.toShort(), testBundle.getShortOrNull("short"))
        assertEquals(null, testBundle.getShortOrNull("string"))
        assertEquals(null, testBundle.getShortOrNull("invalid"))

        assertEquals(1.toShort(), testBundle.requireShort("short"))
        assertFailsWith<IllegalArgumentException> {
            testBundle.requireShort("invalid")
        }
        assertFailsWith<IllegalStateException> {
            testBundle.requireShort("string")
        }
    }

}
