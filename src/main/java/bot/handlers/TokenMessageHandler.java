package bot.handlers;

import bot.Keyboards;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class TokenMessageHandler implements MessageHandler {
    public static boolean canHandle = false;

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String userName = update.getMessage().getFrom().getUserName();
        String synkToken = "0";
        String token = update.getMessage().getText();

        Map<String, String> body = Map.of(
                "id", userId,
                "name", userName,
                "token", token,
                "sync_token", synkToken
        );

        //delete user
        try {
            deleteUser(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // create user
        try {
            Map<String, String> result = postJSON(new URI("http://localhost:8081/user"), body);
            System.out.println("result = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // get user projects
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
            String todoId = 0 +"";
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


//        @GetMapping(value = "/projects")
//        public List<Project> allProjects(@RequestHeader long userId) throws IOException, ParseException {
//            return service.all(userId);
//        }

        // if tasks are not empty -> show OK message
        // if tasks are empty or got error -> show token invalid message

        canHandle = false;
        if (resultProjects == null) {
            message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Введенный токен не верный, попробуй еще раз");
            Keyboards.setButtons2(message);
            return message;
        }
        else {
            message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Ура\uD83C\uDF89\n" +
                            "Теперь ты можешь пользоваться ботом.\n" +
                            "Просто отправляй мне ссылки, настраивай свой режим чтения, а я буду подкидывать тебе все это в Todoist \uD83E\uDD13\n" +
                            "Все очень просто, ты мне ссылку, а я тебе сэкономленное время \uD83D\uDE0A\n" +
                            "Прямо сейчас можешь отправлять мне ссылки, я уже жду)\n" +
                            "\n" +
                            "Внизу расположены кнопки для навигации⬇️");
            Keyboards.setButtons(message);
            return message;

        }
    }

    @Override
    public boolean canHandle(Update update) {
        return canHandle;
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
            return objectMapper.readValue(body, new TypeReference<>() {});
        }

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

    public Map<String,String> postJSON(URI uri, Map<String,String> map)
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
        URI uri = new URI("http://localhost:8081/user/"+ userId);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .build();

        HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public Map<String,String> postNewProject(URI uri, Map<String,String> map, String userId)
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
}
