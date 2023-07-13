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
            TODO("Create a $Fact whose representation is `a_fact'"),
            TODO("Create a $Rule whose representation is `a_head :- a_body'"),
            TODO("Create a $Directive whose representation is `:- a_directive'"),
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

        val theory: Theory = TODO("Create a $Theory equal to $representation (a part from variables names)")
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

        val theory: Theory = TODO("Create a $Theory equal to $representation (a part from variables names)")
        val expected = parser.parseTheory(representation)

        assertTrue { expected.equals(theory, useVarCompleteName = false) }
        assertEquals(1, theory.first().variables.distinct().count())
        assertEquals(4, theory.drop(1).first().variables.distinct().count())
    }
}