package bot.handlers;

import bot.connectors.BackendConnector;
import bot.TextMessage;
import bot.TimeZoneMapBean;
import bot.exceptions.BackendConnectorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
@Slf4j
public class TimeZoneHandler implements MessageHandler {
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
        log.info(location.getLatitude() + " " + location.getLongitude());
        String timezone = timeZoneMapBean.getTimeZone(location.getLatitude(), location.getLongitude());
        try {
            backendConnector.setTimezone(userId, timezone);
            message.setText(TextMessage.timeZone+ timezone);
        } catch (BackendConnectorException e) {
            message.setText(TextMessage.error);
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && update.getMessage().hasLocation();
    }
}
