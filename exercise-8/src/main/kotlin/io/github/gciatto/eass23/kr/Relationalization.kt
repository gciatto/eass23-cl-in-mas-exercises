package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Fact
import it.unibo.tuprolog.core.Indicator
import it.unibo.tuprolog.core.Struct
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
    val relationalTheory = theory.get(Indicator.of(predication, attributes.size))
        .flatMapIndexed { index, rule ->
            val key = Atom.of("instance$index")
            sequenceOf(Struct.of(predication, key)) +
                attributes.asSequence().mapIndexed { attributeIndex, attribute ->
                    Struct.of(attribute, key, rule.head[attributeIndex])
                }
        }.map(Fact::of)
    return Theory.of(relationalTheory)
}
