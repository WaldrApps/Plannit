package waldrapps.plannit.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_event.view.*
import waldrapps.plannit.Event
import waldrapps.plannit.R
import waldrapps.plannit.utils.Time
import waldrapps.plannit.utils.getLayoutInflater

/**
 * https://github.com/xiprox/Timetable
 */

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var eventList: List<Event>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.getLayoutInflater().inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Event = eventList!![position]

        holder.view.name.text = item.name ?: "?"
        holder.view.place.text = item.place ?: "?"
        holder.view.startTime.text = Time.getDisplayableTimeFromMinutes(item.startTime ?: -1)
        holder.view.endTime.text = Time.getDisplayableTimeFromMinutes(item.endTime ?: -1)

        //TODO:Make size match timing
        if (item.length > 60) {
            holder.view.layoutParams.height = (holder.view.context.resources.getDimension(R.dimen.event_item_height) * 2).toInt()
        }
    }

    internal fun setEvents(eventList: List<Event>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (eventList != null) {
            eventList!!.size
        }
        else {
            0
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}