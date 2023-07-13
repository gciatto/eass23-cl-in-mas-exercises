package io.github.gciatto.eass23.kr

import kotlin.test.Test
import kotlin.test.assertEquals

class TestRelationalization {
    @Test
    fun testIrisRelationalization() {
        val relationalized = relationalise(
            Theories.iris,
            "iris",
            "sepal_length",
            "sepal_width",
            "petal_length",
            "petal_width",
            "class"
        )
        assertEquals(ExpectedTheories.iris, relationalized)
    }

    @Test
    fun testWineRelationalization() {
        val relationalized = relationalise(
            Theories.wines,
            "wine",
            "class",
            "alcohol",
            "malic_acid",
            "ash",
            "ash_alcalinity",
            "magnesium",
            "phenols",
            "flavanoids",
            "nonflavanoid_phenols",
            "proanthocyanins",
            "color_intensity",
            "hue",
            "diluted_wines",
            "proline"
        )
        assertEquals(ExpectedTheories.wines, relationalized)
    }
}
