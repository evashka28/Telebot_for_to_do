package bot.domen;

import java.io.Serializable;


public class Task implements Serializable {
    private long id;
    private long todoistId;
    private String description;
    private String content;
    private boolean favourite;

    public Task(long id, String description, String content, boolean favourite) {
        this.id = id;
        this.description = description;
        this.content = content;
        this.favourite = favourite;
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public long getTodoistId() {
        return todoistId;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public boolean getFavourite() {
        return favourite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public void setTodoistId(long todoistId) {
        this.todoistId = todoistId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"todoistId\":%d, \"description\":\"%s\", \"content\":\"%s\", \"favourite\":%s}",
                id, todoistId, description, content, favourite);
    }

}

