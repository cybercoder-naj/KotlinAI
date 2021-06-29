package com.github.cybercodernaj

import kotlin.math.ceil
import kotlin.math.floor

class Matrix internal constructor() {
    internal val rows = arrayListOf<DoubleArray>()

    private val m: Int
        get() = rows.size

    private var n = -1

    private val indices: IntRange
        get() = 0 until rows.size

    private constructor(m: Int, n: Int) : this() {
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
            S[i][j] = other * this[i][j]
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
                S[i][j] += this[i][k] * other[k][j]
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
            throw IllegalArgumentException("Cannot add matrices of different order")
    }

    private fun assertRowConditions(elements: DoubleArray) {
        if (elements.isEmpty())
            throw IllegalArgumentException("Cannot add empty row")

        if (n == -1)
            n = elements.size

        if (elements.size != n)
            throw IllegalArgumentException("Invalid matrix")
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