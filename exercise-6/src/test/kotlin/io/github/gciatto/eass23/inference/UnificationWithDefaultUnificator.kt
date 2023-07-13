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
            TODO("What $Substitution do you expect the expression below to produce?"),
            X mguWith X
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            X mguWith Y
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            X mguWith Z
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
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
            TODO("What $Substitution do you expect the expression below to produce?"),
            person1 mguWith person2
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            person2 mguWith person3
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            person1 mguWith person3
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            person1 mguWith person4
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            person2 mguWith person4
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            person3 mguWith person4
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            pearson mguWith person4
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            pearson mguWith person4
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
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
            TODO("What $Substitution do you expect the expression below to produce?"),
            list mguWith pattern1
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            list mguWith pattern2
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            list mguWith pattern3
        )

        assertEquals(
            TODO("What $Substitution do you expect the expression below to produce?"),
            list mguWith pattern4
        )
    }
}