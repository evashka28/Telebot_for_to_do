package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Tag;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TagSelectionHandler implements MessageHandler{
    private final BackendConnector backendConnector;

    public TagSelectionHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        String userId = update.getCallbackQuery().getFrom().getId().toString();
        message.setText("Выберите тег:");

        String query = update.getCallbackQuery().getData().replace("/tasktagsel", "");
        long taskId = Long.parseLong(query);
        InlineKeyboards.setInlineTagKeyboard(message, getTags(userId), taskId);

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/tasktagsel");
        return false;
    }

    private List<Tag> getTags(String userId) {
        List<Tag> tags = new ArrayList<Tag>();
        try {
            tags = backendConnector.getTags(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return tags;
    }


}
