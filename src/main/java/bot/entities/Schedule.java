package bot.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
public class Schedule {

    String id;

    List<Integer> daysOfWeek;
    private LocalTime dateTime;


}
