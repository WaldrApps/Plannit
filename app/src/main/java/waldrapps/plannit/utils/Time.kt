package waldrapps.plannit.utils

import java.time.LocalTime

object Time {

    fun getCurrentDay(position: Int) : String {
        return when (position) {
            0 -> "Mon"
            1 -> "Tue"
            2 -> "Wed"
            3 -> "Thu"
            4 -> "Fri"
            5 -> "Sat"
            6 -> "Sun"
            else -> "?"
        }
    }

    fun getParsedTimeInMinutes(time: String) : Int {
        var minutes = LocalTime.parse(time).hour * 60
        minutes += LocalTime.parse(time).minute
        return minutes
    }

    fun getDisplayableTimeFromMinutes(time: Int): String {
        return "${time / 60}:${time % 60}"
    }
}