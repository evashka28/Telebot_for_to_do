package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdjustTimeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Выбирай один из трех временных промежутков:️");
        Keyboards.setButtons7(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Настроить время");
    }
}
