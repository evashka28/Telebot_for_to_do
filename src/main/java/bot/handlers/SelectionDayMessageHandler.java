package bot.handlers;

import bot.Keyboards;
import bot.domen.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            message.setText("Выбери расписания для тега в формате: sh 15:45:00 1,2,3 где 1 -вс, 2-пн и т п ");
        }
        else {
            String userId = update.getMessage().getFrom().getId() + "";
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String sch = update.getMessage().getText();
            String[] words = sch.split(" ");


            if (!words[0].equals("sh") || words.length != 3 || words[1].split(":").length !=3 || words[2].split(",").length >7 || words[1].split(":")[0].length() !=2
            || words[1].split(":")[1].length() !=2 || words[1].split(":")[2].length() !=2) {
                message.setText("Неправильное выражение, попробуй еще раз.");
            }
            //else if (words[1].split(":").length !=3){}
            else {
                message.setText("Вы ввели ...");


                Map<String, Object> result = null;
                try {

                    Map<String, Object> tagBody = Map.of(
                            "dateTime", words[1],
                            "daysOfWeek", words[2],
                            "tagId", tagId,
                            "userId", userId
                    );

                    result = postNewSch(new URI("http://localhost:8081/schedule/tag"), tagBody);
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


    public Map<String,Object> postNewSch(URI uri, Map<String,Object> map)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
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
        if((update.getMessage() != null && update.getMessage().getText() != null) ) {
            String text = update.getMessage().getText();
            return  text.contains("sh") ;}
        else if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getData().contains("/tagget");
        }
        return false;

    }
}
