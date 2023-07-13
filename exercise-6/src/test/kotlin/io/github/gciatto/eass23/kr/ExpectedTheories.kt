package io.github.gciatto.eass23.kr


import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.ClausesReader
import java.io.InputStream

object ExpectedTheories {
    private fun open(fileName: String): InputStream =
        ExpectedTheories::class.java.getResource(fileName)?.openStream()
            ?: throw IllegalStateException("No such a resource: $fileName")

    val iris: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("iris-relational.pl")
        )
    }

    val wines: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("wines-relational.pl")
        )
    }
}