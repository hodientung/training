package com.example.voicelockscreen.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.abs

class PatternView : View {
    companion object {
        private const val TAG = "PatternView"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )


    private var density: Float = context.resources.displayMetrics.density
    private val paint = Paint()
    private val pointPositions = mutableListOf<PatternPoint>()
    private val patternPath = mutableListOf<PatternPoint>()

    private var touchX = 0.0f
    private var touchY = 0.0f

    var onPatternChanged: ((patternString: String) -> Unit)? = null
    var onCheckPattern: ((patternString: String) -> Unit)? = null
    var src: Bitmap? = null
    var color: Int? = null

    init {

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        paint.isAntiAlias = true
    }

    fun setImageRes(src: Bitmap) {
        this.src = src
        invalidate()
    }

    fun setColorPath(color: Int) {
        this.color = color
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = measuredWidth
        var height = measuredHeight
        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)
        setMeasuredDimension(width, height)
        initPatternPoints(
            width * 0.5f,
            height * 0.5f,
            8 * density
        )
    }

    private fun initPatternPoints(centerX: Float, centerY: Float, radius: Float) {
        pointPositions.clear()
        var point = PatternPoint(centerX * 0.5f, centerY * 0.5f, radius, "a")
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY * 0.5f, radius, "b")
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY * 0.5f, radius, "c")
        pointPositions.add(point)


        point = PatternPoint(centerX * 0.5f, centerY, radius, "d")
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY, radius, "e")
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY, radius, "f")
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f, centerY * 0.5f + centerY, radius, "g")
        pointPositions.add(point)

        point = PatternPoint(centerX, centerY * 0.5f + centerY, radius, "h")
        pointPositions.add(point)

        point = PatternPoint(centerX * 0.5f + centerX, centerY * 0.5f + centerY, radius, "i")
        pointPositions.add(point)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //drawBackground(canvas)
        drawPoint(canvas)
        drawPatternPath(canvas)
        // drawPatterGuideLine(canvas)
    }

    private fun drawPoint(canvas: Canvas?) {
        paint.color = Color.parseColor("#ffffff")
        for (point in pointPositions) {
            paint.style = if (point.isSelected) {
                Paint.Style.FILL
            } else {
                Paint.Style.STROKE
            }
            paint.color = if (point.isSelected) {
                Color.parseColor("#FF6200EE")
            } else {
                Color.parseColor("#FF018786")
            }
            //val bitmap = BitmapFactory.decodeResource(resources, src)
            val bitmap = src
            bitmap?.let {
                canvas?.drawBitmap(
                    it,
                    point.x - it.width / 2,
                    point.y - it.height / 2,
                    paint
                )
            }
            //canvas?.drawCircle(point.x, point.y, point.r, paint)
        }
    }

    private fun drawBackground(canvas: Canvas?) {
        canvas?.drawColor(Color.parseColor("#666666"))
    }

    private fun drawPatternPath(canvas: Canvas?) {
        color?.let {
            paint.color = ContextCompat.getColor(context,it)
        }
        paint.strokeWidth = 10f
        if (patternPath.size < 2) {
            return
        }
        if (patternPath.size > pointPositions.size) {
            return
        }
        for (i in 1 until patternPath.size) {
            canvas?.drawLine(
                patternPath[i - 1].x,
                patternPath[i - 1].y,
                patternPath[i].x,
                patternPath[i].y, paint
            )
        }
    }

//    private fun drawPatterGuideLine(canvas: Canvas?) {
//        paint.color = Color.BLACK
//        paint.strokeWidth = 20f
//        if (patternPath.size == 0) {
//            return
//        }
//        if (patternPath.size >= pointPositions.size) {
//            return
//        }
//        canvas?.drawLine(
//            patternPath[patternPath.size - 1].x,
//            patternPath[patternPath.size - 1].y,
//            touchX,
//            touchY, paint
//        )
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                touchX = event.x
                touchY = event.y
                for (point in pointPositions) {
                    if (abs(event.x - point.x) < (src?.width?.div(2) ?: 0) * 1
                        && abs(event.y - point.y) < (src?.height?.div(2) ?: 0) * 1
                    ) {
                        if (!point.isSelected) {
                            point.isSelected = true
                            patternPath.add(point)

                            onPatternChangedListener()
                        }
                    }
                }
                invalidate()

            }
            MotionEvent.ACTION_UP -> {
                onCheckPattern()
                clearPattern()
                invalidate()
            }
        }
        return true
    }

    private fun onPatternChangedListener() {
        onPatternChanged?.let { onPatternChanged ->
            var patterString = ""
            for (point in patternPath) {
                if (point.isSelected) {
                    patterString += point.pattern

                }
            }
            onPatternChanged(patterString)
        }
    }

    private fun onCheckPattern() {
        onCheckPattern?.let { onCheckPattern ->
            var patterString = ""
            for (point in patternPath) {
                if (point.isSelected) {
                    patterString += point.pattern

                }
            }
            onCheckPattern(patterString)
        }
    }

    private fun clearPattern() {
        for (point in pointPositions) {
            point.isSelected = false
        }
        touchX = 0f
        touchY = 0f
        patternPath.clear()
    }

    data class PatternPoint(
        val x: Float = 0.0f,
        val y: Float = 0.0f,
        val r: Float = 0.0f,
        val pattern: String = ""
    ) {
        var isSelected = false
    }
}