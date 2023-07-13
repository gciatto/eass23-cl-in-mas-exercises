package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.parsing.TermParser
import kotlin.test.Test
import kotlin.test.assertEquals

class TestHerbrand {
    private val parser = TermParser.withNoOperator()

    private val functors = arrayOf(
        Symbol("a"),
        Symbol("b"),
        Symbol("f", 2)
    )

    private val peanoIntegers: Sequence<Term>
        get() = generateSequence<Struct>(Atom.of("z")) { Struct.of("s", it) }

    @Test
    fun testPartialHerbrandUniverse0() {
        assertEquals(
            herbrand(*functors, max = 0).toSet(),
            setOf(Atom.of("a"), Atom.of("b"))
        )
    }

    @Test
    fun testPartialHerbrandUniverse1() {
        assertEquals(
            herbrand(*functors, max = 1).toSet(),
            sequenceOf(
                "a",
                "b",
                "f(a, a)",
                "f(a, b)",
                "f(b, a)",
                "f(b, b)",
            ).map(parser::parseTerm).toSet()
        )
    }

    @Test
    fun testPartialHerbrandUniverse3() {
        assertEquals(
            herbrand(*functors, max = 2).toSet(),
            sequenceOf(
                "a",
                "b",
                "f(a, a)",
                "f(a, b)",
                "f(b, a)",
                "f(b, b)",
                "f(a, f(a, a))",
                "f(a, f(a, b))",
                "f(a, f(b, a))",
                "f(a, f(b, b))",
                "f(b, f(a, a))",
                "f(b, f(a, b))",
                "f(b, f(b, a))",
                "f(b, f(b, b))",
                "f(f(a, a), a)",
                "f(f(a, a), b)",
                "f(f(a, a), f(a, a))",
                "f(f(a, a), f(a, b))",
                "f(f(a, a), f(b, a))",
                "f(f(a, a), f(b, b))",
                "f(f(a, b), a)",
                "f(f(a, b), b)",
                "f(f(a, b), f(a, a))",
                "f(f(a, b), f(a, b))",
                "f(f(a, b), f(b, a))",
                "f(f(a, b), f(b, b))",
                "f(f(b, a), a)",
                "f(f(b, a), b)",
                "f(f(b, a), f(a, a))",
                "f(f(b, a), f(a, b))",
                "f(f(b, a), f(b, a))",
                "f(f(b, a), f(b, b))",
                "f(f(b, b), a)",
                "f(f(b, b), b)",
                "f(f(b, b), f(a, a))",
                "f(f(b, b), f(a, b))",
                "f(f(b, b), f(b, a))",
                "f(f(b, b), f(b, b))",
            ).map(parser::parseTerm).toSet()
        )
    }
}