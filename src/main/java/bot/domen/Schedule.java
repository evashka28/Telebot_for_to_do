package bot.domen;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
public class Schedule {

    List<Integer> daysOfWeek;
    private LocalTime dateTime;


}
