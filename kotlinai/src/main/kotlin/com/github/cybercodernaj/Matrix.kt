package com.github.cybercodernaj

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
        if (elements.isEmpty())
            throw IllegalArgumentException("Cannot add empty row")

        if (n == -1)
            n = elements.size

        if (elements.size != n)
            throw IllegalArgumentException("Invalid matrix")

        val row = doubleArrayOf(*elements)
        rows.add(row)
        return row
    }

    fun row(vararg elements: Int): DoubleArray {
        if (elements.isEmpty())
            throw IllegalArgumentException("Cannot add empty row")

        if (n == -1)
            n = elements.size

        if (elements.size != n)
            throw IllegalArgumentException("Invalid matrix")

        val row = doubleArrayOf(*(elements.map { it.toDouble() }.toDoubleArray()))
        rows.add(row)
        return row
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Matrix)
            return false

        if (this.m != other.m || this.n != other.n)
            return false

        for (i in indices)
            for (j in rows[i].indices)
                if (this[i][j] != other[i][j])
                    return false

        return true
    }

    operator fun get(position: Int) = rows[position]

    operator fun plus(other: Matrix): Matrix {
        assertEqualOrder(other)

        val S = Matrix(m, n)
        for (i in indices) {
            for (j in rows[i].indices)
                S[i][j] = this[i][j] + other[i][j]
        }
        return S
    }

    operator fun minus(other: Matrix): Matrix {
        assertEqualOrder(other)

        val S = Matrix(m, n)
        for (i in indices) {
            for (j in rows[i].indices)
                S[i][j] = this[i][j] - other[i][j]
        }
        return S
    }

    private fun assertEqualOrder(other: Matrix) {
        if (this.m != other.m || this.n != other.n)
            throw IllegalArgumentException("Cannot add matrices of different order")
    }

    override fun hashCode(): Int {
        var result = rows.hashCode()
        result = 31 * result + m
        result = 31 * result + n
        return result
    }
}

fun matrix(init: Matrix.() -> Unit): Matrix {
    val matrix = Matrix()
    matrix.init()
    if (matrix.rows.isEmpty())
        throw IllegalArgumentException("Cannot instantiate empty matrix")
    return matrix
}