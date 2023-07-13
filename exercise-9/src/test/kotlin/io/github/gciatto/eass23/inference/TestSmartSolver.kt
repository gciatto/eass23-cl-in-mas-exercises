package io.github.gciatto.eass23.inference

import io.github.gciatto.eass23.inference.Theories.inverseMember
import io.github.gciatto.eass23.inference.Theories.inverseNat
import io.github.gciatto.eass23.inference.Theories.member
import io.github.gciatto.eass23.inference.Theories.nat
import it.unibo.tuprolog.core.Directive
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.parsing.parse
import org.junit.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestSmartSolver {

    @Test
    fun testMemberX123() {
        val goal = Struct.parse("member(X, [1, 2, 3])")
        println("?:- $goal.")
        val solver = SmartSolver(member)
        val solutions = solver.solve(goal).take(4).map{ println(it); it }.toList()

        assertEquals(4, solutions.size)

        for (i in 0 .. 2) {
            solutions[i].let {
                assertIs<Solution.Yes>(it)
                assertEquals(Directive.of(goal), it.query)
                assertEquals(Struct.parse(":- member(${i + 1}, [1, 2, 3])"), it.solvedQuery)
            }
        }

        solutions[3].let {
            assertIs<Solution.No>(it)
            assertEquals(Directive.of(goal), it.query)
        }
    }

    @Test
    fun testInverseMemberX123() {
        val goal = Struct.parse("member(X, [1, 2, 3])")
        println("?:- $goal.")
        val solver = SmartSolver(inverseMember)
        val solutions = solver.solve(goal).take(4).map{ println(it); it }.toList()

        assertEquals(4, solutions.size)

        for (i in 0 .. 2) {
            solutions[i].let {
                assertIs<Solution.Yes>(it)
                assertEquals(Directive.of(goal), it.query)
                assertEquals(Struct.parse(":- member(${i + 1}, [1, 2, 3])"), it.solvedQuery)
            }
        }

        solutions[3].let {
            assertIs<Solution.No>(it)
            assertEquals(Directive.of(goal), it.query)
        }
    }

    @Test
    fun testMemberXY() {
        val goal = Struct.parse("member(X, Y)")
        println("?:- $goal.")
        val solver = SmartSolver(member)

        solver.solve(goal).take(10).map{ println(it); it }.forEachIndexed{ i, solution ->
            assertIs<Solution.Yes>(solution)
            assertEquals(i + 3, solution.solvedQuery.variables.count())
        }
    }

    @Test
    fun testInverseMemberXY() {
        val goal = Struct.parse("member(X, Y)")
        println("?:- $goal.")
        val solver = SmartSolver(inverseMember)

        solver.solve(goal).take(10).map{ println(it); it }.forEachIndexed{ i, solution ->
            assertIs<Solution.Yes>(solution)
            assertEquals(i + 3, solution.solvedQuery.variables.count())
        }
    }

    @Test
    fun testNatX() {
        val goal = Struct.parse("nat(X)")
        println("?:- $goal.")
        val solver = SmartSolver(nat)
        val solutions = solver.solve(goal).take(4).map { println(it); it }

        peanoNumbers.map { Directive.of(Struct.of("nat", it)) }
            .zip(solutions)
            .take(4)
            .forEach { (expected, actual) ->
                assertIs<Solution.Yes>(actual)
                assertEquals(expected, actual.solvedQuery)
            }
    }

    @Test
    fun testInverseNatX() {
        val goal = Struct.parse("nat(X)")
        println("?:- $goal.")
        val solver = SmartSolver(inverseNat)
        val solutions = solver.solve(goal).take(4).map { println(it); it }

        peanoNumbers.map { Directive.of(Struct.of("nat", it)) }
            .zip(solutions)
            .take(4)
            .forEach { (expected, actual) ->
                assertIs<Solution.Yes>(actual)
                assertEquals(expected, actual.solvedQuery)
            }
    }
}