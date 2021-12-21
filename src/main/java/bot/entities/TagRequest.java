package bot.entities;

import java.io.Serializable;
import java.time.LocalTime;


public class TagRequest implements Serializable {
    private String id;

    private String daysOfWeek;

    private Tag tag;

    private LocalTime dateTime;


    public TagRequest() {
    }

    public String getId() {
        return id;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateTime(LocalTime dateTime) {
        this.dateTime = dateTime;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
