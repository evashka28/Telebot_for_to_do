package bot.handlers;

import bot.Keyboards;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;

@Component
@Order(value = 1)
public class TokenMessageHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String userName = update.getMessage().getFrom().getUserName() + "";
        String synkToken = "0";
        String token = update.getMessage().getText() + "";
        String zone = "Europe/Moscow";


        Map<String, String> body = Map.of(
                "id", userId,
                "name", userName,
                "token", token,
                "sync_token", synkToken,
                "zone", zone
        );


        //delete user
//        System.out.println("delete user");
//        try {
//            deleteUser(userId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        // create user
        System.out.println("create user");
        try {
            Map<String, String> result = postJSON(new URI("http://localhost:8081/user"), body);
            System.out.println("result = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // get user projects
        System.out.println("get user's projects");
        List<Project> resultProjects = null;
        try {
            resultProjects = getProjects(userId);
            System.out.println("result = " + resultProjects);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // create new project ToDoBot
        // надо проверить есть ли проект fromToDoBot
        // если нет, то создать, а если есть, то складывать туда
        try {
            String favorite = "false";
            String name = "fromToDoBot";
            String todoId = 0 + "";
            Map<String, String> projectBody = Map.of(
                    "favorite", favorite,
                    "id", userId,
                    "name", name,
                    "todoistId", todoId
            );

            Map<String, String> result = postNewProject(new URI("http://localhost:8081/project"), projectBody, userId);
            System.out.println("resultproject = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // if tasks are not empty -> show OK message
        // if tasks are empty or got error -> show token invalid message

        if (resultProjects == null) {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Введенный токен не верный, попробуй еще раз");
            Keyboards.setButtons2(message);
            return message;
        } else {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Отлично \uD83E\uDD17 Пользователь с таким токеном существует!\n" +
                    "\n" +
                    "В твоём профиле Todoist я специально создал проект с названием fromToDoBot.\n" +
                    "Именно туда я буду добавлять все ссылки, которые ты мне пришлёшь \uD83D\uDE09\n" +
                    "\n" +
                    "Немного о моих кнопках:\n" +
                    "\uD83D\uDCCDРасписание \n" +
                    "Ты можешь создавать расписание, а я буду отправлять тебе ссылку в настроенное время\n" +
                    "\uD83D\uDCCDДай задачу\n" +
                    "Дам одну любую ссылку из имеющихся\n" +
                    "\uD83D\uDCCDТеги\n" +
                    "можно создать теги и бытро находить задачи\n" +
                    "\uD83D\uDCCDЗадачи\n" +
                    "Дам все ссылки, которые ты мне присылал\n" +
                    "\uD83D\uDCCDДай Задачу по тегу\n" +
                    "Отыщу любую задачу по выбранному тегу\n" +
                    "\uD83D\uDCCDЗадачи по тегу\n" +
                    "Верну все задачи по выбранному тегу\n" +
                    "\uD83D\uDCCDЗадать часовой пояс\n" +
                    "Необходимо настроить для корректной работы расписания\n" +
                    "\uD83D\uDCCDКак работает бот?\n" +
                    "Краткая информация обо мне");
            Keyboards.setButtons(message);
            return message;

        }
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            boolean can = update.getMessage().getText().length() == 40;
            return can;
        }
        return false;
    }

    public List<Project> getProjects(String userId) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = new URI("http://localhost:8081/projects");
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        }

    }

    public Map<String, String> postJSON(URI uri, Map<String, String> map)
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

        return (Map<String, String>) objectMapper.readValue(resultBody, Map.class);
    }

//    public void deleteUser(String userId) throws IOException, InterruptedException, URISyntaxException {
//        URI uri = new URI("http://localhost:8081/user/" + userId);
//        HttpRequest request = HttpRequest.newBuilder(uri)
//                .DELETE()
//                .build();
//
//        HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//    }

    public Map<String, String> postNewProject(URI uri, Map<String, String> map, String userId)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .header("userId", userId)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resultBody = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();

        return (Map<String, String>) objectMapper.readValue(resultBody, Map.class);
    }

    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        /**
         * Parses the given JSON string into a Map.
         */
        Map<String, String> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
