package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.Theory

/**
 * Given a [theory] in _propositional_ form, this method produces an equivalent [Theory] in _relational_ form.
 * Additional information should be provided by the caller, such as: the predicate symbol used in the input theory to
 * denote each instance, and the names and the ordering of attributes.
 *
 * Primary key attributes are assumed to be __lacking__, hence this method shall generate primary key values.
 *
 * @param predication the predicate symbol used to denote instances in the _propositional_ theory
 * @param attributes the names of the attributes which are kept implicit in the _propositional_ representation,
 * in the same exact order they are used in the _propositional_ theory
 */
fun relationalise(theory: Theory, predication: String, vararg attributes: String): Theory {
    val relationalTheory: Sequence<Clause> = TODO(
        """
            - For each rule/fact in $theory...
                - generate a primary key for that clause
                - yield a fact $predication(<primary key>).
                - then for each attribute name in $attributes...
                    - yield a fact <attribute name>(<primary key>, <attribute value>)
        """.trimIndent()
    )
    return Theory.of(relationalTheory)
}
