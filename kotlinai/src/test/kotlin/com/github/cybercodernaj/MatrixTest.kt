package com.github.cybercodernaj

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows


class MatrixTest {

    @Test
    fun `empty matrix will return error`() {
        assertThrows<IllegalArgumentException> {
            matrix {

            }
        }
    }

    @Test
    fun `irregular row will return error`() {
        assertThrows<IllegalArgumentException> {
            matrix {
                row(1)
                row(1, 4)
            }
        }
    }

    @Test
    fun `two identical matrices returns true on equality`() {
        val A = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }
        assertTrue(A == B)
    }

    @Test
    fun `two matrices of different order returns false on equality`() {
        val A = matrix {
            row(4, 5)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }
        assertFalse(A == B)
    }

    @Test
    fun `two different matrices returns false on equality`() {
        val A = matrix {
            row(8, -5)
            row(3, -1)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }
        assertFalse(A == B)
    }
}