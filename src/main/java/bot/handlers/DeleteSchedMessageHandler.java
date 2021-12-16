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
public class DeleteSchedMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public DeleteSchedMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String[] words = update.getCallbackQuery().getData().split("/");
        String tagId = words[2].replace("schtagid", "");
        System.out.println(tagId);
        String query = words[1].replace("schdel", "");
        System.out.println(query);
        System.out.println("delete " + backendConnector.deleteSch(tagId, query));
        message.setText("Расписание удалено!");
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/schdel");
        return false;
    }
}
