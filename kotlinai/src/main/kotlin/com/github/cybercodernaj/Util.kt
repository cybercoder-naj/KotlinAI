package com.github.cybercodernaj

fun matrix(init: Matrix.() -> Unit): Matrix {
    val matrix = Matrix()
    matrix.init()

    if (matrix.rows.isEmpty())
        throw IllegalArgumentException("Cannot instantiate empty matrix")

    return matrix
}

fun transpose(A: Matrix) = A.transpose()

fun det(A: Matrix) = A.determinant()

fun adj(A: Matrix) = A.adjoint()

fun inv(A: Matrix) = A.inverse()

operator fun Int.times(other: Matrix) = other * this

operator fun Double.times(other: Matrix) = other * this