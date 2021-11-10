package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdjustModeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Для того, чтобы настроить режим необходимо выбрать время и дни ⬇️");
        Keyboards.setButtons5(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Настроить режим");
    }
}
