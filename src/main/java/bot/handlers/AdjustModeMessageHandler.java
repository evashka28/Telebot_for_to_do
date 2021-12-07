package bot.handlers;

import bot.BackendConnector;
import bot.Keyboards;
import bot.domen.Task;
import bot.domen.Tag;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AdjustModeMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public AdjustModeMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        String userId = update.getMessage().getFrom().getId() + "";
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Выбери тег для которого хочешь настроить режим \n \nИменно в таком режиме будут  появляться задачи в Todoist \nДни и время ты выбираешь сам ⬇️ ️");
        //Keyboards.setButtons5(message);
        setInlineTagKeyboard(message, userId);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Режим чтения");
        }
        return false;
    }

    public void setInlineTagKeyboard(SendMessage message, String userId){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<Tag> tags = null;
        try {
            tags = backendConnector.getTags(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(Tag tag: tags){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/tagget%d", tag.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }


        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
