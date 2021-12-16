package bot.handlers;

import bot.BackendConnector;
import bot.domen.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
public class DeleteTagsMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public DeleteTagsMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        if(update.hasMessage()) {
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String userId = update.getMessage().getFrom().getId() + "";
            try {
                message.setText("Выбери тег, который ты хочешь удалить:");
                setInlineTagKeyboard(message, userId, getTag(userId));
            } catch (Exception e) {
                message.setText("Ошибка!");
                e.printStackTrace();
            }
        }
        if(update.hasCallbackQuery()) {
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            String query = update.getCallbackQuery().getData().replace("/deleteTag", "");
            long tagId = Long.parseLong(query);
            String userId = update.getCallbackQuery().getFrom().getId() + "";
            try {
                System.out.println("delete " + backendConnector.deleteTag(userId, Long.parseLong(query)));
                message.setText("Тег удален!");
            } catch (Exception e) {
                message.setText("Ошибка!");
                e.printStackTrace();
            }

        }
        return message;
    }

    public void setInlineTagKeyboard(SendMessage message, String userId, List<Tag> tags){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(Tag tag: tags){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/deleteTag%d", tag.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }
        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText())
            return update.getMessage().getText().equalsIgnoreCase("Удалить тег");
        if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/deleteTag");
        }
        return false;
    }

    private List<Tag> getTag(String userId) throws Exception {
        return backendConnector.getTags(userId);
    }
}
