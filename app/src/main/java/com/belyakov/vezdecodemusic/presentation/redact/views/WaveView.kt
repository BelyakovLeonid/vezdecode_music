package com.belyakov.vezdecodemusic.presentation.redact.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.utils.dpToPx
import com.belyakov.vezdecodemusic.utils.toTimeString
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

private const val ELEMENTS_NUMBER = 100

private const val CHUNK_WIDTH = 3
private const val GAP_WIDTH = 5

private const val RULLER_HEIGHT_SMALL = 2
private const val RULLER_HEIGHT_MEDIUM = 4
private const val RULLER_HEIGHT_BIG = 9

private const val RULLER_STROKE_WIDTH = 1
private const val RULLER_HEIGHT = 26
private const val TEXT_SIZE = 9
private const val TEXT_OFFSET = 4
private const val OFFSET_PERCENT = 0.4f

class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var currentXPosition = 0
    private var maxXPosition = 0

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            when {
                currentXPosition + distanceX.toInt() < 0 -> {
                    scrollBy(-currentXPosition, 0)
                    currentXPosition = 0
                }
                currentXPosition + distanceX.toInt() > maxXPosition -> {
                    scrollBy(maxXPosition - currentXPosition, 0)
                    currentXPosition = maxXPosition
                }
                else -> {
                    scrollBy(distanceX.toInt(), 0)
                    currentXPosition += distanceX.toInt()
                }
            }
            return true
        }
    }
    private val detector: GestureDetector = GestureDetector(context, gestureListener)

    private var topTextVisible: Boolean = false
    private var topText: String = ""
    private var bottomTextVisible: Boolean = false
    private var bottomText: String = ""

    //random dataset
    private val dataSet = generateRandom()
    private fun generateRandom(): ByteArray {
        val result = ByteArray(ELEMENTS_NUMBER)
        for (i in 0 until ELEMENTS_NUMBER) {
            result[i] = Random.nextInt(0, 128).toByte()
        }
        return result
    }

    private val rullerTitles = generateRullerTitles()
    private fun generateRullerTitles(): List<String> {
        val result = mutableListOf<String>()
        for (i in 0..dataSet.size.div(10)) {
            result.add((i * 10_000L).toTimeString())
        }
        return result
    }

    private var chunkCenterY: Float = 0f
    private var chunkHeightProportion: Float = 0f

    private val textOffset = dpToPx(TEXT_OFFSET)
    private val rullerHeight = dpToPx(RULLER_HEIGHT)
    private val rullerHeightSmall = dpToPx(RULLER_HEIGHT_SMALL)
    private val rullerHeightMedium = dpToPx(RULLER_HEIGHT_MEDIUM)
    private val rullerHeightBig = dpToPx(RULLER_HEIGHT_BIG)
    private val rullerStrokeWidth = dpToPx(RULLER_STROKE_WIDTH)
    private val chunkWidth = dpToPx(CHUNK_WIDTH)
    private val gapWidth = dpToPx(GAP_WIDTH)
    private val chunkWithGapWidth = chunkWidth + gapWidth

    private val rullerTextBounds = Rect()

    private val colorGrayUltraLight = ContextCompat.getColor(context, R.color.colorGrayUltraLight)
    private val colorGrayLight = ContextCompat.getColor(context, R.color.colorGrayLight)
    private val colorBlue = ContextCompat.getColor(context, R.color.colorBlue)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = chunkWidth
        strokeCap = Paint.Cap.ROUND
        textSize = dpToPx(TEXT_SIZE)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val maxChunk = dataSet.maxOrNull() ?: 1
        chunkHeightProportion = ((h.toFloat() - rullerHeight) * OFFSET_PERCENT) / maxChunk
        chunkCenterY = (h.toFloat() + rullerHeight) / 2
        maxXPosition = (dataSet.size * chunkWithGapWidth).toInt() - w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(colorGrayUltraLight)

        drawCharts(canvas)
        drawRuller(canvas)
        if (topTextVisible) {
            canvas?.drawText(
                topText,
                textOffset,
                rullerHeight + textOffset + rullerTextBounds.height(),
                paint
            )
        }
        if (bottomTextVisible) {
            canvas?.drawText(
                bottomText,
                textOffset,
                height - textOffset,
                paint
            )
        }
    }

    private fun drawCharts(canvas: Canvas?) {
        paint.color = colorBlue
        paint.strokeWidth = chunkWidth

        for (i in dataSet.indices) {
            canvas?.drawLine(
                i * chunkWithGapWidth + gapWidth,
                chunkCenterY - (dataSet[i] * chunkHeightProportion) / 2,
                i * chunkWithGapWidth + gapWidth,
                chunkCenterY + (dataSet[i] * chunkHeightProportion) / 2,
                paint
            )
        }
    }

    private fun drawRuller(canvas: Canvas?) {
        paint.color = colorGrayLight
        paint.strokeWidth = rullerStrokeWidth

        canvas?.drawLine(0f, rullerHeight, (maxXPosition + width).toFloat(), rullerHeight, paint)
        for (i in dataSet.indices) {
            val rullerChunkHeight = when {
                i == 0 -> rullerHeightSmall
                i.rem(10) == 0 -> rullerHeightBig
                i.rem(2) == 0 -> rullerHeightMedium
                else -> rullerHeightSmall
            }

            if (i != 0 && i.rem(10) == 0) {
                paint.getTextBounds(
                    rullerTitles[i.div(10)],
                    0,
                    rullerTitles[i.div(10)].length,
                    rullerTextBounds
                )
                canvas?.drawText(
                    rullerTitles[i.div(10)],
                    i * chunkWithGapWidth + gapWidth - (rullerTextBounds.width() / 2),
                    rullerHeight - rullerHeightBig - dpToPx(4),
                    paint
                )
            }

            canvas?.drawLine(
                i * chunkWithGapWidth + gapWidth,
                rullerHeight - rullerChunkHeight,
                i * chunkWithGapWidth + gapWidth,
                rullerHeight,
                paint
            )
        }
    }

    fun setTopText(text: String) {
        topText = text
        invalidate()
    }

    fun setBottomText(text: String) {
        bottomText = text
        invalidate()
    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(null)
        topText = (state as SavedState).topText
        bottomText = state.bottomText
        topTextVisible = state.topTextVisible
        bottomTextVisible = state.bottomTextVisible
        invalidate()
    }

    override fun onSaveInstanceState(): Parcelable? {
        super.onSaveInstanceState()
        return SavedState(topText, bottomText, topTextVisible, bottomTextVisible)
    }

    fun setTopTextVisible(isVisible: Boolean) {
        topTextVisible = isVisible
        invalidate()
    }

    fun setBottomTextVisible(isVisible: Boolean) {
        bottomTextVisible = isVisible
        invalidate()
    }

    @Parcelize
    private data class SavedState(
        val topText: String,
        val bottomText: String,
        val topTextVisible: Boolean,
        val bottomTextVisible: Boolean
    ) : Parcelable
}