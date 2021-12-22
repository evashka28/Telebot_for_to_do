package bot.keyboards;

import bot.entities.Tag;
import bot.entities.TagRequest;
import bot.entities.Task;
import bot.exceptions.BackendConnectorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboards {

    public static void setInlineAddTagToTaskKeyboard(SendMessage message, List<Tag> tags, long taskId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();


        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        int counter = 0;

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/addTtT%d.%s", tag.getId(), taskId));
            keyboardRow.add(button);
            counter++;
            if (counter == 3) {
                keyboard.add(keyboardRow);
                keyboardRow = new ArrayList<>();
                counter = 0;
            }
        }

        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineTaskKeyboard(SendMessage message, String userId, long taskId) {
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

    public static void setInlineAddTagToTaskKeyboard(SendMessage message, String userId, List<Tag> tags) throws BackendConnectorException {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();


        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();


        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/tagget%d", tag.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        button.setText("Без тега");
//        button.setCallbackData(String.format("/tagget%d", -1));
//        row.add(button);
//        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);


        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineDeleteTagKeyboard(SendMessage message, String userId, List<Tag> tags) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Tag tag : tags) {
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

    public static void setInlineGetOneTaskTagKeyboard(SendMessage message, String userId, List<Tag> tags) throws BackendConnectorException {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/getOneTaskByTag%d", tag.getId()));
            keyboardRow.add(button);
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineTaskKeyboard(SendMessage message, List<TagRequest> schedules) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (TagRequest schedule : schedules) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.format("%s Время: %s, Дни: %s", schedule.getTag().getName(), schedule.getDateTime(), schedule.getDaysOfWeek()));
            button.setCallbackData(String.format("/schdel%s", schedule.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineGetTaskByTagKeyboard(SendMessage message, String userId, List<Tag> tags) throws BackendConnectorException {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/getTaskByTag%d", tag.getId()));
            keyboardRow.add(button);
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineTaskKeyboard(SendMessage message, String userId, List<Task> tasks) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Task task : tasks) {
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
}
