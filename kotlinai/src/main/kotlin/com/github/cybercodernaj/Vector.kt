package com.github.cybercodernaj

import kotlin.coroutines.cancellation.CancellationException

class Vector internal constructor(array: DoubleArray) {
    internal val matrix: Matrix

    private val size: Int
        get() = matrix.n

    init {
        if (array.isEmpty())
            throw IllegalArgumentException("Array cannot be empty")

        matrix = matrix {
            array.forEach { e ->
                row(e)
            }
        }
    }

    private constructor(
        size: Int,
        init: DoubleArray = DoubleArray(0)
    ) : this(DoubleArray(size) { if (it < init.size) init[it] else 0.0 })

    private constructor(matrix: Matrix): this(DoubleArray(matrix.n) { matrix[it][0] })

    operator fun plus(other: Vector) = plus(other, true)

    fun plus(other: Vector, strict: Boolean = true): Vector {
        if (strict && this orderNotEqual other)
            throw IllegalArgumentException(
                "Cannot add vectors of different number of dimensions. " +
                        "Consider passing false for strict mode."
            )

        var x: Vector = Vector(0)
        var y: Vector = Vector(0)

        if (!strict) {
            when {
                size > other.size -> {
                    val array = DoubleArray(other.size)
                    array.forEachIndexed { index, _ ->
                        array[index] = other.matrix[index][0]
                    }
                    x = this
                    y = Vector(size, array)
                }
                size < other.size -> {
                    val array = DoubleArray(size)
                    array.forEachIndexed { index, _ ->
                        array[index] = other.matrix[index][0]
                    }
                    x = Vector(other.size, array)
                    y = other
                }
                else -> {
                    x = this
                    y = other
                }
            }
        }

        return Vector(x.matrix + y.matrix)
    }

    infix fun orderEqual(other: Vector) = size == other.size

    infix fun orderNotEqual(other: Vector) = !orderEqual(other)
}