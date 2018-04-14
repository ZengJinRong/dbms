package pers.zengjinrong.dbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 用于实现数据库相关操作
 *
 * @author ZengJinRong
 * @version 1.0
 */
public class DatabaseManager {
    private static String driver;
    private static String databaseURL;
    private static String user;
    private static String password;

    /**
     * 初始化
     */
    public static void init() {
        try {
            // 加载配置文件
            InputStream inputStream = Main.class.getClassLoader()
                    .getResourceAsStream("database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            driver = properties.getProperty("jdbcDriver");
            databaseURL = properties.getProperty("databaseURL");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 注册 JDBC 驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从数据库查询所有书籍信息
     *
     * @return 查询到的书籍信息
     */
    public static ObservableList<Book> queryAll() {
        final ObservableList<Book> books = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 打开链接
            connection = DriverManager.getConnection(databaseURL, user, password);

            // 执行查询语句
            String sql = "SELECT * FROM book";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //处理查询结果
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                books.add(new Book(id, name, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (resultSet != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return books;
        }
    }

    /**
     * 增加书籍信息到数据库
     *
     * @param book 要增加到数据库的书籍对象
     */
    public static void add(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 打开链接
            connection = DriverManager.getConnection(databaseURL, user, password);

            // 执行插入语句
            String sql = "INSERT INTO book VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getId());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (resultSet != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从数据库删除指定的书籍信息
     *
     * @param id 要删除信息的书籍编号
     */
    public static void delete(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 打开链接
            connection = DriverManager.getConnection(databaseURL, user, password);

            // 执行插入语句
            String sql = "DELETE FROM book WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (resultSet != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改数据库中指定的书籍信息
     *
     * @param book 要修改信息的书籍对象
     */
    public static void update(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 打开链接
            connection = DriverManager.getConnection(databaseURL, user, password);

            // 执行插入语句
            String sql = "UPDATE book SET name=?,author=? WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setString(3,book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (resultSet != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
