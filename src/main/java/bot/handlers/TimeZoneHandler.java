package bot.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import us.dustinj.timezonemap.TimeZoneMap;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
public class TimeZoneHandler implements MessageHandler{
    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        Location location = update.getMessage().getLocation();
        System.out.println(location.getLatitude() + " " + location.getLongitude());
        TimeZoneMap map = TimeZoneMap.forEverywhere();
        String timeZone = map.getOverlappingTimeZone(location.getLatitude(), location.getLongitude()).getZoneId();
        message.setText("Ваш часовой пояс - " + timeZone);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasLocation())
            return true;
        return false;
    }
}
