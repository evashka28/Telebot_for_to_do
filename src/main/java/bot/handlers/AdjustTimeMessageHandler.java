package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdjustTimeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String time = update.getMessage().getText()+ "";
        System.out.println("бот месседж:"+time);
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Отлично!Каждый день в это время у тебя будут появляться задачи в Todoist\n" +
                        "\n" +
                        "Теперь можешь выбрать удобные дни \uD83D\uDC47️");
        //сменить клаву
        Keyboards.setButtons6(message);
        return message;
    }

//    public SendMessage getMessages(Update update) {
//        SendMessage message;
//        String time = update.getMessage().getFrom().getId() + "";
//        System.out.println(time);
//        return null;
//    }


    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("12:00");
    }
}
