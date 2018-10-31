package waldrapps.plannit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @android.support.annotation.NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String flag;
    private int color;

    public Contact(String id, String name, String flag, int color) {
        this.id = id;
        this.name = name;
        this.flag = flag;
        this.color = color;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    @Override
    public String toString() {
        return name + "~" + flag + "~" + color;
    }
}
