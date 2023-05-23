package test_task.data_models;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
public class BookOnlyName {
    private String name;

    public BookOnlyName(Map<String, Object> bookMap) {
        this.name = (String) bookMap.get("name");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if ((obj.getClass() != this.getClass())
                & (!this.getClass().isAssignableFrom(obj.getClass().getSuperclass()))
                & (!this.getClass().getSuperclass().isAssignableFrom(obj.getClass())))  {
            return false;
        }

        final BookOnlyName other = (BookOnlyName) obj;
        return (Objects.equals(this.name, other.name));
    }
}
