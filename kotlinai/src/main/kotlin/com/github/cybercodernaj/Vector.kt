package com.github.cybercodernaj

class Vector internal constructor(array: DoubleArray){
	internal val matrix: Matrix

	private val size: Int
		get() =	matrix.n

	init {
		if (array.isEmpty())
			throw IllegalArgumentException("Array cannot be empty")

		matrix = matrix {
			array.forEach {e ->
				row(e)
			}
		}
	}

	private constructor(size: Int): this(DoubleArray(size))
}