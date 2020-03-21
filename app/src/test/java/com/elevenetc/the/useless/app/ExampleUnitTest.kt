package com.elevenetc.the.useless.app

import com.elevenetc.the.useless.app.MathUtils.genMatrix
import com.elevenetc.the.useless.app.MathUtils.getAvailableRects
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val m = arrayOf(
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10),
        BooleanArray(10)
    )

    var colMax = m[0].size - 1
    var rMax = m.size - 1

    @Test
    fun testRandomList() {
        val testSet = mutableSetOf<Int>()
        val testList = mutableListOf<Int>()

        for (i in 0..100) {
            testSet.clear()
            testList.clear()
            for (k in 0..100) {
                testList.add(k)
            }

            testList.forRandom {
                if (!testSet.add(it))
                    throw RuntimeException("copy value: $it in $testSet")
            }
        }
    }

    @Test
    fun addition_isCorrect() {
        var k = 100
        var c = 0
        var r = 0

        while (k > 0) {


            val occupied = m[c][r]
            if (!occupied) {

            }

            k--
        }
    }

    @Test
    fun testAvailability() {

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(true, true, true),
                    arrayOf(true, true, true),
                    arrayOf(true, true, true)
                ), Rect(0, 0, 3, 3)
            )
        ).isTrue()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(false, true, true),
                    arrayOf(true, true, true),
                    arrayOf(true, true, true)
                ), Rect(0, 0, 1, 1)
            )
        ).isFalse()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(false, true, true),
                    arrayOf(true, true, true),
                    arrayOf(true, true, true)
                ), Rect(0, 0, 3, 3)
            )
        ).isFalse()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(true, true, true),
                    arrayOf(true, false, true),
                    arrayOf(true, true, true)
                ), Rect(0, 0, 3, 3)
            )
        ).isFalse()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(true, true, true),
                    arrayOf(true, true, true),
                    arrayOf(true, true, false)
                ), Rect(0, 0, 3, 3)
            )
        ).isFalse()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(true, true, false),
                    arrayOf(true, true, true),
                    arrayOf(true, true, false)
                ), Rect(0, 0, 3, 3)
            )
        ).isFalse()

        assertThat(
            MathUtils.checkIfAvailable(
                arrayOf(
                    arrayOf(true, true, false),
                    arrayOf(true, false, true),
                    arrayOf(true, true, false)
                ), Rect(1, 1, 3, 3)
            )
        ).isFalse()

        println("zed")
    }

    @Test
    fun testGeneration() {

        for (i in 0 until 100) {
            val m = genMatrix(10, 10, true)
            val generate = MathUtils.generate(m, Rect(0, 0, 1, 1))

            generate.forEach { a ->
                generate.forEach { b ->
                    if (a != b) {
                        if (a.intersect(b)) {
                            throw RuntimeException()
                        }
                    }
                }
            }
        }
    }

    @Test
    fun assertMax() {
        assertThat(getMax(0, 10)).isEqualTo(10)
        assertThat(getMax(1, 10)).isEqualTo(9)
        assertThat(getMax(3, 10)).isEqualTo(7)
        assertThat(getMax(-1, 10)).isEqualTo(0)
        assertThat(getMax(10, 10)).isEqualTo(0)
    }

    @Test
    fun getAv() {

        val m = arrayOf(
            Array(2) { true },
            Array(2) { true }
        )

        val result = mutableSetOf<Rect>()
        getAvailableRects(0, 0, 1, 1, m, Rect(0, 0, 3, 3), result)

        if (result.isEmpty()) {

        }
    }

    fun getAvailableCols(c: Int, r: Int, m: Array<Array<Boolean>>): List<Int> {

        val maxMatrixCol = m[0].size - 1
        val maxMatrixRow = m.size - 1

        if (c > maxMatrixCol) return emptyList()
        if (r > maxMatrixRow) return emptyList()

        val maxC = getMax(c, maxMatrixCol)
        var currentC = c + 1
        val result = mutableListOf<Int>()

        while (currentC <= maxC) {
            if (!m[r][currentC]) {
                result.add(currentC)
            }
            currentC++
        }

        return result
    }

    fun getMax(from: Int, colMax: Int): Int {
        if (from < 0) return 0
        return if (from == colMax) 0
        else colMax - from
    }
}
