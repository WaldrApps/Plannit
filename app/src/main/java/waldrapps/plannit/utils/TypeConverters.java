package waldrapps.plannit.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class TypeConverters {

    @TypeConverter
    public static Calendar toCalendar(Long value) {
        if(value == null) {
            return null;
        }
        else {
            Date date = new Date(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
    }

    @TypeConverter
    public static Long toLong(Calendar value) {
        if(value == null) {
            return null;
        }
        else {
            return value.getTime().getTime();
        }
    }
}
