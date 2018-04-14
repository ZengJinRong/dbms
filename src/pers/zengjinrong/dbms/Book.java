package pers.zengjinrong.dbms;

import javafx.beans.property.SimpleStringProperty;

/**
 * 书籍类Bean
 *
 * @author ZengJinRong
 * @version 1.0
 */
public class Book {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty author;

    public Book(String id, String name, String author) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }
}
