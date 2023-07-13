package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.core.List
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("PrivatePropertyName", "LocalVariableName")
class UnificationWithDefaultUnificator {
    private val X = Var.of("X")

    private val Y = Var.of("Y")

    private val Z = Var.of("Z")

    private val giovanni = Atom.of("giovanni")

    private val ciatto = Atom.of("ciatto")

    private val age = Integer.of(30)

    private val anotherAge = Integer.of(31)

    @Test
    fun mguAmongConstants() {
        val c1 = Atom.of("c")
        val c2 = Atom.of("c")
        val k = Atom.of("k")

        assertEquals(
            Substitution.empty(),
            c1 mguWith c2
        )

        assertEquals(
            Substitution.failed(),
            c1 mguWith k
        )
    }

    @Test
    fun mguAmongVariables() {
        assertEquals(
            Substitution.empty(),
            X mguWith X
        )

        assertEquals(
            Substitution.unifier(X to Y),
            X mguWith Y
        )

        assertEquals(
            Substitution.unifier(X to Z),
            X mguWith Z
        )

        assertEquals(
            Substitution.unifier(Y to Z),
            Y mguWith Z
        )
    }

    @Test
    fun mguAmongStructures() {
        val person1 = Struct.of("person", giovanni, Y, age)
        val person2 = Struct.of("person", X, ciatto, Z)
        val person3 = Struct.of("person", X, ciatto, anotherAge)
        val person4 = Struct.of("person", X, Y)
        val pearson = Struct.of("pearson", X, Y, Z)

        assertEquals(
            Substitution.of(X to giovanni, Y to ciatto, Z to age),
            person1 mguWith person2
        )

        assertEquals(
            Substitution.of(Z to anotherAge),
            person2 mguWith person3
        )

        assertEquals(
            Substitution.failed(),
            person1 mguWith person3
        )

        assertEquals(
            Substitution.failed(),
            person1 mguWith person4
        )

        assertEquals(
            Substitution.failed(),
            person2 mguWith person4
        )

        assertEquals(
            Substitution.failed(),
            person3 mguWith person4
        )

        assertEquals(
            Substitution.failed(),
            pearson mguWith person4
        )

        assertEquals(
            Substitution.failed(),
            pearson mguWith person4
        )

        assertEquals(
            Substitution.failed(),
            pearson mguWith person4
        )
    }

    @Test
    fun mguAmongLists() {
        val W = Var.of("W")

        val list = List.of(giovanni, ciatto, age)
        val pattern1 = Cons.of(X, Y)
        val pattern2 = List.of(X, Y, Z)
        val pattern3 = List.of(X, Y, Z, W)
        val pattern4 = List.from(X, Y, Z, last=W)

        assertEquals(
            Substitution.of(X to giovanni, Y to List.of(ciatto, age)),
            list mguWith pattern1
        )

        assertEquals(
            Substitution.of(X to giovanni, Y to ciatto, Z to age),
            list mguWith pattern2
        )

        assertEquals(
            Substitution.failed(),
            list mguWith pattern3
        )

        assertEquals(
            Substitution.of(X to giovanni, Y to ciatto, Z to age, W to EmptyList.instance),
            list mguWith pattern4
        )
    }
}