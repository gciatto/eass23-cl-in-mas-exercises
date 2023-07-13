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
    val predications = enumeratePredicates(theory)
        .map { Symbol(it.functor, it.arity) }
        .toSet()
    val functors = enumeratePredicates(theory)
        .enumerateSymbolsInStructs()
        .filter { it !in predications }
        .toSet()
    val variables = theory.rules.flatMap { it.variables }.distinct().map { Symbol(it.name) }.toSet()
    return Analysis(functors, predications, variables)
}

private fun enumeratePredicates(theory: Theory): Sequence<Struct> =
    theory.rules.asSequence()
        .flatMap { it.bodyItems.asSequence() + it.head }
        .filterIsInstance<Struct>()
        .filter { !it.isTrue  }

private fun <T : Term> Sequence<T>.enumerateSymbolsInStructs(): Sequence<Symbol> =
    sequence {
        for (item in filterIsInstance<Struct>()) {
            yield(Symbol(item.functor, item.arity))
            for (arg in item.args) {
                if (arg is Struct) {
                    yield(Symbol(arg.functor, arg.arity))
                    arg.argsSequence.enumerateSymbolsInStructs()
                }
            }
        }
    }
