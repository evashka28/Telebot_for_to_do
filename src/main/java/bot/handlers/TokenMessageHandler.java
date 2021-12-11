package bot.handlers;

import bot.Keyboards;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TokenMessageHandler implements MessageHandler {
    public static boolean canHandle = false;

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
        System.out.println("delete user");
        try {
            deleteUser(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
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

        canHandle = true;
        if (resultProjects == null) {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Введенный токен не верный, попробуй еще раз");
            Keyboards.setButtons2(message);
            return message;
        } else {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Отлично\uD83E\uDD17 пользователь с таким токеном существует!\n" +
                    "\n" +
                    "В твоём профиле Todoist я специально создал проект с названием fromToDoBot.\n" +
                    "Именно туда я буду добавлять все ссылки, которые ты мне пришлёшь \uD83D\uDE09\n" +
                    "\n" +
                    "Немного о моих кнопках:\n" +
                    "\n" +
                    "➡️Теги\n" +
                    "\n" +
                    "Рекомендую тебе сразу создать пару тегов. Когда ты будешь отправлять мне ссылки, будет полезно их разносить в разные категории\n" +
                    "например: \n" +
                    "скинул видос ➡️пометил #смотреть \n" +
                    "скинул статью ➡️пометил #читать \n" +
                    "\n" +
                    "Таким образом, ты быстро найдёшь необходимую ссылку, если захочешь вновь ее посмотреть. \n" +
                    "Так же можно: посмотреть какие теги уже созданы или удалить если уже не актуальны \n" +
                    "\n" +
                    "➡️Режим чтения \n" +
                    "\n" +
                    "Очень удобная штука\uD83D\uDE42\n" +
                    "Задаёшь расписание — получаешь напоминание. \n" +
                    "Если предварительно ко всем ссылкам добавлять теги, то можно настроить напоминание ссылок с тегом #смотреть в пятницу вечером, а с тегом #читать по понедельникам и вторникам в обед \uD83D\uDE09\n" +
                    "Тогда я вечерком в пятницу накидаю тебе ссылок с видосами\uD83C\uDFA5\uD83C\uDFA5\uD83C\uDFA5 \n" +
                    "А получать статейки ты будешь в понедельник и вторник \uD83D\uDCD6\uD83D\uDCD6\uD83D\uDCD6\n" +
                    "Настраиваешь расписание так, как тебе удобно(время, дни)\n" +
                    "И жизнь хоРРРоша и жить хоРРРошо \uD83D\uDE0A\n" +
                    "\n" +
                    "➡️Что умеет бот?\n" +
                    "\n" +
                    "Тут я рассказываю о своих возможностях \uD83E\uDD16\n" +
                    "Проект перспективный, время от времени разработчики меня улучшают \uD83E\uDDD1\u200D\uD83D\uDCBB\uD83D\uDC69\u200D\uD83D\uDCBB\uD83D\uDC69\u200D\uD83D\uDCBB");
            Keyboards.setButtons(message);
            return message;

        }
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.getMessage() != null && update.getMessage().getText() != null) {
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

    public void deleteUser(String userId) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("http://localhost:8081/user/" + userId);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .build();

        HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

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
