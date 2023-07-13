package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Substitution
import it.unibo.tuprolog.core.Var
import org.junit.Test
import kotlin.test.assertEquals

class SubstitutionMerging {
    private val X = Var.of("X")

    private val Y = Var.of("Y")

    private val Z = Var.of("Z")

    private val a = Atom.of("a")

    private val b = Atom.of("b")

    private val c = Atom.of("c")

    private val one = Integer.of(1)

    private val two = Integer.of(2)

    @Test
    fun mergeDisjunct() {
        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to a, Y to b) + Substitution.of(Z to c)
        )
    }

    @Test
    fun mergeWithFail() {
        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to a) + Substitution.failed()
        )
    }

    @Test
    fun mergeWithOverlap() {
        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to a, Y to b) + Substitution.of(Y to b, Z to c)
        )
    }

    @Test
    fun mergeWithContradiction() {
        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to a, Y to b) + Substitution.of(Y to one, Z to two)
        )
    }

    @Test
    fun mergeWithVariables() {
        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to Y) + Substitution.of(Y to one, Z to two)
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to Y, Y to Z) + Substitution.of(Z to a)
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            Substitution.of(X to Y, Y to Z) + Substitution.of(Z to X)
        )
    }
}