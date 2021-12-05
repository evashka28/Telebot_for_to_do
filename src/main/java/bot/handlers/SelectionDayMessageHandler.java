package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SelectionDayMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Отлично!\n" +
                        "День выбран. Не забудь выбрать Время ⏰");
        Keyboards.setButtons5(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.getMessage() != null && update.getMessage().getText() != null) {
            String text = update.getMessage().getText();
            return text.equals("Каждый день \uD83D\uDE48") || text.equals("Понедельник") || text.equals("Вторник") || text.equals("Среда") ||
                    text.equals("Четверг") || text.equals("Пятница") || text.equals("Суббота") || text.equals("Воскресенье");

        }
        return false;
    }
}
