package bot.handlers;

import bot.BackendConnector;
import bot.domen.Task;
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
public class GiveTaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public GiveTaskMessageHandler() {
        this.backendConnector = new BackendConnector();
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setText("Отличное время, чтобы изучить что-то новое. Например  это:" +
                "  ");

        if(update.hasMessage()) {
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String userId = update.getMessage().getFrom().getId() + "";
            setInlineTaskKeyboard(message, userId, getTasks(userId));
        }
        if(update.hasCallbackQuery()) {
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            String query = update.getCallbackQuery().getData().replace("/getTaskByTag", "");
            long tagId = Long.parseLong(query);
            String userId = update.getCallbackQuery().getFrom().getId() + "";
            setInlineTaskKeyboard(message, userId, getTasks(userId, tagId));
        }


        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equalsIgnoreCase("Дай Задачу");
        }
        if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/getTaskByTag");
        }
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, String userId, List<Task> tasks){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(Task task: tasks){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(task.getContent());
            button.setCallbackData(String.format("/taskget%d", task.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    private List<Task> getTasks(String userId){
        return backendConnector.getTasks(userId);
    }

    private List<Task> getTasks(String userId, long tagId){
        return backendConnector.getTasksByTag(userId, tagId);
    }
}
