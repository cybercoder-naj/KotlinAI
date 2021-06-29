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

    @Test
    fun `add two matrices`() {
        val A = matrix {
            row(8, -5)
            row(3, -1)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }

        val C = A + B

        val requiredC = matrix {
            row(12, 0)
            row(3.3, -4.0)
        }
        assertTrue(C == requiredC)
    }

    @Test
    fun `subtract two matrices`() {
        var A = matrix {
            row(8, -5)
            row(3, -1)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }

        A -= B

        val requiredA = matrix {
            row(4, -10)
            row(2.7, 2.0)
        }
        assertTrue(A == requiredA)
    }

    @Test
    fun `multiply matrix with scalar`() {
        val A = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }

        val B = 2 * A

        val requiredB = matrix {
            row(8, 10)
            row(0.6, -6.0)
        }

        assertTrue(B == requiredB)
    }

    @Test
    fun `matrix with same order returns true on order test`() {
        val A = matrix {
            row(8, -5)
            row(3, -1)
        }
        val B = matrix {
            row(4, 5)
            row(0.3, -3.0)
        }

        val C = A + B

        assertTrue(A orderEqual  B)
        assertTrue(B orderEqual  C)
        assertFalse(A orderNotEqual C)
    }

    @Test
    fun `multiply two matrices`() {
        val A = matrix {
            row(-2, 3)
            row(3, -4)
        }

        val X = matrix {
            row(4)
            row(-1)
        }

        val B = A * X

        val requiredB = matrix {
            row(-11)
            row(16)
        }

        assertTrue(B == requiredB)

        assertThrows<IllegalArgumentException> {
            X * A
        }
    }
}