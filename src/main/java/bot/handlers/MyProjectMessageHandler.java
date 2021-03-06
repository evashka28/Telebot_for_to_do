//package bot.handlers;
//
//import bot.keyboards.Keyboards;
//import bot.TextMessage;
//import bot.entities.Project;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.springframework.core.annotation.Order;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//import java.util.stream.Collectors;
//
////@Component
//@Order(value = 1)
//@Slf4j
//public class MyProjectMessageHandler implements MessageHandler {
//    @Override
//    public SendMessage getMessage(Update update) {
//        SendMessage message;
//        String userId = update.getMessage().getFrom().getId() + "";
//
//        // get user projects
//        List<Project> resultProjects = null;
//        try {
//            resultProjects = getProjects(userId);
//            log.info("result = " + resultProjects);
//        } catch (IOException | InterruptedException | URISyntaxException e) {
//            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
//        }
//        String returnProjectsName = resultProjects.stream()
//                .map(Project::getName)
//                .collect(Collectors.joining(", "));
//
//        message = new SendMessage();
//        message.setChatId(String.valueOf(update.getMessage().getChatId()));
//        message.setText(TextMessage.projectList + returnProjectsName);
//        Keyboards.setButtonsProject(message);
//        return message;
//
//    }
//
//    public List<Project> getProjects(String userId) throws IOException, InterruptedException, URISyntaxException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        URI uri = new URI("http://localhost:8081/projects");
//        HttpRequest request = HttpRequest.newBuilder(uri)
//                .header("userId", userId)
//                .build();
//
//        HttpResponse<String> response = HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 410) {
//            return null;
//        } else {
//            String body = response.body();
//            return objectMapper.readValue(body, new TypeReference<>() {
//            });
//        }
//    }
//
//    @Override
//    public boolean canHandle(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String text = update.getMessage().getText();
//            return text.equalsIgnoreCase(TextMessage.myProject);
//        }
//        return false;
//    }
//}

