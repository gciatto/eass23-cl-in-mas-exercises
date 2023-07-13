package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.Directive
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.theory.Theory

interface Solver {
    /**
     * The [Theory] queries should be solved against
     */
    val knowledgeBase: Theory

    /**
     * Lazily computes the [Sequence] of [Solution] for some given [goals]
     */
    fun solve(goals: Directive): Sequence<Solution>

    /**
     * Just a handy shortcut for the aforementioned method, where goals can be provided one by one
     * @see solve
     */
    fun solve(firstGoal: Struct, vararg goals: Struct): Sequence<Solution> = solve(Directive.of(firstGoal, *goals))
}
