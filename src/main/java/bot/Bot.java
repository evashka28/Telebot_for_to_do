package bot;

import bot.domen.Task;
import bot.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

// Аннотация @Component необходима, чтобы наш класс распознавался Spring, как полноправный Bean
@Component
// Наследуемся от TelegramLongPollingBot - абстрактного класса Telegram API
public class Bot extends TelegramLongPollingBot {
    final private BackendConnector backendConnector;
    @Autowired
    List<MessageHandler> handlers;
    // Аннотация @Value позволяет задавать значение полю путем считывания из application.yaml
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    public Bot() {
        backendConnector = new BackendConnector();
    }

    /* Перегружаем метод интерфейса LongPollingBot
     */
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = null;
        for (MessageHandler handler : handlers) {
            if (handler.canHandle(update)) {
                SendMessage sendMessage = getMessage(update, handler);
                sendMessageToUser(sendMessage);
                break;
            }
        }
    }

    private SendMessage getMessage(Update update, MessageHandler handler) {
        try {
            return handler.getMessage(update);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessageToUser(SendMessage msg) {
        try {
            if (msg != null) {
                execute(msg);
            }
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

    public void sendTaskToUser(Task task, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(userId));
        sendMessage.enableMarkdown(true);
        sendMessage.setText(task.getContent());

        InlineKeyboards.setInlineTaskKeyboard(sendMessage, String.valueOf(userId), task.getId());

        sendMessageToUser(sendMessage);

    }
}
