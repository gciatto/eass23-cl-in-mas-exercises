package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

class PrologLikeSolver(knowledgeBase: Theory) : DepthFirstSolver(knowledgeBase) {

    companion object {
        val not1 = Indicator.of("not", 1)
        val is2 = Indicator.of("is", 2)
        val assert1 = Indicator.of("assert", 1)
        val retract1 = Indicator.of("retract", 1)
    }

    override suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct,
        matchingRules: Sequence<Rule>
    ) = when (currentGoal.indicator) {
        not1 -> handleNot(query, remainingGoals, unifier, currentGoal)
        is2 -> handleIs(query, remainingGoals, unifier, currentGoal)
        assert1 -> handleAssert(query, remainingGoals, unifier, currentGoal)
        retract1 -> handleRetract(query, remainingGoals, unifier, currentGoal)
        else -> handleRulesInARow(query, remainingGoals, unifier, currentGoal, matchingRules)
    }

    private suspend fun SequenceScope<Solution>.handleNot(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        when (val negatedGoal = currentGoal[0]) {
            !is Struct -> {
                yield(Solution.Halt(query, "Invalid goal $negatedGoal"))
            }
            else -> {
                val internalSolver = PrologLikeSolver(knowledgeBase)
                for (solution in internalSolver.solve(negatedGoal)) {
                    when (solution) {
                        is Solution.Halt -> {
                            yield(Solution.Halt(query, solution.error))
                            return
                        }
                        is Solution.Yes -> {
                            yield(Solution.No(query))
                            return
                        }
                        else -> continue
                    }
                }
                yieldAll(solve(query, remainingGoals, unifier))
            }
        }
    }

    private suspend fun SequenceScope<Solution>.handleIs(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        val left = currentGoal[0]
        val right = currentGoal[1]
        try {
            val substitution = left mguWith Integer.of(eval(right))
            yieldAll(solve(query, remainingGoals, unifier + substitution))
        } catch (e: IllegalArgumentException) {
            yield(Solution.Halt(query, e.message ?: "Cannot evaluate $right"))
        }
    }

    private suspend fun SequenceScope<Solution>.handleAssert(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        when (val toBeAsserted = currentGoal[0]) {
            is Clause -> knowledgeBase += toBeAsserted
            is Struct -> knowledgeBase += Fact.of(toBeAsserted)
            else -> yield(Solution.Halt(query, "Not a clause: $toBeAsserted"))
        }
        yieldAll(solve(query, remainingGoals, unifier))
    }

    private suspend fun SequenceScope<Solution>.handleRetract(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        val toBeRetracted = currentGoal[0]
        if (toBeRetracted !is Struct) {
            yield(Solution.Halt(query, "Not a clause: $toBeRetracted"))
        } else {
            val retraction = knowledgeBase.retract(toBeRetracted)
            knowledgeBase = retraction.theory
            val substitution = retraction.firstClause?.head?.let { toBeRetracted mguWith it } ?: Substitution.failed()
            yieldAll(solve(query, remainingGoals, unifier + substitution))
        }
    }

}