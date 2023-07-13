package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.unify.Unificator.Companion.mguWith

class SmartSolver(knowledgeBase: Theory) : AbstractSolver(knowledgeBase) {
    override suspend fun SequenceScope<Solution>.handleRules(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct,
        matchingRules: Sequence<Rule>
    ) {
        val sortedRules = matchingRules.sortedWith(nonRecursiveFirst)
        handleRulesInARow(query, remainingGoals, unifier, currentGoal, sortedRules)
    }

    private fun isRecursive(rule: Rule): Boolean =
        TODO("return true if $rule is recursive")

    private val nonRecursiveFirst = Comparator<Rule> { r1, r2 ->
        when {
            isRecursive(r1) == isRecursive(r2) -> 0
            !isRecursive(r1) && isRecursive(r2) -> -1
            else -> 1
        }
    }
}
