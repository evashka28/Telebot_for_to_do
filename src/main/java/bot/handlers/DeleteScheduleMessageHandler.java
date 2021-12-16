package bot.handlers;

import bot.BackendConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
public class DeleteScheduleMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public DeleteScheduleMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/schdel", "");
        System.out.println(query);
        try {
            System.out.println("delete " + backendConnector.deleteSchedule(query));
            message.setText("Расписание удалено!");
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/schdel");
        return false;
    }
}
