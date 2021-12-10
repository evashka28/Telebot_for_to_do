package bot.handlers;

import bot.BackendConnector;
import bot.domen.Task;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TaskMessageHandler implements MessageHandler{
    private final BackendConnector backendConnector;

    public TaskMessageHandler() {
        backendConnector = new BackendConnector();
    }


    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/taskget", "");

        Task task = backendConnector.getTask(userId, Long.parseLong(query));

        message.setText(task.getContent());
        setInlineTaskKeyboard(message, userId, task.getId());

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/taskget");
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, String userId, long taskId){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton deleteButton = new InlineKeyboardButton();
        deleteButton.setCallbackData(String.format("/taskdel%d", taskId));
        deleteButton.setText("Удалить❌");
        row.add(deleteButton);

        InlineKeyboardButton completeButton = new InlineKeyboardButton();
        completeButton.setCallbackData(String.format("/taskcom%d", taskId));
        completeButton.setText("Выполнить✅");
        row.add(completeButton);

        InlineKeyboardButton tagSelectionButton = new InlineKeyboardButton();
        tagSelectionButton.setCallbackData(String.format("/tasktagsel%d", taskId));
        tagSelectionButton.setText("Выбрать тег");
        row.add(tagSelectionButton);

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
