package pl.smcebi.recipeme.ui.scanner.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import pl.smcebi.recipeme.ui.common.extensions.dp
import pl.smcebi.recipeme.ui.common.extensions.getColorCompat
import pl.smcebi.recipeme.ui.scanner.R
import pl.smcebi.recipeme.ui.scanner.overlay.ScannerOverlayView.Direction.*

internal class ScannerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    // Background overlay
    private val overlayColor = context.getColorCompat(R.color.scanner_overlay_color)
    private val overlayPaint = Paint().apply {
        style = Paint.Style.FILL
        color = overlayColor
    }

    // The square with rounded edges that cuts the transparency layer
    private val focusingPointPaint = Paint().apply {
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    // Configuring indicator arcs style
    private val cameraIndicatorPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 2.dp
        strokeCap = Paint.Cap.ROUND
    }

    private val viewportRect = RectF()
    private val focusingPointRect = RectF()

    private val topLeftCameraIndicatorPath = Path()
    private val topRightCameraIndicatorPath = Path()
    private val bottomLeftCameraIndicatorPath = Path()
    private val bottomRightCameraIndicatorPath = Path()

    // Here we set the calculated width and height to the drawn shapes
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewportRect.set(
            0f,
            0f,
            w.toFloat(),
            h.toFloat()
        )
        setupFocusingRectSize(w, h)
        topLeftCameraIndicatorPath.setIndicator(w, h, TOP_LEFT, TOP_RIGHT)
        topRightCameraIndicatorPath.setIndicator(w, h, TOP_RIGHT, TOP_LEFT)
        bottomLeftCameraIndicatorPath.setIndicator(w, h, BOTTOM_LEFT, BOTTOM_RIGHT)
        bottomRightCameraIndicatorPath.setIndicator(w, h, BOTTOM_RIGHT, BOTTOM_LEFT)
    }

    // Drawing on canvas
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

    // Method which calculates the cubic bezier path based on the starting and ending direction
    private fun Path.setIndicator(
        w: Int,
        h: Int,
        startingDirection: Direction,
        endingDirection: Direction
    ) {
        val scannerIndicesOffset: Float = SCANNER_INDICES_OFFSET.dp

        val startingWidth = (w / 2f) + (2f * scannerIndicesOffset) * startingDirection.coordinate.x
        val startingHeight = (h / 2f) + (scannerIndicesOffset * startingDirection.coordinate.y)
        moveTo(
            startingWidth,
            startingHeight
        )
        cubicTo(
            startingWidth,
            startingHeight,
            startingWidth + (0.2f * scannerIndicesOffset) * startingDirection.coordinate.x,
            startingHeight + (1.1f * scannerIndicesOffset) * startingDirection.coordinate.y,
            startingWidth + scannerIndicesOffset * endingDirection.coordinate.x,
            startingHeight + scannerIndicesOffset * endingDirection.coordinate.y
        )
    }

    private enum class Direction(val coordinate: Point) {
        TOP_LEFT(Point(-1, -1)),
        TOP_RIGHT(Point(1, -1)),
        BOTTOM_LEFT(Point(-1, 1)),
        BOTTOM_RIGHT(Point(1, 1))
    }

    private companion object {
        const val FOCUSING_RECT_HALF_SIZE = 120
        const val FOCUSING_RECT_CORNER_SIZE = 40
        const val SCANNER_INDICES_OFFSET = FOCUSING_RECT_HALF_SIZE / 2
    }
}