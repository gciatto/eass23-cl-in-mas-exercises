package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

abstract class AbstractSolver(knowledgeBase: Theory) : Solver {

    override var knowledgeBase: Theory = knowledgeBase
        protected set

    override fun solve(goals: Directive): Sequence<Solution> {
        // delegate the construction of a lazy stream of solutions to the method below
        val streamOfSolutions = solve(goals, goals.bodyItems.toList(), Substitution.empty())
        //  while lazily iterating of the stream of solutions...
        return sequence {
            for (solution in streamOfSolutions) {
                // ... ignore all intermediate negative solutions
                if (solution is Solution.No) continue
                // ... yield positive or exceptional solutions
                yield(solution)
                // ... ignore all solutions coming after some exception solution
                if (solution is Solution.Halt) return@sequence
            }
            // ... add one final negative solution to signal that no more solutions are available
            // (unless some exceptional solution has occurred before)
            yield(Solution.No(goals))
        }
    }

    /**
     * Handles the intermediate resolution step.
     * Lazily computes [Solution]s for the [remainingGoals], provided that
     * - the solver is currently handling a [query] provided by the user
     * - some goals have already been proved, hence constructing the partial resolving [unifier]
     * - some other goals are yet to be proved, namely [remainingGoals]
     */
    protected fun solve(query: Directive, remainingGoals: List<Term>, unifier: Substitution): Sequence<Solution> {
        println("$remainingGoals / $unifier")
        return if (unifier !is Substitution.Unifier) {
            sequenceOf(Solution.No(query))
        } else if (remainingGoals.isEmpty()) {
            sequenceOf(Solution.Yes(query, unifier))
        } else {
            sequence {
                when (val currentGoal = remainingGoals.first().apply(unifier)) {
                    !is Struct -> {
                        yield(Solution.Halt(query, "Invalid goal $currentGoal"))
                    }
                    is Truth -> {
                        if (currentGoal.isTrue) {
                            yieldAll(solve(query, remainingGoals.drop(1), unifier))
                        } else {
                            yield(Solution.No(query))
                        }
                    }
                    else -> {
                        val matchingRules = knowledgeBase[currentGoal].map { it.freshCopy() }
                        handleRules(query, remainingGoals.drop(1), unifier, currentGoal, matchingRules)
                    }
                }
            }
        }
    }

    /**
     * Handles the many [matchingRules] for the [currentGoal], as well as the many [remainingGoals], necessary to prove [query]
     */
    protected abstract suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct,
        matchingRules: Sequence<Rule>
    )

    protected suspend fun SequenceScope<Solution>.handleRulesInARow(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct,
        rules: Sequence<Rule>
    ) {
        TODO("attempt to continue resolution of $currentGoal with the rules in $rules")
    }
}
