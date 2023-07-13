package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.core.parsing.parse
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.ClausesParser
import org.junit.Test
import kotlin.test.assertEquals

class RetrievingClausesFromTheories {
    private val clauses = ClausesParser.withDefaultOperators().parseClauses("""
        member(_, []).
        member(H, [H | _]).
        member(X, [_ | T]) :- member(X, T).
        member.
        member(x).
    """.trimIndent())

    private val theory = Theory.of(clauses)

    @Test
    fun missing() {
        val missing: Sequence<Rule> = theory[Struct.parse("member(A, B, C)")]

        assertEquals(
            emptyList(),
            missing.toList()
        )
    }

    @Test
    fun retrieveMember2() {
        val member2: Sequence<Rule> = theory[Struct.parse("member(A, B)")]

        assertEquals(
            clauses.slice(0 .. 2),
            member2.toList()
        )
    }

    @Test
    fun getMember2() {
        val member2: Sequence<Rule> = theory.get(Struct.parse("member(A, B)"))

        assertEquals(
            clauses.slice(0 .. 2),
            member2.toList()
        )
    }

    @Test
    fun retrieveMember1() {
        val member1: Sequence<Rule> = theory[Struct.parse("member(A)")]

        assertEquals(
            clauses.slice(4 .. 4),
            member1.toList()
        )
    }

    @Test
    fun retrieveMember0() {
        val member0: Sequence<Rule> = theory[Atom.of("member")]

        assertEquals(
            clauses.slice(3 .. 3),
            member0.toList()
        )
    }

    @Test
    fun retrieveMember2WithNonEmptyList() {
        val member2WithNonEmptyList: Sequence<Rule> = theory[Struct.parse("member(_, [_ | _])")]

        assertEquals(
            clauses.slice(1 .. 2),
            member2WithNonEmptyList.toList()
        )
    }
}