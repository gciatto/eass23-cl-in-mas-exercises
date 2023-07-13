package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term
import kotlin.test.Test
import kotlin.test.assertEquals

class TestHerbrandPeano {
    private val functors = arrayOf(
        Symbol("z"),
        Symbol("s", 1)
    )

    private val peanoIntegers: Sequence<Term>
        get() = generateSequence<Struct>(Atom.of("z")) { Struct.of("s", it) }

    @Test
    fun testPartialHerbrandUniverse0() {
        assertEquals(
            herbrand(*functors, max = 0).toSet(),
            peanoIntegers.take(1).toSet()
        )
    }

    @Test
    fun testPartialHerbrandUniverse1() {
        assertEquals(
            herbrand(*functors, max = 1).toSet(),
            peanoIntegers.take(2).toSet()
        )
    }

    @Test
    fun testPartialHerbrandUniverse3() {
        assertEquals(
            herbrand(*functors, max = 2).map { println(it); it }.toSet(),
            peanoIntegers.take(3).toSet()
        )
    }
}