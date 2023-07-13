package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.ClausesParser
import kotlin.test.assertEquals
import kotlin.test.assertTrue

import kotlin.test.Test

class ClauseCreation {
    /**
     * Supports parsing theories out of strings using the Prolog syntax
     */
    private val parser = ClausesParser.withStandardOperators()

    @Test
    fun simpleTheory() {
        val representation = """
            a_fact.
            a_head :- a_body.
            :- a_directive.
        """.trimIndent()

        val theory: Theory = Theory.of(
            Fact.of(Atom.of("a_fact")),
            Rule.of(Atom.of("a_head"), Atom.of("a_body")),
            Directive.of(Atom.of("a_directive"))
        )
        val expected = parser.parseTheory(representation)

        assertEquals(expected, theory)
    }

    @Test
    @Suppress("LocalVariableName")
    fun simpleSum3() {
        val representation = """
            sum(z, X, X).
            sum(s(N), M, s(R)) :- sum(N, M, R).
        """.trimIndent()

        val X = Var.of("X")
        val N = Var.of("N")
        val M = Var.of("M")
        val R = Var.of("R")

        val theory: Theory = Theory.of(
            Fact.of(Struct.of("sum", Atom.of("z"), X, X)),
            Rule.of(
                Struct.of("sum", Struct.of("s", N), M, Struct.of("s", R)),
                Struct.of("sum", N, M, R)
            )
        )
        val expected = parser.parseTheory(representation)

        assertTrue { expected.equals(theory, useVarCompleteName = false) }
        assertEquals(1, theory.first().variables.distinct().count())
        assertEquals(3, theory.drop(1).first().variables.distinct().count())
    }

    @Test
    @Suppress("LocalVariableName")
    fun simpleConcat3() {
        val representation = """
            concat([], L, L).
            concat([X | Xs], Ys, [X | Zs]) :- concat(Xs, Ys, Zs).
        """.trimIndent()

        val theory: Theory = Theory.of(
            Scope.empty { factOf(structOf("concat", emptyList, varOf("L"), varOf("L"))) },
            Scope.empty {
                ruleOf(
                    structOf("concat", consOf(varOf("X"), varOf("Xs")), varOf("Ys"), consOf(varOf("X"), varOf("Zs"))),
                    structOf("concat", varOf("Xs"), varOf("Ys"), varOf("Zs"))
                )
            }
        )
        val expected = parser.parseTheory(representation)

        assertTrue { expected.equals(theory, useVarCompleteName = false) }
        assertEquals(1, theory.first().variables.distinct().count())
        assertEquals(4, theory.drop(1).first().variables.distinct().count())
    }
}