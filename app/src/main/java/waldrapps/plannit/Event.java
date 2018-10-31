package waldrapps.plannit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;

@Entity(
        tableName = "events",
        indices = {@Index("contact_id")}
        //TODO: Foreign key to link contact (Problem is that contact is made after event, might have to change that
//        foreignKeys = @ForeignKey(
//                entity = Contact.class,
//                parentColumns = "id",
//                childColumns = "contact_id",
//                onDelete = ForeignKey.CASCADE
//        )
)
public class Event implements IEvent {

    @android.support.annotation.NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "contact_id")
    private String contactId;
    private String name;
    private int color;
    @ColumnInfo(name = "day_of_week")
    private int dayOfWeek;
    private String location;
    @ColumnInfo(name = "start_time")
    private Calendar startTime;
    @ColumnInfo(name = "end_time")
    private Calendar endTime;

    public Event(String id, String contactId, String name, int color, int dayOfWeek, String location, Calendar startTime, Calendar endTime) {
        this.id = id;
        this.contactId = contactId;
        this.name = name;
        this.color = color;
        this.dayOfWeek = dayOfWeek;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public Calendar getStartTime() {
        return startTime;
    }

    @Override
    public Calendar getEndTime() {
        return endTime;
    }
}
