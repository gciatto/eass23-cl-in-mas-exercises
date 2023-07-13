package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.Directive
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.parsing.parse
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.parse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestPrologLikeSolver {
    private val sampleTheory = Theory.parse("""
        f(1).
        f(X) :- g(X).
        g(2).
        h(3).
    """.trimIndent())


    @Test
    fun testSimpleQuery() {
        val goal = Struct.parse("f(X)")
        println("?:- $goal.")
        val solver = PrologLikeSolver(sampleTheory)
        val solutions = solver.solve(goal).take(2).map { println(it); it }.toList()

        assertEquals(2, solutions.size)

        solutions.forEachIndexed { i, solution ->
            assertIs<Solution.Yes>(solution)
            assertEquals(Directive.of(goal), solution.query)
            assertEquals(Struct.parse(":- f(${i + 1})"), solution.solvedQuery)
        }
    }

    @Test
    fun testAssertion() {
        val goal = Term.parse(":- assert(a), assert(g(3) :- a), f(X)") as Directive
        val X = goal.variables.first()

        println("$goal.")
        val solver = PrologLikeSolver(sampleTheory)

        val solutions = solver.solve(goal).take(3).map { println(it); it }.toList()
        assertEquals(3, solutions.size)

        solutions.forEachIndexed { i, solution ->
            assertIs<Solution.Yes>(solution)
            assertEquals(Integer.of(i + 1), solution.unifier[X])
        }

        assertEquals(6, solver.knowledgeBase.size)
    }

    @Test
    fun testRetraction() {
        val goal = Term.parse(":- retract(g(X)), f(Y)") as Directive
        val X = goal.variables.filter { it.name == "X" }.first()
        val Y = goal.variables.filter { it.name == "Y" }.first()

        println("$goal.")
        val solver = PrologLikeSolver(sampleTheory)

        val solution = solver.solve(goal).map { println(it); it }.first()

        assertIs<Solution.Yes>(solution)
        assertEquals(Integer.of(2), solution.unifier[X])
        assertEquals(Integer.of(1), solution.unifier[Y])

        assertEquals(3, solver.knowledgeBase.size)
    }

    @Test
    fun testNegation1() {
        val goal = Term.parse(":- not(f(3)), f(1)") as Directive
        println("$goal.")
        val solver = PrologLikeSolver(sampleTheory)
        val solution = solver.solve(goal).map { println(it); it }.first()

        assertIs<Solution.Yes>(solution)
        assertEquals(goal, solution.query)
        assertEquals(goal, solution.solvedQuery)
    }

    @Test
    fun testNegation2() {
        val goal = Term.parse(":- not(f(X))") as Directive
        println("$goal.")
        val solver = PrologLikeSolver(sampleTheory)
        val solution = solver.solve(goal).map { println(it); it }.first()

        assertIs<Solution.No>(solution)
    }

    @Test
    fun testIs() {
        val goal = Term.parse(":- f(X), f(Y), Z is (Y * 2 - X * 3) + 4 / Y, h(Z)") as Directive
        val X = goal.variables.filter { it.name == "X" }.first()
        val Y = goal.variables.filter { it.name == "Y" }.first()
        val Z = goal.variables.filter { it.name == "Z" }.first()

        println("$goal.")
        val solver = PrologLikeSolver(sampleTheory)
        val solutions = solver.solve(goal).take(2).map { println(it); it }.toList()

        solutions[0].let {
            assertIs<Solution.Yes>(it)
            assertEquals(Integer.of(1), it.unifier[X])
            assertEquals(Integer.of(1), it.unifier[Y])
            assertEquals(Integer.of(3), it.unifier[Z])
        }

        solutions[1].let {
            assertIs<Solution.Yes>(it)
            assertEquals(Integer.of(1), it.unifier[X])
            assertEquals(Integer.of(2), it.unifier[Y])
            assertEquals(Integer.of(3), it.unifier[Z])
        }
    }
}