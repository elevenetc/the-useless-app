package com.elevenetc.the.useless.app

import android.util.Log


object MathUtils {

    fun genMatrix(rows: Int, cols: Int, defValue: Boolean): Array<Array<Boolean>> {
        val list = mutableListOf<Array<Boolean>>()

        for (i in 0 until rows) {
            val row = Array(cols) { defValue }
            list.add(row)
        }

        return list.toTypedArray()
    }

    fun generate(m: Array<Array<Boolean>>, maxRect: Rect): Set<Rect> {
        val result = mutableSetOf<Rect>()
        val random = mutableSetOf<Rect>()
        var id = 0
        for ((row, rows) in m.withIndex()) {
            for ((col, free) in rows.withIndex()) {
                if (free) {
                    random.clear()
                    getAvailableRects(col, row, 1, 1, m, maxRect, random)

                    if (random.isEmpty()) continue

                    if (fillRandom(m, result, random, id)) {
                        id++
                    }
                }
            }
        }

        //clearIntersected(result)

        return result
    }

    fun fillRandom(m: Array<Array<Boolean>>, result: MutableSet<Rect>, random: Set<Rect>, id: Int): Boolean {
        var filled = false
        random.forRandom { cell ->
            if (fill(m, cell)) {
                cell.id = id
                result.add(cell)
                filled = true
                return@forRandom
            }
        }

        return filled
    }

    fun checkIfAvailable(m: Array<Array<Boolean>>, cell: Rect): Boolean {

        var rowStart = cell.left
        var rowEnd = cell.right
        var colStart = cell.top
        var colEnd = cell.bottom

        var r = rowStart
        var c = colStart

        while (r < rowEnd) {

            while (c < colEnd) {
                if (!m[r][c]) return false
                c++
            }

            c = colStart
            r++
        }

        return true
    }

    fun clearIntersected(rects: MutableSet<Rect>) {
        val toRemove = mutableSetOf<Rect>()
        rects.forEach { a ->
            rects.forEach { b ->
                if (a != b) {
                    if (a.intersect(b)) {
                        toRemove.add(a)
                    }
                }
            }
        }

        toRemove.forEach {
            val removed = rects.remove(it)
            if (removed) {

            }
        }
    }

    fun fill(m: Array<Array<Boolean>>, cell: Rect): Boolean {

        //TODO: optimise double array

        for ((row, rows) in m.withIndex()) {
            for ((col, free) in rows.withIndex()) {
                if (cell.contains(col, row)) {

                    if (!m[row][col]) {
                        Log.e("MATH", "over: $cell")
                        return false
                    }
                }
            }
        }

        for ((row, rows) in m.withIndex()) {
            for ((col, free) in rows.withIndex()) {
                if (cell.contains(col, row)) {

                    if (!m[row][col]) {
                        Log.e("MATH", "over: $cell")
                    }

                    m[row][col] = false
                }
            }
        }

        return true
    }

    fun getAvailableRects(
        c: Int,
        r: Int,
        cSize: Int,
        rSize: Int,
        m: Array<Array<Boolean>>,
        max: Rect,
        result: MutableSet<Rect>
    ) {
        if (!m[r][c]) return
        if (cSize > max.width()) return
        if (rSize > max.height()) return

        val macCol = m[r].size
        val maxRow = m.size
        val cSizeFit = c + cSize <= macCol
        val rSizeFit = r + rSize <= maxRow

        if (cSizeFit && m[r][c + cSize - 1]) {
            val rect = Rect(c, r, c + cSize, r)
            val exists = rect.area() > 0
            if (exists) {
                result.add(rect)
            }
            getAvailableRects(c, r, cSize + 1, rSize, m, max, result)
        }

        if (rSizeFit && m[r + rSize - 1][c]) {
            val rect = Rect(c, r, c, r + rSize)
            val exists = rect.area() > 0
            if (exists) {
                result.add(rect)
            }
            getAvailableRects(c, r, cSize, rSize + 1, m, max, result)
        }

        if (cSizeFit && rSizeFit && m[r + rSize - 1][c + cSize - 1]) {
            val rect = Rect(c, r, c + cSize, r + rSize)
            val exists = rect.area() > 0
            if (exists) {
                result.add(rect)
            }
            getAvailableRects(c, r, cSize + 1, rSize + 1, m, max, result)
        }
    }

}