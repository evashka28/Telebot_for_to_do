package bot.handlers;

import bot.Keyboards;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class ReadingModeHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Здесь ты можешь работать с расписание по которому тебе приходят задачи.\n\n" +
                        "Ты можешь\n" +
                        "➡️Посмотреть все свои расписания по тегам \n" +
                        "➡️Создать новое расписание для тега  \n");
        Keyboards.setButtons6(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equals("Расписание");
        }
        return false;
    }
}
