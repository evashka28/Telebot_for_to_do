package bot.connectors;

import bot.entities.Project;
import bot.entities.Tag;
import bot.entities.TagRequest;
import bot.entities.Task;
import bot.exceptions.BackendConnectorException;
import bot.handlers.CreateTagsMessageHandler;
import bot.handlers.TokenMessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BackendConnector {

    public List<Tag> getTags(String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI("http://localhost:8081/tags");
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public List<Task> getTasks(String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI("http://localhost:8081/tasks");
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public List<Task> getTasksByTag(String userId, long tagId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/tasks/tag/%d", tagId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public Task getTask(String userId, long taskId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public Task getTaskByTag(String tagId, String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI("http://localhost:8081/task/tag/" + tagId);
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public Task getOneTask(String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI("http://localhost:8081/task");
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        if ((response != null ? response.statusCode() : 0) == 410) {
            return null;
        } else {
            try {
                String body = response.body();
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException | NullPointerException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public String addTagToTask(String userId, long taskId, long tagId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d/tag/%d", taskId, tagId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public String deleteTask(String userId, long taskId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.toString();
        }
    }

    public String completeTask(String userId, long taskId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d/complete", taskId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.toString();
        }
    }

    public String deleteTag(String userId, long tagId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/tag/%d", tagId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.toString();
        }
    }


    public List<TagRequest> getSchedules(String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        URI uri;
        try {
            uri = new URI("http://localhost:8081/schedules");
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                throw new BackendConnectorException();
            }
        }
    }

    public TagRequest getSchedule(String userId, long taskId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                return null;
            }
        }
    }

    public String setTimezone(String userId, String timezone) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/user/%s/timezone", userId));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(timezone))
                .header("userId", userId)
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.body();
        }
    }

    public String deleteSchedule(String id) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI(String.format("http://localhost:8081/schedule/%s", id));
        } catch (URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.toString();
        }
    }

    public Map<String, Object> createTag(String userId, String content, CreateTagsMessageHandler createTagsMessageHandler) throws IOException, InterruptedException, URISyntaxException {
        Map<String, Object> result;
        String id = 0 + "";
        Map<String, Object> tagBody = Map.of(
                "id", id,
                "name", content
        );

        result = createTagsMessageHandler.postNewTag(new URI("http://localhost:8081/tag"), tagBody, userId);
        return result;
    }

    public Map<String, Object> postNewTask(URI uri, Map<String, Object> map, String userId)
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

        return (Map<String, Object>) objectMapper.readValue(resultBody, Map.class);
    }

    public Map<String, Object> postNewSchedule(URI uri, Map<String, Object> map, String tagId, String userId)
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

    public Map<String, String> createProject(String userId, TokenMessageHandler tokenMessageHandler) throws IOException, InterruptedException, URISyntaxException {
        String favorite = "false";
        String name = "fromToDoBot";
        String todoId = 0 + "";
        Map<String, String> projectBody = Map.of(
                "favorite", favorite,
                "id", userId,
                "name", name,
                "todoistId", todoId
        );

        Map<String, String> resultProject = tokenMessageHandler.backendConnector.postNewProject(new URI("http://localhost:8081/project"), projectBody, userId);
        return resultProject;
    }

    public Map<String, String> createUser(String userId, String userName, String synkToken, String token, String zone, TokenMessageHandler tokenMessageHandler) throws IOException, InterruptedException, URISyntaxException {
        Map<String, String> body = Map.of(
                "id", userId,
                "name", userName,
                "token", token,
                "sync_token", synkToken,
                "zone", zone
        );
        Map<String, String> result = tokenMessageHandler.backendConnector.postJSON(new URI("http://localhost:8081/user"), body);
        return result;
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
}
