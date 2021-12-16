package bot;

import bot.domen.Tag;
import bot.domen.TagRequest;
import bot.domen.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.time.LocalTime;

@Component
public class BackendConnector {

    public List<Tag> getTags(String userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI("http://localhost:8081/tags");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Task> getTasks(String userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI("http://localhost:8081/tasks");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Task> getTasksByTag(String userId, long tagId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/tasks/tag/%d", tagId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Task getTask(String userId, long taskId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Task getOneTask(String userId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String addTagToTask(String userId, long taskId, long tagId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d/tag/%d", taskId, tagId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String deleteTask(String userId, long taskId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.toString();
            return body;
        }
    }

    public String completeTask(String userId, long taskId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d/complete", taskId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.toString();
            return body;
        }
    }

    public String deleteTag(String userId, long tagId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/tag/%d", tagId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.toString();
            return body;
        }
    }


    public List<TagRequest> getSchs(String tagId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        URI uri = null;
        try {
            uri = new URI("http://localhost:8081/schedules");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("tagId", tagId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public TagRequest getSh(String userId, long taskId) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/task/%d", taskId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            try {
                return objectMapper.readValue(body, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String setTimezone(String userId, String timezone) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/user/%s/timezone", userId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(String.format(timezone)))
                .header("userId", userId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            return body;
        }
    }

    public String deleteSch(String tagId, String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = null;
        try {
            uri = new URI(String.format("http://localhost:8081/schedule/%s", id));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpRequest request = HttpRequest.newBuilder(uri)
                .DELETE()
                .header("tagId", tagId)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.toString();
            return body;
        }
    }
}
