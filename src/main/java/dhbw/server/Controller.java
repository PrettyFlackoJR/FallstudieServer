package dhbw.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.Properties;

@RestController
@RequestMapping("/jonasstinkt")
public class Controller {

    @GetMapping
    void methode() throws SQLException {
        System.out.println("Hello, World!");
        String url = String.format("jdbc:postgresql://vorlesung.postgres.database.azure.com:5432/postgres?currentSchema=%s", "vorlesungsplaner");
        Properties props = new Properties();
        props.setProperty("user", "admindhbw@vorlesung");
        // password is passed in as first argument through command line
        props.setProperty("password", "tomate12!");
        Connection conn = DriverManager.getConnection(url, props);

        // %s = print string from argument, %n = new line
        System.out.printf("Connection established: %s%n", conn.getMetaData().getURL());

        // create schema and insert test data
        /*createSchema(conn);
        insertData(conn); */
        queryData(conn);

        // free the connection
        conn.close();
    }

    public void queryData(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM kurs");
        System.out.println("Reading all entries...");
        while (rs.next()) {
            final int id = rs.getInt("kurs_id");
            final String name = rs.getString("kurs_name");
            System.out.printf("Entry: id=%d, name=%s%n", id, name);
        }

        stmt.close();
    }


}
