package bot.handlers;

import bot.Keyboards;
import bot.domen.Schedule;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class SelectionDayMessageHandler implements MessageHandler {
    enum Week { Воскресенье, Понедельник, Вторник, Среда, Четверг, Пятница, Суббота}
    List<Integer> daysOfWeek = new ArrayList<Integer>();
    String allDays="";

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();

        String day = update.getMessage().getText();

        if (allDays.contains(day)) { message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Ты уже выбрал данный день недели, выбери другой");}
        else {
            allDays=allDays+ day+" ";
            Week weekDay = Week.valueOf(day);
            System.out.println(weekDay.ordinal());
            daysOfWeek.add(weekDay.ordinal()+1);

            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Отлично!\n" +allDays + " выбран. Выбери еще дни или выбери расписание ⏰");
        }
        Keyboards.setButtons6(message);

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
