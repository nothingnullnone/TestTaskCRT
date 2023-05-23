package test_task.data_models;

import lombok.Data;

@Data
public class BookNoDatatypeCheck {
    private Object author;
    private Object isElectronicBook;
    private Object name;
    private Object year;
    public BookNoDatatypeCheck(Book book) {
        this.name = book.getName();
        this.author = book.getAuthor();
        this.year = book.getYear();
        this.isElectronicBook = book.isElectronicBook();
    }
}
