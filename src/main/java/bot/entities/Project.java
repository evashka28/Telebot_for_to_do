package bot.entities;

import java.io.Serializable;

public class Project implements Serializable {

    private long id;
    private long todoistId;
    private boolean favourite;
    private String name;

    public Project() {
    }

    public Project(long id, long todoistId, boolean favourite) {
        this.id = id;
        this.todoistId = todoistId;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public long getTodoistId() {
        return todoistId;
    }

    public String getName() {
        return name;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTodoistId(long todoistId) {
        this.todoistId = todoistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", todoistId=" + todoistId +
                ", favourite=" + favourite +
                ", name='" + name + '\'' +
                '}';
    }
}