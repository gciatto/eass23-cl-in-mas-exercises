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
            X,
            X.apply(Substitution.empty())
        )

        assertEquals(
            hello,
            X.apply(Substitution.unifier(X, hello))
        )

        assertEquals(
            X,
            X.apply(Substitution.unifier(Y to hello))
        )

        assertEquals(
            world,
            X.apply(Substitution.unifier(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            X.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToConstant() {
        assertEquals(
            world,
            world.apply(Substitution.empty())
        )

        assertEquals(
            world,
            world.apply(Substitution.unifier(X, hello))
        )

        assertEquals(
            world,
            world.apply(Substitution.unifier(Y to hello))
        )

        assertEquals(
            world,
            world.apply(Substitution.unifier(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            world.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToStruct() {
        val fX = Struct.of("f", X)

        assertEquals(
            fX,
            fX.apply(Substitution.empty())
        )

        assertEquals(
            Struct.of("f", hello),
            fX.apply(Substitution.of(X, hello))
        )

        assertEquals(
            fX,
            fX.apply(Substitution.of(Y to hello))
        )

        assertEquals(
            Struct.of("f", world),
            fX.apply(Substitution.of(Y to hello, X to world))
        )

        assertFailsWith<SubstitutionApplicationException> {
            fX.apply(Substitution.failed())
        }
    }

    @Test
    fun applyToList() {
        val listPattern = Cons.of(X, Y)

        assertEquals(
            listPattern,
            listPattern.apply(Substitution.empty())
        )

        assertEquals(
            Cons.of(Integer.ONE, Y),
            listPattern.apply(Substitution.of(X to Integer.ONE))
        )

        assertEquals(
            List.of(Integer.ONE),
            listPattern.apply(Substitution.of(X to Integer.ONE, Y to EmptyList.instance))
        )

        assertEquals(
            List.of(Integer.ONE, hello, world),
            listPattern.apply(
                Substitution.of(
                    X to Integer.ONE,
                    Y to List.of(hello, world)
                )
            )
        )

        assertFailsWith<SubstitutionApplicationException> {
            listPattern.apply(Substitution.failed())
        }
    }
}
