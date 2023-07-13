package io.github.gciatto.eass23.kr


import it.unibo.tuprolog.theory.Theory
import it.unibo.tuprolog.theory.parsing.ClausesReader
import java.io.InputStream

object Theories {
    private fun open(fileName: String): InputStream =
        Theories::class.java.getResource(fileName)?.openStream()
            ?: throw IllegalStateException("No such a resource: $fileName")

    val iris: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("iris.pl")
        )
    }

    val wines: Theory by lazy {
        ClausesReader.withDefaultOperators().readTheory(
            open("wines.pl")
        )
    }
}