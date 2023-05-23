package test_task.data_models;

import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
public class Book {
    protected String author;
    protected boolean isElectronicBook;
    protected String name;
    protected int year;
    public Book(){
    }

    public Book(BookOnlyName basicBook) {
        this.name = basicBook.getName();
        this.author = "";
        this.year = 0;
        this.isElectronicBook = false;
    }

    public Book(Map<String, Object> bookMap) {
        this.author = (String) bookMap.get("author");
        this.isElectronicBook = (boolean) bookMap.get("isElectronicBook");
        this.name = (String) bookMap.get("name");
        this.year = ((Double) bookMap.get("year")).intValue();
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

        final Book other = (Book) obj;
        return (Objects.equals(this.name, other.name)) &&
                (Objects.equals(this.author, other.author)) &&
                (Objects.equals(this.year, other.year)) &&
                (Objects.equals(this.isElectronicBook, other.isElectronicBook));
    }
}
