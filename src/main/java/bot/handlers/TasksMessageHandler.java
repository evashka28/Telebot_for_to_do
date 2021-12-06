package bot.handlers;

import bot.BackendConnector;
import bot.Keyboards;
import bot.domen.Tag;
import bot.domen.Task;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TasksMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public TasksMessageHandler() {
        this.backendConnector = new BackendConnector();
    }


    @Override
    public SendMessage getMessage(Update update) {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;





        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Список твоих задач на данный момент: ");
        setInlineTaskKeyboard(message, userId);
        //Keyboards.setButtons(message);

        return message;
    }




    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Задачи");
        }
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, String userId){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<Task> tasks = null;
        try {
            tasks = backendConnector.getTasks(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(Task task: tasks){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(task.getContent());
            button.setCallbackData(String.format("/task%d", task.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }


        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
