package bot.handlers;

import bot.BackendConnector;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final BackendConnector backendConnector;

    private final TimeZoneMapBean timeZoneMapBean;

    @Autowired
    public TimeZoneHandler(BackendConnector backendConnector, TimeZoneMapBean timeZoneMapBean) {
        this.backendConnector = backendConnector;
        this.timeZoneMapBean = timeZoneMapBean;
    }

    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        String userId = update.getMessage().getFrom().getId() + "";
        Location location = update.getMessage().getLocation();
        System.out.println(location.getLatitude() + " " + location.getLongitude());
        String timezone = timeZoneMapBean.getTimeZone(location.getLatitude(), location.getLongitude());
        backendConnector.setTimezone(userId, timezone);
        message.setText("Ваш часовой пояс - " + timezone);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasLocation())
            return true;
        return false;
    }
}