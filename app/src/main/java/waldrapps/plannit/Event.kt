package waldrapps.plannit

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "events")
class Event(
        @field:android.support.annotation.NonNull
        @PrimaryKey
        var id: String?,
        @ColumnInfo(name = "contact_id")
        var contactId: String?,
        name : String,
        day : Int,
        place : String,
        startTime: Int,
        endTime: Int
) : Serializable {

    var name: String? = name
    var place: String? = place
    var day: Int? = day
    var startTime: Int? = startTime
    var endTime: Int? = endTime
    val length: Int get() = (endTime ?: 0) - (startTime ?: 0)

    override fun toString(): String {
        return "$contactId, $day, $startTime, $endTime"
    }
}