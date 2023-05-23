package test_task.data_models;

import lombok.Data;

@Data
public class BookWithIdNoDatatypeCheck {
    private Object author;
    private Object isElectronicBook;
    private Object name;
    private Object year;
    private Object id;
    public BookWithIdNoDatatypeCheck(){
    }
}
