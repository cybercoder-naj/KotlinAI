package com.github.cybercodernaj

fun matrix(init: Matrix.() -> Unit): Matrix {
    val matrix = Matrix()
    matrix.init()

    if (matrix.rows.isEmpty())
        throw IllegalArgumentException("Cannot instantiate empty matrix")

    return matrix
}

operator fun Int.times(other: Matrix) = other * this

operator fun Double.times(other: Matrix) = other * this