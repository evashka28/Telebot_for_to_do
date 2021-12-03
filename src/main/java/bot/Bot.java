package bot;

import bot.domen.Task;
import bot.handlers.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    List<MessageHandler> handlers = List.of(
            new StartMessageHandler(), new TokenMessageHandler(), new LoginMessageHandler(),
            new ReturnToMainMessage(), new ThisismeMessageHandler(), new AboutBotMessageHandler(),
            new ModeSettingsMessageHandler(), new ResetModeMessageHandler(), new AdjustModeMessageHandler(),
            new SettingDaysMessageHandler(), new SelectionDayMessageHandler(), new AdjustTimeMessageHandler(), new TimingMessageHandler(), new TaskListMessageHandler(), new LinkMessageHandler(), new ProjectMessageHandler(), new MyProjectMessageHandler(), new TagSettingsMessageHandler(),
            new TasksMessageHandler(), new TaskListMessageHandler(), new TagsMessageHandler()
    );

    /* Перегружаем метод интерфейса LongPollingBot
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage message = null;
            handlers.stream()
                    .filter(handler -> handler.canHandle(update))
                    .map(handler -> handler.getMessage(update))
                    .forEach(this::sendMessageToUser);
            execute(message);
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
        sendMessage.setChatId(userId);
        sendMessage.enableMarkdown(true);
        sendMessage.setText(task.toString());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
