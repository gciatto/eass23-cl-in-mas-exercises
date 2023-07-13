package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.parse

object Theories {
    val member = Theory.parse("""
        member(X, [X | _]).
        member(X, [_ | T]) :- member(X, T).
    """.trimIndent())

    val inverseMember = Theory.parse("""
        member(X, [_ | T]) :- member(X, T).
        member(X, [X | _]).
    """.trimIndent())

    val nat = Theory.parse("""
        nat(z).
        nat(s(X)) :- nat(X).
    """.trimIndent())

    val inverseNat = Theory.parse("""
        nat(s(X)) :- nat(X).
        nat(z).
    """.trimIndent())
}