package bot.handlers;

import bot.BackendConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class AddTagToTaskHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public AddTagToTaskHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/addTtT", "");
        System.out.println(query);
        String[] tags = query.split("\\."); //первый id тега, второй - задачи
        String result = backendConnector.addTagToTask(update.getCallbackQuery().getFrom().getId().toString(),
                Long.parseLong(tags[1]), Long.parseLong(tags[0]));
        message.setText("Тег добавлен к задаче!");
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/addTtT");
        return false;
    }
}
