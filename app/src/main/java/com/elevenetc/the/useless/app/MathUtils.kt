package com.elevenetc.the.useless.app


object MathUtils {

    fun genMatrix(rows: Int, cols: Int, defValue: Boolean): Array<Array<Boolean>> {
        val list = mutableListOf<Array<Boolean>>()

        for (i in 0 until rows) {
            val row = Array(cols) { defValue }
            list.add(row)
        }

        return list.toTypedArray()
    }

    fun generate(m: Array<Array<Boolean>>, maxRect: Rect, minRect: Rect): Set<Rect> {
        val result = mutableSetOf<Rect>()
        val random = mutableSetOf<Rect>()
        var id = 0
        for ((row, rows) in m.withIndex()) {
            for ((col, free) in rows.withIndex()) {
                if (free) {
                    random.clear()
                    getAvailableRects(col, row, 1, 1, m, maxRect, minRect, random)

                    if (random.isEmpty()) continue

                    if (fillRandom(m, result, random, id)) {
                        id++
                    }
                }
            }
        }

        //clearIntersected(result)

        result.forEach { r ->
            if (r.width() < minRect.width()) {
                print("ZZZ: W")
            }

            if (r.height() < minRect.height()) {
                print("ZZZ: H")
            }
        }

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
                        //Log.e("MATH", "over: $cell")
                        return false
                    }
                }
            }
        }

        for ((row, rows) in m.withIndex()) {
            for ((col, free) in rows.withIndex()) {
                if (cell.contains(col, row)) {

                    if (!m[row][col]) {
                        //Log.e("MATH", "over: $cell")
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
        min: Rect,
        result: MutableSet<Rect>
    ) {
        if (!m[r][c]) return
        if (cSize > max.width()) return
        if (rSize > max.height()) return

        val macCol = m[r].size
        val maxRow = m.size
        val width = c + cSize
        val height = r + rSize
        val minWidthFit = width >= min.width()
        val minHeightFit = height >= min.height()

        val cSizeFit = width <= macCol
        val rSizeFit = height <= maxRow

        if (cSizeFit && m[r][width - 1]) {

            val rect = Rect(c, r, width, r)
            val exists = rect.area() > 0
            if (exists && rect.width() >= min.width() && rect.height() >= min.height()) {
                result.add(rect)
            }

            getAvailableRects(c, r, cSize + 1, rSize, m, max, min, result)
        }

        if (rSizeFit && m[height - 1][c]) {

            val rect = Rect(c, r, c, height)
            val exists = rect.area() > 0
            if (exists && rect.width() >= min.width() && rect.height() >= min.height()) {
                result.add(rect)
            }


            getAvailableRects(c, r, cSize, rSize + 1, m, max, min, result)
        }

        if (cSizeFit && rSizeFit && m[height - 1][width - 1]) {

            val rect = Rect(c, r, width, height)
            val exists = rect.area() > 0
            if (exists && rect.width() >= min.width() && rect.height() >= min.height()) {
                result.add(rect)
            }

            getAvailableRects(c, r, cSize + 1, rSize + 1, m, max, min, result)
        }
    }

}