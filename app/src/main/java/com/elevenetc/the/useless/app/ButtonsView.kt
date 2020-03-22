package com.elevenetc.the.useless.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup

class ButtonsView : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setBackgroundColor(Color.WHITE)
    }

    val minButtonWidth = 100
    val minButtonHeight = 50
    var screenWidth = 0
    var screenHeight = 0
    var rows = 0
    var cols = 0

    lateinit var rects: Set<Rect>

    val alphaFill = Paint().apply {
        style = Paint.Style.FILL
        color = Color.argb(0.5f, 1f, 0f, 0f)
    }

    val black = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = 30f
    }

    val greyStroke = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.argb(0.5f, 0.5f, 0.5f, 0.7f)
    }

    val stroke = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.argb(0.5f, 0f, 0f, 0.5f)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        screenWidth = r
        screenHeight = b

        rows = screenHeight / minButtonHeight
        cols = screenWidth / minButtonWidth
//        rows = 5
//        cols = 5


        rects = MathUtils.generate(
            MathUtils.genMatrix(rows, cols, true),
            Rect(0, 0, 4, 4),
            Rect(0, 0, 1, 1)
        )

        if (rects.isEmpty()) {

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        val wSize = width / (cols)
        val hSize = height / (rows)

        dragGrid(width, height, wSize, hSize, canvas)

        for ((index, it) in rects.withIndex()) {
            val left = it.left * wSize
            val top = it.top * hSize
            val right = it.right * wSize
            val bottom = it.bottom * hSize
            canvas.drawRect(left, top, right, bottom, alphaFill)
            canvas.drawRect(left, top, right, bottom, stroke)
            //canvas.drawText("${it.id}: w:${it.width()} h:${it.height()} area:${it.area()}", left + 40, top + 80, black)
        }

        if (width == 0f) {

        }
    }

    private fun dragGrid(
        width: Float,
        height: Float,
        wSize: Float,
        hSize: Float,
        canvas: Canvas
    ) {
        for (c in 0..cols) {
            canvas.drawLine(c * wSize, 0f, c * wSize, height, greyStroke)
        }

        for (r in 0..rows) {
            canvas.drawLine(0f, r * hSize, width, r * hSize, greyStroke)
        }
    }
}