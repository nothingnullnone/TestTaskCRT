package test_task.data_models;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
public class BookWithId extends Book {
    private int id;

    public BookWithId(Map<String, Object> bookMap) {
        this.author = (String) bookMap.get("author");
        this.isElectronicBook = (boolean) bookMap.get("isElectronicBook");
        this.name = (String) bookMap.get("name");
        this.year = ((Double) bookMap.get("year")).intValue();
        this.id = ((Double) bookMap.get("id")).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            if (this.getClass().getSuperclass().isAssignableFrom(obj.getClass())
                    | (!this.getClass().isAssignableFrom(obj.getClass().getSuperclass()))) {
                return super.equals(obj);
            }
            else return false;
        }

        final BookWithId other = (BookWithId) obj;
        return (Objects.equals(this.name, other.name)) &&
                (Objects.equals(this.author, other.author)) &&
                (Objects.equals(this.year, other.year)) &&
                (Objects.equals(this.id, other.id)) &&
                (Objects.equals(this.isElectronicBook, other.isElectronicBook));
    }

    @Override
    public String toString() {
        return "Book(" +
                "author="+ this.author + ", " +
                "isElectronicBook="+ this.isElectronicBook + ", " +
                "name="+ this.name + ", " +
                "year="+ this.year +
                ")";
    }
}
