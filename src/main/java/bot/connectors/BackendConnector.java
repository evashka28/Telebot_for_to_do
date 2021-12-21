package bot.connectors;

import bot.entities.Tag;
import bot.entities.TagRequest;
import bot.entities.Task;
import bot.exceptions.BackendConnectorException;
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

@Component
@Slf4j
public class BackendConnector {

    public List<Tag> getTags(String userId) throws BackendConnectorException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri;
        try {
            uri = new URI("http://localhost:8081/tags");
        } catch (URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
                log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            throw new BackendConnectorException();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            return response.toString();
        }
    }
}
