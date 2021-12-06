package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SelectingTimeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String time = update.getMessage().getText()+ "";
        System.out.println("бот месседж:"+time);
        message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Введите время в которое вы хотите получать уведомления в формате: часы:минуты\n" +
                        "\n" +
                        "Пример: 18:45 \uD83D\uDC47️");
        //сменить клаву
        Keyboards.setButtons6(message);
        return message;
    }



    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Время");
        }
        return false;
    }
}
