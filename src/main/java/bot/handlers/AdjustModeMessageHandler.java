package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdjustModeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Для настройки удобного режима необходимо задать подходящее время ⏰ и день(дни) недели \n \nИменно в таком режиме будут  появляться задачи в Todoist \nДни и время ты выбираешь сам ⬇️ ️");
        Keyboards.setButtons5(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Режим чтения");
        }
        return false;
    }
}
