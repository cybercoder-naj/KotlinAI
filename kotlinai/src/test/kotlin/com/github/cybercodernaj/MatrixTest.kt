package com.github.cybercodernaj

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows


@Suppress("LocalVariableName")
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

        assertEquals(A, B)
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
        assertNotEquals(A, B)
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
        assertNotEquals(A, B)
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
        assertEquals(requiredC, C)
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
        assertEquals(requiredA, A)
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

        assertEquals(requiredB, B)
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

        assertTrue(A orderEqual B)
        assertTrue(B orderEqual C)
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

        assertEquals(requiredB, B)

        assertThrows<IllegalArgumentException> {
            X * A
        }
    }

    @Test
    fun `find the transpose of matrix`() {
        val A = matrix {
            row(4)
            row(-1)
        }

        val B = transpose(A)

        val requiredB = matrix {
            row(4, -1)
        }

        assertEquals(requiredB, B)

        val C = matrix {
            row(-2, 7)
            row(3, -4)
        }

        val D = C.transpose()

        val requiredD = matrix {
            row(-2, 3)
            row(7, -4)
        }

        assertEquals(requiredD, D)
    }

    @Test
    fun `negating a matrix`() {
        val A = matrix {
            row(-2, 7, 9)
            row(3, -4, 0)
            row(0, 1, -8)
        }

        val B = -A

        val requiredB = matrix {
            row(2, -7, -9)
            row(-3, 4, 0)
            row(0, -1, 8)
        }

        assertEquals(requiredB, B)
    }

    @Test
    fun `determinant of 3 x 3 matrix`() {
        val A = matrix {
            row(1, 3, 2)
            row(-3, -1, -3)
            row(2, 3, 1)
        }

        val modA = det(A)

        assertEquals(-15.0, modA)
    }

    @Test
    fun `find minor of a matrix`() {
        val A = matrix {
            row(1, 3, 2)
            row(-3, -1, -3)
            row(2, 3, 1)
        }

        val minA = A.minor(0, 0)

        val requiredA = matrix {
            row(-1, -3)
            row(3, 1)
        }

        assertEquals(requiredA, minA)

        val B = A.minor(1, 1)

        val requiredB = matrix {
            row(1, 2)
            row(2, 1)
        }

        assertEquals(requiredB, B)
    }

    @Test
    fun `determinant of non square throws error`() {
        val A = matrix {
            row(1, 3, 2)
            row(-3, -1, -3)
        }

        assertThrows<IllegalStateException> {
            A.determinant()
        }
    }

    @Test
    fun `Zero Matrix of size 4`() {
        val A = Matrix.O(4)

        val B = matrix {
            row(0, 0, 0, 0)
            row(0, 0, 0, 0)
            row(0, 0, 0, 0)
            row(0, 0, 0, 0)
        }

        assertEquals(A, B)
    }

    @Test
    fun `Identity matrix of size 3`() {
        val A = Matrix.I(3)

        val B = matrix {
            row(1, 0, 0)
            row(0, 1, 0)
            row(0, 0, 1)
        }

        assertEquals(A, B)
    }

    @Test
    fun `Adjoint of non-square matrix throws error`() {
        val A = matrix {
            row(1, 0, 4)
            row(-2, 1, 0)
        }

        assertThrows<IllegalStateException> {
            A.adjoint()
        }
    }

    @Test
    fun `finding adjoint of matrix`() {
        val A = matrix {
            row(8, -6, 2)
            row(-6, 7, -4)
            row(2, -4, 3)
        }

        val adjA = adj(A)

        val requiredMatrix = matrix {
            row(5, 10, 10)
            row(10, 20, 20)
            row(10, 20, 20)
        }

        assertEquals(requiredMatrix, adjA)
    }

    @Test
    fun `symmetric matrix will return true`() {
        val A = matrix {
            row(1, 4, 2)
            row(4, -1, 5)
            row(2, 5, -3)
        }

        assertTrue(A.isSymmetric())
    }

    @Test
    fun `skew symmetric matrix will return true`() {
        val A = matrix {
            row(0, 4, -2)
            row(-4, 0, 3)
            row(2, -3, 0)
        }

        assertTrue(A.isSkewSymmetric())
    }

    @Test
    fun `singular matrix will return true`() {
        val A = matrix {
            row(3, 8, 1)
            row(-4, 1, 1)
            row(-4, 1, 1)
        }

        assertTrue(A.isSingular())
    }

    @Test
    fun `finding the inverse matrix`() {
        val A = matrix {
            row(4, 7)
            row(2, 6)
        }

        val invA = inv(A)

        val required = matrix {
            row(0.6, -0.7)
            row(-0.2, 0.4)
        }

        assertEquals(required, invA)
    }
}