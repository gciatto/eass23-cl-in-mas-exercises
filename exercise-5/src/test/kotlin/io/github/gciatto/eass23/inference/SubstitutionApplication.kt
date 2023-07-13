package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.core.List
import it.unibo.tuprolog.core.exception.SubstitutionApplicationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SubstitutionApplication {

    val X = Var.of("X")

    val Y = Var.of("Y")

    val hello = Atom.of("hello")

    val world = Atom.of("world")

    @Test
    fun applyToVariable() {
        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            X.apply(Substitution.empty())
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            X.apply(Substitution.unifier(X, hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            X.apply(Substitution.unifier(Y to hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            X.apply(Substitution.unifier(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            // nothing to do here, just read and understand this case
            X.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToConstant() {
        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            world.apply(Substitution.empty())
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            world.apply(Substitution.unifier(X, hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            world.apply(Substitution.unifier(Y to hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            world.apply(Substitution.unifier(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            // nothing to do here, just read and understand this case
            world.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToStruct() {
        val fX = Struct.of("f", X)

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            fX.apply(Substitution.empty())
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            fX.apply(Substitution.of(X, hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            fX.apply(Substitution.of(Y to hello))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            fX.apply(Substitution.of(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            // nothing to do here, just read and understand this case
            fX.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToList() {
        val listPattern = Cons.of(X, Y)

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            listPattern.apply(Substitution.empty())
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            listPattern.apply(Substitution.of(X to Integer.ONE))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            listPattern.apply(Substitution.of(X to Integer.ONE, Y to EmptyList.instance))
        )

        assertEquals(
            TODO("What term do you expect the application below to produce?"),
            listPattern.apply(
                Substitution.of(
                    X to Integer.ONE,
                    Y to List.of(hello, world)
                )
            )
        )

        assertFailsWith<SubstitutionApplicationException> {
            // nothing to do here, just read and understand this case
            listPattern.apply(Substitution.failed())
        }
    }
}