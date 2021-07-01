package com.github.cybercodernaj

import kotlin.math.*

@Suppress("LocalVariableName", "FunctionName")
class Matrix internal constructor(precision: Int) {
    internal val rows = arrayListOf<DoubleArray>()

    internal val m: Int
        get() = rows.size

    internal var n = -1

    private val indices: IntRange
        get() = rows.indices

    private val precision: Double
        get() = 10.0.pow(field)

    init {
        if (precision < 0)
            throw IllegalArgumentException("Cannot have negative precision")

        this.precision = precision.toDouble()
    }

    companion object {
        fun I(size: Int): Matrix {
            val M = O(size)
            M.forEachElement { i, j ->
                M[i][j] = if (i == j) 1.0 else 0.0
            }
            return M
        }

        fun O(size: Int) = Matrix(size, size)
    }

    private constructor(m: Int, n: Int, precision: Int = 2) : this(precision) {
        if (m < 1 || n < 1)
            throw IllegalArgumentException("Cannot have non-positive size of matrix")

        repeat(m) {
            rows.add(DoubleArray(n))
        }
        this.n = n
    }

    fun row(vararg elements: Double): DoubleArray {
        assertRowConditions(elements)

        val row = doubleArrayOf(*elements)
        rows.add(row)
        return row
    }

    fun row(vararg elements: Int): DoubleArray {
        assertRowConditions(elements.map { it.toDouble() }.toDoubleArray())

        val row = doubleArrayOf(*(elements.map { it.toDouble() }.toDoubleArray()))
        rows.add(row)
        return row
    }

    operator fun plus(other: Matrix): Matrix {
        assertEqualOrder(other)

        val S = Matrix(m, n)
        forEachElement { i, j ->
            S[i][j] = this[i][j] + other[i][j]
        }
        return S
    }

    operator fun minus(other: Matrix): Matrix {
        assertEqualOrder(other)

        val S = Matrix(m, n)
        forEachElement { i, j ->
            S[i][j] = this[i][j] - other[i][j]
        }
        return S
    }

    operator fun unaryMinus() = -1 * this

    operator fun times(other: Double): Matrix {
        val S = Matrix(m, n)

        forEachElement { i, j ->
            S[i][j] = round(other * this[i][j] * precision) / precision
        }
        return S
    }

    operator fun times(other: Int) = this * other.toDouble()

    operator fun times(other: Matrix): Matrix {
        if (this.n != other.m)
            throw IllegalArgumentException("Cannot multiply matrices of incompatible order")

        val S = Matrix(this.m, other.n)
        S.forEachElement { i, j ->
            for (k in 0 until n)
                S[i][j] += round(this[i][k] * other[k][j] * precision) / precision
        }
        return S
    }

    fun transpose(): Matrix {
        val S = Matrix(n, m)

        forEachElement { i, j ->
            S[j][i] = this[i][j]
        }
        return S
    }

    fun isSymmetric() = this == transpose()

    fun isSkewSymmetric() = this == -transpose()

    fun determinant(): Double {
        assertSquare()

        if (m == 1)
            return this[0][0]

        var det = 0.0
        for (i in indices)
            det += ((-1.0).pow(i)) * this[0][i] * minor(0, i).determinant()

        return det
    }

    fun isSingular() = determinant() == 0.0

    internal fun minor(_i: Int, _j: Int): Matrix  {
        val S = Matrix(m - 1, n - 1)

        var y = 0
        var x = 0
        for (i in indices) {
            for (j in rows[i].indices)
                if (_i != i && _j != j)
                    S[x][y++] = this[i][j]
            if (y == S.rows.size) {
                x++
                y = 0
            }
        }

        return S
    }

    fun adjoint(): Matrix {
        assertSquare()

        val S = Matrix(m, n)
        S.forEachElement { i, j ->
            S[i][j] = ((-1.0).pow(i + j)) * minor(i, j).determinant()
        }

        return S.transpose()
    }

    fun inverse(): Matrix {
        if (isSingular())
            throw ArithmeticException("Cannot find inverse of singular matrix")

        return (1 / determinant()) * adjoint()
    }

    infix fun orderEqual(other: Matrix) =
        this.m == other.m && this.n == other.n

    infix fun orderNotEqual(other: Matrix) =
        !(this orderEqual other)

    private inline fun forEachElement(block: (i: Int, j: Int) -> Unit) {
        for (i in indices)
            for (j in rows[i].indices)
                block(i, j)
    }

    private fun assertEqualOrder(other: Matrix) {
        if (this orderNotEqual other)
            throw IllegalArgumentException("Cannot perform operation on matrices of different order")
    }

    private fun assertRowConditions(elements: DoubleArray) {
        if (elements.isEmpty())
            throw IllegalArgumentException("Cannot add empty row")

        if (n == -1)
            n = elements.size

        if (elements.size != n)
            throw IllegalArgumentException("Invalid matrix")
    }

    private fun assertSquare() {
        if (this.m != this.n)
            throw IllegalStateException("Matrix must be a square matrix")
    }

    operator fun get(position: Int) = rows[position]

    override operator fun equals(other: Any?): Boolean {
        if (other !is Matrix)
            return false

        if (this.m != other.m || this.n != other.n)
            return false

        forEachElement { i, j ->
            if (this[i][j] != other[i][j])
                return false
        }

        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in indices) {
            for (j in rows[i].indices) {
                val element = if (ceil(this[i][j]) == floor(this[i][j]))
                    this[i][j].toInt()
                else
                    this[i][j]

                sb.append(element)
                if (j != n - 1)
                    sb.append("\t")
                else sb.append("\n")
            }
        }
        return sb.toString()
    }

    override fun hashCode(): Int {
        var result = rows.hashCode()
        result = 31 * result + m
        result = 31 * result + n
        return result
    }
}