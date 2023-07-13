package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.theory.Theory

/**
 * A type carrying 3 sorts of information concerning a given theory:
 * @param predications the set of predicate symbols possibly used in all heads and bodies of any clause in the theory
 * @param functors the set of function symbols possibly used within any predicates of any clause in the theory
 * @param variableNames the set of variables' names possibly used within all terms exploited by of any clause in the theory
 */
data class Analysis(val functors: Set<Symbol>, val predications: Set<Symbol>, val variableNames: Set<Symbol>)

/**
 * Given a [Theory], this method should compute
 * - the set of predicate symbols possibly used in all heads and bodies of any clause in the theory
 * - the set of function symbols possibly used within any predicates of any clause in the theory
 * - the set of variables' names possibly used within all terms exploited by of any clause in the theory
 * and wrap them up into an instance of [Analysis]
 * @param theory the [Theory] to be analysed
 * @return an instance of [Analysis] summarising the set of functors, predicates, and variables' names
 * used with the [theory]
 */
fun analyze(theory: Theory): Analysis {
    val predications: Set<Symbol> = TODO("Get all the functors/arities of all *first-level* structures in all heads and bodies of all clauses in $theory")
    val functors: Set<Symbol> = TODO("Get all the functors/arities of all structures in all head and bodies of all clauses in $theory, at any level of depth, then exclude the ones in $predications")
    val variables: Set<Symbol> = TODO("Get all the variables in all terms in all clauses in $theory")
    return Analysis(functors, predications, variables)
}
