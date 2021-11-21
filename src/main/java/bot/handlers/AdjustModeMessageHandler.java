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
                .setText("Для настройки удобного режима необходимо задать подходящее время в формате чч:мм ⏰ \nНапример: 12:30 \nИменно в это время у тебя будут появляться задачи в Todoist \n Удобные дни ты тоже выбираешь сам ⬇️ ️");
        Keyboards.setButtons5(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Настроить режим");
    }
}
