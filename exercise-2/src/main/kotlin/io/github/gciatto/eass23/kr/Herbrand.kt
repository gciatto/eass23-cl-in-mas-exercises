package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term

/**
 * A type for functors (or predications), i.e. function (or predicate) names of given arities.
 * A `n`-ary symbol named `f` can be used to construct [Struct]ures having `n` arguments.
 * Symbols of arity equal to 0 are for constants, a.k.a. [Atom]s.
 *
 * @param name a [String] representing the symbol
 * @param arity a non-negative [Int] representing the arity of the symbol
 */
data class Symbol(val name: String, val arity: Int = 0) {
    init { require(arity >= 0) }
}

/**
 * Given a set of [functors], this method lazily enumerates the partial Herbrand universe `Hᵢ`, up to index [max].
 * The algorithm implemented by this method is as follows:
 * 1. let `H₀` be the set of [Atom]s, attained from the 0-ary names in [functors]
 * 2. let `H₁` be the set of [Term]s attained by adding the [expansion] of `H₀` to `H₀` itself
 * 3. let `H₂` be the set of [Term]s attained by adding the [expansion] of `H₁` to `H₁` itself
 * 4. let `Hᵢ` be the set of [Term]s attained by adding the [expansion] of `Hᵢ₋₁` to `Hᵢ₋₁` itself
 * 5. and so on, up to `i == [max]
 * @param functors is the set of [Symbol]s spawning the Herbrand universe
 * @param max limits the computation of the Herbrand universe to `Hₘₐₓ`. Negative values are interpreted as infinity
 * @return a [Sequence] lazily enumerating all the terms in `Hₘₐₓ`
 */
fun herbrand(vararg functors: Symbol, max: Int = -1): Sequence<Term> {
    val constants = functors.filter { it.arity == 0 }.map { Atom.of(it.name) }.toSet()
    val functorsWithArity = functors.filter { it.arity > 0 }.toSet()
    return sequence {
        val universe: MutableSet<Term> = mutableSetOf()
        var expansion: Set<Term> = constants
        var i = max
        while (i >= 0 && expansion.isNotEmpty()) {
            universe.addAll(expansion)
            yieldAll(expansion)
            expansion = expansion(universe, functorsWithArity).toSet()
            i--
        }
    }
}

/**
 * Given some partial Herbrand [universe] `Hᵢ`, and a set of [functors],
 * this method yields the sequence of [Term]s to be added to `Hᵢ` to compute `Hᵢ₊₁`, i.e.:
 * ```
 * Hᵢ₊₁ - Hᵢ
 * ```
 * @param universe the partial Herbrand universe `Hᵢ`, spawned by [functors]
 * @param functors a set of [Symbol]s, i.e. function names of given arities
 * @return the [Sequence] of [Term]s in `Hᵢ₊₁ - Hᵢ`
 */
internal fun expansion(universe: Set<Term>, functors: Set<Symbol>): Sequence<Term> {
    return sequence {
        for ((name, arity) in functors) {
            for (arguments in cartesianPower(universe, arity)) {
                val term = Struct.of(name, arguments)
                if (term !in universe) {
                    yield(term)
                }
            }
        }
    }
}

/**
 * Given a set of [items] of type [T], and a non-negative integer [n],
 * this method computes the  [n]-ary [Cartesian power](https://en.wikipedia.org/wiki/Cartesian_product#n-ary_Cartesian_power ) of [items], i.e.:
 * ```
 * itemsⁿ = items × items × ... × items (n times)
 * ```
 * @param T any type
 * @param items a set of objects of type [T], coveniently represented as an [Iterable]
 * @param n a non-negative [Int] representing the order of the power
 * @return a [Sequence] of [List]s of [T] where each list contains [n] items
 */
internal fun <T> cartesianPower(items: Iterable<T>, n: Int): Sequence<List<T>> {
    return when {
        n < 0 -> throw IllegalArgumentException("Unexpected exponent: $n (should be non-negative)")
        n == 0 -> emptySequence()
        n == 1 -> items.asSequence().map { listOf(it) }
        else -> sequence {
            for (item in items) {
                for (smallerPower in cartesianPower(items , n - 1)) {
                    yield(listOf(item) + smallerPower)
                }
            }
        }
    }
}
