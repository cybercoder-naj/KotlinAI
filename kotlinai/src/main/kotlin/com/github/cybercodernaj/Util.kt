package com.github.cybercodernaj

fun matrix(precision: Int = 2, init: Matrix.() -> Unit): Matrix {
    val matrix = Matrix(precision)
    matrix.init()

    if (matrix.rows.isEmpty())
        throw IllegalArgumentException("Cannot instantiate empty matrix")

    return matrix
}

fun vector(vararg elements: Double): Vector {
	val vector = Vector(elements)

	return vector
}

fun vector(vararg elements: Int): Vector {
	val vector = Vector(
		elements.map { it.toDouble() }.toDoubleArray()
	)

	return vector
}

fun transpose(A: Matrix) = A.transpose()

fun det(A: Matrix) = A.determinant()

fun adj(A: Matrix) = A.adjoint()

fun inv(A: Matrix) = A.inverse()

operator fun Int.times(other: Matrix) = other * this

operator fun Double.times(other: Matrix) = other * this