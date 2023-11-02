package pl.smcebi.recipeme.ui.scanner.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import pl.smcebi.recipeme.ui.common.extensions.dp
import pl.smcebi.recipeme.ui.common.extensions.getColorCompat
import pl.smcebi.recipeme.ui.scanner.R

internal class ScannerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val overlayColor = context.getColorCompat(R.color.scanner_overlay_color)
    private val overlayPaint = Paint().apply {
        style = Paint.Style.FILL
        color = overlayColor
    }
    private val focusingPointPaint = Paint().apply {
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val viewportRect = RectF()
    private val focusingPointRect = RectF()

    private val cameraIndicatorPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 2.dp
        strokeCap = Paint.Cap.ROUND
    }
    private val topLeftCameraIndicatorPath = Path()
    private val topRightCameraIndicatorPath = Path()
    private val bottomLeftCameraIndicatorPath = Path()
    private val bottomRightCameraIndicatorPath = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewportRect.set(
            0f,
            0f,
            w.toFloat(),
            h.toFloat()
        )
        setupFocusingRectSize(w, h)
        setTopLeftIndicatorPath(w, h)
        setTopRightIndicatorPath(w, h)
        setBottomLeftIndicatorPath(w, h)
        setBottomRightIndicatorPath(w, h)
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.drawRect(viewportRect, overlayPaint)
        canvas.drawRoundRect(
            focusingPointRect,
            FOCUSING_RECT_CORNER_SIZE.dp,
            FOCUSING_RECT_CORNER_SIZE.dp,
            focusingPointPaint
        )
        canvas.drawPath(topLeftCameraIndicatorPath, cameraIndicatorPaint)
        canvas.drawPath(topRightCameraIndicatorPath, cameraIndicatorPaint)
        canvas.drawPath(bottomLeftCameraIndicatorPath, cameraIndicatorPaint)
        canvas.drawPath(bottomRightCameraIndicatorPath, cameraIndicatorPaint)
    }

    private fun setupFocusingRectSize(w: Int, h: Int) {
        val focusingRectDpSize = FOCUSING_RECT_HALF_SIZE.dp

        focusingPointRect.set(
            w / 2 - focusingRectDpSize,
            h / 2 - focusingRectDpSize,
            w / 2 + focusingRectDpSize,
            h / 2 + focusingRectDpSize
        )
    }

    private fun setTopLeftIndicatorPath(w: Int, h: Int) {
        val scannerIndicesOffset: Float = SCANNER_INDICES_OFFSET.dp

        val startingWidth = (w / 2f - 2f * scannerIndicesOffset)
        val startingHeight = (h / 2f - scannerIndicesOffset)
        topLeftCameraIndicatorPath.moveTo(
            startingWidth,
            startingHeight
        )
        topLeftCameraIndicatorPath.cubicTo(
            startingWidth,
            startingHeight,
            startingWidth - 0.2f * scannerIndicesOffset,
            startingHeight - 1.1f * scannerIndicesOffset,
            startingWidth + scannerIndicesOffset,
            startingHeight - scannerIndicesOffset
        )
    }

    private fun setTopRightIndicatorPath(w: Int, h: Int) {
        val scannerIndicesOffset: Float = SCANNER_INDICES_OFFSET.dp

        val startingWidth = (w / 2f + 2f * scannerIndicesOffset)
        val startingHeight = (h / 2f - scannerIndicesOffset)
        topRightCameraIndicatorPath.moveTo(
            startingWidth,
            startingHeight
        )
        topRightCameraIndicatorPath.cubicTo(
            startingWidth,
            startingHeight,
            startingWidth + 0.2f * scannerIndicesOffset,
            startingHeight - 1.1f * scannerIndicesOffset,
            startingWidth - scannerIndicesOffset,
            startingHeight - scannerIndicesOffset
        )
    }

    private fun setBottomLeftIndicatorPath(w: Int, h: Int) {
        val scannerIndicesOffset: Float = SCANNER_INDICES_OFFSET.dp

        val startingWidth = (w / 2f - 2f * scannerIndicesOffset)
        val startingHeight = (h / 2f + scannerIndicesOffset)
        bottomLeftCameraIndicatorPath.moveTo(
            startingWidth,
            startingHeight
        )
        bottomLeftCameraIndicatorPath.cubicTo(
            startingWidth,
            startingHeight,
            startingWidth - 0.2f * scannerIndicesOffset,
            startingHeight + 1.1f * scannerIndicesOffset,
            startingWidth + scannerIndicesOffset,
            startingHeight + scannerIndicesOffset
        )
    }

    private fun setBottomRightIndicatorPath(w: Int, h: Int) {
        val scannerIndicesOffset: Float = SCANNER_INDICES_OFFSET.dp

        val startingWidth = (w / 2f + 2f * scannerIndicesOffset)
        val startingHeight = (h / 2f + scannerIndicesOffset)
        bottomRightCameraIndicatorPath.moveTo(
            startingWidth,
            startingHeight
        )
        bottomRightCameraIndicatorPath.cubicTo(
            startingWidth,
            startingHeight,
            startingWidth + 0.2f * scannerIndicesOffset,
            startingHeight + 1.1f * scannerIndicesOffset,
            startingWidth - scannerIndicesOffset,
            startingHeight + scannerIndicesOffset
        )
    }

    private companion object {
        const val FOCUSING_RECT_HALF_SIZE = 120
        const val FOCUSING_RECT_CORNER_SIZE = 40
        const val SCANNER_INDICES_OFFSET = FOCUSING_RECT_HALF_SIZE / 2
    }
}