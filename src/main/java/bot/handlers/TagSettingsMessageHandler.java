package bot.handlers;

import bot.Keyboards;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class TagSettingsMessageHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Добавляя тег к задаче, ты условно делишь свои задачи на категории.\n\n" +
                        "Благодаря этому можно быстро получать список задач или конкретную задачу\n" +
                        "Это удобно, я проверял\uD83D\uDE09\n\n" +
                        "Ты можешь\n" +
                        "➡️Посмотреть все свои теги \n" +
                        "➡️Создать новый тег  \n");
        Keyboards.setButtonsTag(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equalsIgnoreCase("Теги");
        }
        return false;
    }
}
