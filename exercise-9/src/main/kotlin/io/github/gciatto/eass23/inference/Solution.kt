package io.github.gciatto.eass23.inference

import it.unibo.tuprolog.core.*

/**
 * A response for a given [query], computed by a [Solver].
 * It may be positive, negative, or denote that an error has occurred.
 *
 * @param query a [Directive] containing the goal(s) this solution is carrying a response for
 */
sealed class Solution(open val query: Directive) {
    /**
     * A positive response stating that [query] is satisfiable, provided that [unifier] is applied to it
     */
    data class Yes(override val query: Directive, val unifier: Substitution.Unifier) : Solution(query) {
        /**
         * The [query] after [unifier] has been applied to it
         */
        val solvedQuery: Directive by lazy {
            query.apply(unifier).castToDirective()
        }

        override fun toString(): String = "yes: $solvedQuery"
    }

    /**
     * A negative response stating that [query] is unsatisfiable (or perhaps that no more positive solutions exist)
     */
    data class No(override val query: Directive) : Solution(query) {
        override fun toString(): String = "no"
    }

    /**
     * An exceptional response stating that the resolution process for [query] has met an unexpected situation, described by [error]
     * @param error a [String] describing the exceptional situation
     */
    data class Halt(override val query: Directive, val error: String) : Solution(query) {
        override fun toString(): String = "halt: $error"
    }
}