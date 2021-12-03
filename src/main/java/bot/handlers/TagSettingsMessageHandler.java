package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TagSettingsMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Добавляя тег к задаче, ты условно делишь свои задачи на категории.\n\n" +
                        "Благодаря этому можно быстро получать список задач или конкретную задачу\n" +
                        "Это удобно, я проверял\uD83D\uDE09\n\n" +
                        "Ты можешь\n" +
                        "➡️Посмотреть все свои теги \n" +
                        "➡️Создать новый тег  \n" +
                        "➡️Удалить тег");
        Keyboards.setButtonsTag(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Теги");
    }
}