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

    private suspend fun SequenceScope<Solution>.handleAssert(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        TODO("handle assert by modifying $knowledgeBase")
    }

    private suspend fun SequenceScope<Solution>.handleRetract(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        TODO("handle retract by modifying $knowledgeBase")
    }

    private suspend fun SequenceScope<Solution>.handleNot(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        TODO("handle negated queries by creating a new solver behind the scenes")
    }

    private suspend fun SequenceScope<Solution>.handleIs(
        query: Directive,
        remainingGoals: List<Term>,
        unifier: Substitution.Unifier,
        currentGoal: Struct
    ) {
        TODO("handle is by evaluating the second argument of the $currentGoal as an expression, via ${::eval}")
    }

}