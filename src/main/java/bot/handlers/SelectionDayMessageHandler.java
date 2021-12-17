package bot.handlers;

import bot.Keyboards;
import bot.domen.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 1)
public class SelectionDayMessageHandler implements MessageHandler {
    enum Week { Воскресенье, Понедельник, Вторник, Среда, Четверг, Пятница, Суббота}
    Schedule schedule = new Schedule();
    List<Integer> daysOfWeek = new ArrayList<Integer>();
    String tagId="";

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        if (update.hasCallbackQuery()){
            tagId = update.getCallbackQuery().getData().replace("/tagget","") ;
            System.out.println(tagId);
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            message.setText("Выбери расписания для тега в формате:\n" +
                    "sh 15:45 1,2,3 \n" +
                    "где 1 -вс, 2-пн и так далее");
        }
        else {
            String userId = update.getMessage().getFrom().getId() + "";
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String sch = update.getMessage().getText();
            String[] words = sch.split(" ");


            if (!words[0].equals("sh") || words.length != 3 ) {
                message.setText("Неправильное выражение, попробуй еще раз.");
            }
            else if (sameday(words)|| wrongday(words) || words[2].split(",").length >7){message.setText("Неправильно введены дни недели, попробуй еще раз.");}
            else if (timeiswrong(words)|| words[1].split(":").length !=2  || words[1].split(":")[0].length() !=2
                    || words[1].split(":")[1].length() !=2  ) {message.setText("Неправильно введено время, попробуй еще раз.");}
            else {
                message.setText("Спасибо, расписание сохранено" );
                String dateTime= words[1]+":00";
                //message.setText("Спасибо, задача с тегом %s будет выведена в %s в %s", tagId, words[1], weeks[Integer.parseInt(words[2].split(",")[1])]  );


                Map<String, Object> result = null;
                try {

                    Map<String, Object> tagBody = Map.of(
                            "dateTime", dateTime,
                            "daysOfWeek", words[2],
                            "id", "hi"
                    );

                    result = postNewSch(new URI("http://localhost:8081/schedule/tag"), tagBody, tagId, userId);
                    System.out.println(tagBody);
                    System.out.println("resultTask = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }


        Keyboards.setButtons6(message);
        return message;
    }
    public boolean sameday(String[] words) {
        for (int i = 0; i < words[2].split(",").length - 1; i++) {
            for (int j = i + 1; j < words[2].split(",").length; j++) {
                if (words[2].split(",")[i].equals(words[2].split(",")[j])) {
                    return true;

                }
            }
        }
        return false;
    }

    public boolean wrongday(String[] words) {
        for (int i = 0; i < words[2].split(",").length; i++) {
            if (Integer.parseInt(words[2].split(",")[i])>7){
                return true;
            }
        }
        return false;
    }

    public boolean timeiswrong(String[] words) {
        if ((words[1].split(":")[0].charAt(0)!='0'&& Integer.parseInt(words[1].split(":")[0])>23)||
                (words[1].split(":")[1].charAt(0)!='0'&& Integer.parseInt(words[1].split(":")[1])>59  )){
            return true;
        }
        else  {return false;}
    }




    public Map<String,Object> postNewSch(URI uri, Map<String,Object> map, String tagId, String userId)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .header("tagId", tagId)
                .header("userId", userId)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resultBody = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();

        return (Map<String, Object>) objectMapper.readValue(resultBody, Map.class);
    }

    @Override
    public boolean canHandle(Update update) {
        //if(update.hasCallbackQuery())
       //     return update.getCallbackQuery().getData().contains("/tagget");
        if((update.hasMessage() && update.getMessage().hasText()) ) {
            String text = update.getMessage().getText();
            return  text.contains("sh") ;}
        else if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getData().contains("/tagget");
        }
        return false;

    }
}
