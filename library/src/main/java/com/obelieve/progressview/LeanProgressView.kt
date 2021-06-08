package com.obelieve.progressview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import com.obelieve.progressview.library.R


class LeanProgressView : View {

    companion object{
        val TAG = LeanProgressView::class.java.simpleName
        val LEFT_COLOR = Color.parseColor("#FF5252")
        val RIGHT_COLOR = Color.parseColor("#308BFE")
    }

    private val leftPaint = Paint()
    private val rightPaint = Paint()
    private val progressOffset = 6
    private var leftProgress = 50
    private var rightProgress = 50

    private lateinit var dm:DisplayMetrics
    private var edgeRadius = -1.0F
    private var leftRightGap = 10.0F
    private val leftPath = Path()
    private val rightPath = Path()
    private val tempRectF = RectF()


    constructor(context: Context) : this(context , null)

    constructor(context: Context , attributeSet: AttributeSet?) : this(context , attributeSet , 0)

    constructor(context: Context , attributeSet: AttributeSet? , defStyle : Int) : super(context , attributeSet , defStyle){
        initView(attributeSet)
    }

    private fun initView(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.LeanProgressView)
        dm = context.resources.displayMetrics
        leftPaint.isAntiAlias = true
        rightPaint.isAntiAlias = true
        leftPaint.style = Paint.Style.FILL
        rightPaint.style = Paint.Style.FILL
        leftPaint.color = typedArray.getColor(R.styleable.LeanProgressView_leftProgressColor,LEFT_COLOR)
        rightPaint.color = typedArray.getColor(R.styleable.LeanProgressView_rightProgressColor, RIGHT_COLOR)
        leftRightGap = typedArray.getDimension(R.styleable.LeanProgressView_leftRightGap, dm.density*10.0F)
        var lp = typedArray.getInt(R.styleable.LeanProgressView_leftProgress, -1)
        var rp = typedArray.getInt(R.styleable.LeanProgressView_rightProgress, -1)
        setLeftRightProgressValue(lp, rp)
        typedArray.recycle()
    }

    private fun setLeftRightProgressValue(lp: Int, rp: Int) {
        var lp1 = lp
        var rp1 = rp
        if (lp1 == -1 || rp1 == -1) {
            lp1 = 50
            rp1 = 50
        } else {
            if (lp1 == 0) {
                lp1 = 0
                rp1 = 100
            } else if (rp1 == 0) {
                lp1 = 100
                rp1 = 0
            } else {
                lp1 = ((lp1 * 1.0F) / (lp1+ rp1) * 100).toInt()
                rp1 = ((rp1 * 1.0F) / (lp1 +rp1) * 100).toInt()
            }
        }
        leftProgress = lp1
        rightProgress = rp1
    }

    fun setLeftRightProgress(lp: Int,rp: Int){
        setLeftRightProgressValue(lp,rp)
        measurePath()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measurePath()
    }

    private fun measurePath() {
        if (edgeRadius == -1.0F) {
            edgeRadius = (measuredHeight / 2).toFloat()
        }
        leftPath.reset()
        rightPath.reset()
        val rectLeftWidth = measuredWidth / (leftProgress + rightProgress + progressOffset * 2.0F) * (leftProgress + progressOffset) - leftRightGap - edgeRadius
        val rectRightWidth = measuredWidth / (leftProgress + rightProgress + progressOffset * 2.0F) * (rightProgress + progressOffset) - leftRightGap - edgeRadius
        //左边进度条
        tempRectF.set(0F, 0F, edgeRadius * 2, edgeRadius * 2)
        leftPath.addArc(tempRectF, 180F, 90F)
        leftPath.rLineTo(rectLeftWidth + leftRightGap, 0F)
        leftPath.rLineTo((-leftRightGap).toFloat(), measuredHeight.toFloat())
        leftPath.rLineTo(-rectLeftWidth, 0F)
        leftPath.addArc(tempRectF, 90F, 90F)
        leftPath.close()
        //右边进度条
        val rightStartX = measuredWidth - edgeRadius - rectLeftWidth - leftRightGap
        tempRectF.set(measuredWidth - edgeRadius * 2, 0F, measuredWidth.toFloat(), edgeRadius * 2)
        rightPath.moveTo(rightStartX, 0F)
        rightPath.rLineTo(rectRightWidth + leftRightGap, 0F)
        rightPath.addArc(tempRectF, 90F, -180F)
        rightPath.rLineTo(-rectRightWidth, 0.0F)
        rightPath.rLineTo((-leftRightGap).toFloat(), edgeRadius * 2)
        rightPath.close()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(leftPath,leftPaint)
        canvas.drawPath(rightPath,rightPaint)
    }
}