package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import org.gciatto.kt.math.BigInteger

fun eval(term: Term): BigInteger = when(term) {
    is Struct -> eval(term)
    is Numeric -> term.intValue
    else -> throw IllegalArgumentException("Invalid term: $term")
}

fun eval(struct: Struct): BigInteger {
    if (struct.arity != 2) throw IllegalArgumentException("Invalid expression: $struct")
    val x = eval(struct[0])
    val y = eval(struct[1])
    return when (struct.functor) {
        "+" -> x + y
        "-" -> x - y
        "*" -> x * y
        "/" -> x / y
        else -> throw IllegalArgumentException("Invalid expression: $struct")
    }
}