package waldrapps.plannit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import waldrapps.plannit.utils.Constants.HOURS_IN_DAY
import waldrapps.plannit.utils.Constants.MINUTES_IN_DAY
import waldrapps.plannit.utils.Time
import waldrapps.plannit.utils.Time.getCurrentDay

class TimetableOverviewView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var eventList : List<Event>? = null
    private val busyPaint = Paint()
    private val borderPaint = Paint()
    private val guidePaint = Paint()
    private val textPaint = Paint()

    init {
        busyPaint.color = Color.RED
        borderPaint.color = Color.BLACK
        borderPaint.style = Paint.Style.FILL_AND_STROKE
        borderPaint.strokeWidth = 10f
        guidePaint.color = Color.LTGRAY
        guidePaint.style = Paint.Style.FILL_AND_STROKE
        guidePaint.strokeWidth = 10f
        textPaint.color = Color.BLACK
        textPaint.textSize = 60f
    }

    override fun onDraw(canvas: Canvas) {

        val hourHeight = height / 26f
        val dayWidth = width / 9f

        //Draw horizontal guidelines and time
        for(i in 1..25) {
            canvas.drawLine(dayWidth , hourHeight * i,  width.toFloat() - dayWidth, hourHeight * i, guidePaint)
            if(i < 25) {
                canvas.drawText((i - 1).toString(), dayWidth / 2, (hourHeight * i) + (hourHeight / 3), textPaint)
            }
        }

        //Draw event blocks
        eventList?.forEach {
            Log.d("STUFF", (it.startTime!!.toFloat() / MINUTES_IN_DAY).toString())
            Log.d("STUFF", height.toString())
            Log.d("STUFF", hourHeight.toString())
            canvas.drawRect(
                    dayWidth * it.day!!.toFloat(),
                    (it.startTime!!.toFloat() / MINUTES_IN_DAY * HOURS_IN_DAY * hourHeight) + hourHeight,
                    dayWidth * (it.day!!.toFloat() + 1f),
                    (it.endTime!!.toFloat() / MINUTES_IN_DAY * HOURS_IN_DAY * hourHeight) + hourHeight,
                    busyPaint
            )
        }

        //Draw vertical border lines and days of week
        for(i in 1..8) {
            canvas.drawLine(dayWidth * i, hourHeight, dayWidth * i, height.toFloat() - hourHeight, borderPaint)
            if(i < 8){
                canvas.drawText(getCurrentDay(i - 1), dayWidth * i, hourHeight / 1.5f, textPaint)
            }
        }
    }

    fun setData(eventList : List<Event>) {
        this.eventList = eventList
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
                resolveSize(suggestedMinimumWidth + paddingLeft + paddingRight, widthMeasureSpec),
                resolveSize(suggestedMinimumHeight + paddingTop + paddingBottom, heightMeasureSpec)
        )
    }
}