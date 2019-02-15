package waldrapps.plannit

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contact")
class Contact(
        @field:android.support.annotation.NonNull
        @PrimaryKey
        var id : String?,
        var name : String? = null,
        var flag : String? = null,
        color : String?
) {
    var color = color ?: "#1b5e20"

    override fun toString(): String {
        return "$id~$name~$flag~$color"
    }
}