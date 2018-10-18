package waldrapps.plannit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String data;
    private String flag;
    private int color;
    private String planner;
    private char pref;

    Contact(String name, String flag, String data, int color, char pref)
    {
        this.name = name;
        this.data = data;
        this.flag = flag;
        this.color = color;
        this.planner = planner;
        this.pref = pref;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public String getPlanner() { return planner; }
    public void setPlanner(String planner) { this.planner = planner; }

    public char getPref() { return pref; }
    public void setPref(char pref) { this.pref = pref; }

    @Override
    public String toString() {
        return name + "~" + data + "~" + flag + "~" + color;
    }
}
