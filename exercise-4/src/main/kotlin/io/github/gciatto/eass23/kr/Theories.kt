package io.github.gciatto.eass23.kr

import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.ClausesParser
import it.unibo.tuprolog.theory.parsing.ClausesReader
import java.io.InputStream

object Theories {
    private fun open(fileName: String): InputStream =
        Theories::class.java.getResource(fileName)?.openStream()
            ?: throw IllegalStateException("No such a resource: $fileName")

    val parenthood: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("parenthood.pl")
        )
    }

    val genders: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("genders.pl")
        )
    }

    val grandParenthood : Theory by lazy {
        ClausesParser.withDefaultOperators().parseTheory(
            """
            grandparent(X, Y) :- parent(X, Z), parent(Z, Y). 
            """.trimIndent()
        )
    }

    val motherhood : Theory by lazy {
        ClausesParser.withDefaultOperators().parseTheory(
            """
            mother(X, Y) :- parent(X, Y), female(X).
            """.trimIndent()
        )
    }

    val fatherhood : Theory by lazy {
        ClausesParser.withDefaultOperators().parseTheory(
            """
            father(X, Y) :- parent(X, Y), male(X).
            """.trimIndent()
        )
    }
}