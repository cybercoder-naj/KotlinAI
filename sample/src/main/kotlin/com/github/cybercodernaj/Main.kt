@file:Suppress("LocalVariableName")

package com.github.cybercodernaj

import kotlin.math.round

fun main() {
    linearAlgebra()
}

fun linearAlgebra() {
    println(
        "If you buy one apple, one banana and one carrot, it costs you ${'\u20B9'}9.\n" +
                "If you buy 2 apples, 3 bananas and 2 carrots, it costs you ${'\u20B9'}21.\n" +
                "If you buy 1 apple, 2 bananas and 4 carrots, it costs you ${'\u20B9'}24.\n\nThen\n"
    )

    val A = matrix {
        row(1, 1, 1)
        row(2, 3, 2)
        row(1, 2, 4)
    }

    val B = matrix {
        row(9)
        row(21)
        row(24)
    }

    val X = inv(A) * B

    println("1 apple costs: ${round(X[0][0])}")
    println("1 banana costs: ${round(X[1][0])}")
    println("1 carrot costs: ${round(X[2][0])}")
}