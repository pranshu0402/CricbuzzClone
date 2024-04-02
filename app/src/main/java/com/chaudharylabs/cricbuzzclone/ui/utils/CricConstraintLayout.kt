package com.chaudharylabs.cricbuzzclone.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.chaudharylabs.cricbuzzclone.R

class CricConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var backgroundColor: Int = 0
    private var backgroundRadius: Float = 0f

    init {
        // Load attributes from XML
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CricConstraintLayout,
            defStyleAttr,
            0
        )

        backgroundColor =
            typedArray.getColor(R.styleable.CricConstraintLayout_customBackgroundColor, 0)
        backgroundRadius =
            typedArray.getDimension(R.styleable.CricConstraintLayout_customBackgroundRadius, 0f)

        typedArray.recycle()

        // Set the background color and corner radius
        setBackgroundColorWithRadius(backgroundColor, backgroundRadius)
    }

    // Set background color and corner radius
    private fun setBackgroundColorWithRadius(color: Int, radius: Float) {
        background = BackgroundDrawable(color, radius)
    }

    // Custom drawable to set background color and corner radius
    private class BackgroundDrawable(private val color: Int, private val radius: Float) :
        android.graphics.drawable.ColorDrawable() {

        private val paint = Paint()

        init {
            paint.color = color
        }

        override fun draw(canvas: Canvas) {
            canvas.drawRoundRect(
                bounds.left.toFloat(), bounds.top.toFloat(),
                bounds.right.toFloat(), bounds.bottom.toFloat(),
                radius, radius, paint
            )
        }
    }

    // Public method to set background color programmatically
    fun setCustomBackgroundColor(color: Int) {
        setBackgroundColorWithRadius(color, backgroundRadius)
    }

    // Public method to set background radius programmatically
    fun setCustomBackgroundRadius(radius: Float) {
        setBackgroundColorWithRadius(backgroundColor, radius)
    }
}
