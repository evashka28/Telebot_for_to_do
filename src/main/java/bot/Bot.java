package bot;

import bot.domen.Tag;
import bot.domen.Task;
import bot.handlers.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

// Аннотация @Component необходима, чтобы наш класс распознавался Spring, как полноправный Bean
@Component
// Наследуемся от TelegramLongPollingBot - абстрактного класса Telegram API
public class Bot extends TelegramLongPollingBot {
    // Аннотация @Value позволяет задавать значение полю путем считывания из application.yaml
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    final private BackendConnector backendConnector;

    List<MessageHandler> handlers = List.of(
            new StartMessageHandler(), new TokenMessageHandler(), new LoginMessageHandler(),
            new ReturnToMainMessage(), new ThisismeMessageHandler(), new AboutBotMessageHandler(),
            new AdjustModeMessageHandler(),
            new SelectionDayMessageHandler(), new LinkMessageHandler(), new ProjectMessageHandler(),
            new MyProjectMessageHandler(), new TagSettingsMessageHandler(),
            new TasksMessageHandler(), new TagsMessageHandler(),
            new AddTagToTaskHandler(),
            new ReturnTagMessageHandler(), new TaskMessageHandler(), new DeleteTaskMessageHandler(),
            new CompleteTaskMessageHandler(), new CreateTagsMessageHandler(), new TasksByTagHandler(),
            new TagSelectionHandler()
    );

    public Bot() {
        backendConnector = new BackendConnector();
    }

    /* Перегружаем метод интерфейса LongPollingBot
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage message = null;
            handlers.stream()
                    .filter(handler -> handler.canHandle(update))
                    .map(handler -> {
                        try {
                            return handler.getMessage(update);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .forEach(this::sendMessageToUser);
            if(message != null) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToUser(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public void sendTaskToUser(Task task, long userId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(userId));
        sendMessage.enableMarkdown(true);
        sendMessage.setText(task.getContent());

        InlineKeyboards.setInlineTaskKeyboard(sendMessage, String.valueOf(userId), task.getId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
