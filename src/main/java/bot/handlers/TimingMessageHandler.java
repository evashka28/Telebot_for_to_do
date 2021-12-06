package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

//его удалить
public class TimingMessageHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("В таком режиме я буду заводить тебе задачи в Todoist.\n Если решишь изменить его, нажимай Сбросить режим");
        Keyboards.setButtons5(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            String text = update.getMessage().getText();
            return text.equals("Утро 8:00-12:00") || text.equals("День 12:00-18:00") || text.equals("Вечер 18:00-23:00");
        }
        return false;
    }
}
