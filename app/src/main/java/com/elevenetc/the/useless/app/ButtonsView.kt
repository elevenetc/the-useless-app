package com.elevenetc.the.useless.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children


class ButtonsView : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    //    val debug = Debug()
    val debug = Debug(rects = true)

    init {
        setBackgroundColor(Color.WHITE)
        setWillNotDraw(false)
    }

    val minButtonWidth = 180
    val minButtonHeight = 90
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

    var generated = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        //measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (!generated) {
            generated = true
            screenWidth = width
            screenHeight = height

            rows = screenHeight / minButtonHeight
            cols = screenWidth / minButtonWidth
//        rows = 5
//        cols = 5


            rects = MathUtils.generate(
                MathUtils.genMatrix(rows, cols, true),
                Rect(3, 2),
                Rect(1, 1)
            )



            rects.forEach { rect ->
                val b = Button(context)
                val w = rect.width() * minButtonWidth
                val h = rect.height() * minButtonHeight
                val x = rect.left * minButtonWidth
                val y = rect.top * minButtonHeight
                val lp = LayoutParams(w, h, x, y)
                b.layoutParams = lp
                addView(b)
            }
            //invalidate()
        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        val mPaddingLeft = 0
        val mPaddingTop = 0
        val mPaddingRight = 0
        val mPaddingBottom = 0

        var maxWidth = width
        var maxHeight = height

        // Account for padding too
        maxWidth = mPaddingLeft + mPaddingRight;
        maxHeight = mPaddingTop + mPaddingBottom;

        // Check against minimum height and width
        maxHeight = Math.max(maxHeight, suggestedMinimumHeight);
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth);

        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        );
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount

        val mPaddingLeft = 0
        val mPaddingTop = 0

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as LayoutParams
                val childLeft: Int = mPaddingLeft + lp.x
                val childTop: Int = mPaddingTop + lp.y
                child.layout(
                    childLeft, childTop,
                    childLeft + child.measuredWidth,
                    childTop + child.measuredHeight
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val children = children

        val action: (View) -> Unit = {
            val visibility = it.visibility
            if (visibility == View.VISIBLE) {

            }
        }
        children.forEach(action)

        val chC = childCount
        if (chC == 0) {

        }

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        val wSize = width / (cols)
        val hSize = height / (rows)

        if (debug.grid)
            dragGrid(width, height, wSize, hSize, canvas)

        if (debug.rects)
            for ((index, it) in rects.withIndex()) {
                val left = it.left * wSize
                val top = it.top * hSize
                val right = it.right * wSize
                val bottom = it.bottom * hSize
                canvas.drawRect(left, top, right, bottom, alphaFill)
                canvas.drawRect(left, top, right, bottom, stroke)


                if (debug.ids)
                    canvas.drawText(
                        "${it.id}: w:${it.width()} h:${it.height()} area:${it.area()}",
                        left + 40,
                        top + 80,
                        black
                    )
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

    data class Debug(
        val grid: Boolean = false,
        val ids: Boolean = false,
        val rects: Boolean = false
    )

    class LayoutParams(width: Int, height: Int, var x: Int, var y: Int) : ViewGroup.LayoutParams(width, height)
}