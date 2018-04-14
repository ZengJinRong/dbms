package pers.zengjinrong.dbms;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 基于JDBC的图书信息管理系统
 *
 * @author ZengJinRong
 * @version 1.0
 */
public class Main extends Application {
    private final TextField idText = new TextField();
    private final TextField nameText = new TextField();
    private final TextField authorText = new TextField();
    private final TextField deleteText = new TextField();
    private final TableView<Book> bookTable = new TableView<>();

    /**
     * 主函数入口
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Application启动入口
     */
    @Override
    public void start(Stage stage) {
        DatabaseManager.init();

        Scene scene = new Scene(new Group());
        stage.setTitle("图书管理系统");

        //初始化表格
        addTableColumn("id", "书号");
        addTableColumn("name", "书名");
        addTableColumn("author", "作者");
        bookTable.setItems(DatabaseManager.queryAll());

        //初始化数据增加/修改栏
        idText.setPromptText("书号");
        nameText.setPromptText("书名");
        authorText.setPromptText("作者");
        final Button addButton = new Button("增加/修改");
        addButton.setOnAction(new AddOrUpdateAction());
        final HBox addOrUpdateBox = new HBox();
        addOrUpdateBox.setSpacing(5);
        addOrUpdateBox.setPadding(new Insets(10, 10, 10, 10));
        addOrUpdateBox.getChildren().addAll(idText, nameText, authorText, addButton);

        //初始化数据删除栏
        deleteText.setPromptText("书号");
        final Button deleteButton = new Button("删除");
        deleteButton.setOnAction(new DeleteAction());
        final HBox deleteBox = new HBox();
        deleteBox.setSpacing(5);
        deleteBox.setPadding(new Insets(10, 10, 10, 10));
        deleteBox.getChildren().addAll(deleteText, deleteButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(bookTable, addOrUpdateBox, deleteBox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * 增加一列表格
     */
    private void addTableColumn(String itemName, String columnName) {
        TableColumn column = new TableColumn(columnName);
        column.setMinWidth(100);
        column.setCellValueFactory(
                new PropertyValueFactory<>(itemName));
        bookTable.getColumns().add(column);
    }

    /**
     * 增加/修改键点击事件响应
     */
    class AddOrUpdateAction implements EventHandler {
        @Override
        public void handle(Event event) {
            String id = idText.getText();
            String name = nameText.getText();
            String author = authorText.getText();
            if (!id.isEmpty() && !name.isEmpty() && !author.isEmpty()) {
                Book book = new Book(id, name, author);
                DatabaseManager.add(book);
                DatabaseManager.update(book);
                bookTable.setItems(DatabaseManager.queryAll());
                //清空输入框
                idText.clear();
                nameText.clear();
                authorText.clear();
            }
        }
    }

    /**
     * 删除键点击事件响应
     */
    class DeleteAction implements EventHandler {
        @Override
        public void handle(Event event) {
            if (!deleteText.getText().isEmpty()) {
                DatabaseManager.delete(deleteText.getText());
                bookTable.setItems(DatabaseManager.queryAll());
                deleteText.clear();
            }
        }
    }
}
