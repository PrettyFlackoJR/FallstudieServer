package dhbw.server.controller;

import dhbw.server.domain.Nutzer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Properties;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @PostMapping
    void onRegister(@RequestBody Nutzer nutzer) throws SQLException {
        String url = String.format("jdbc:postgresql://vorlesung.postgres.database.azure.com:5432/postgres?currentSchema=%s", "vorlesungsplaner");
        Properties props = new Properties();
        props.setProperty("user", "admindhbw@vorlesung");
        props.setProperty("password", "tomate12!");
        Connection conn = DriverManager.getConnection(url, props);

        registerUser(conn, nutzer);

        conn.close();
    }

    public void registerUser(Connection conn, Nutzer nutzer) throws SQLException {
        PreparedStatement check = conn.prepareStatement("SELECT nut_email FROM nutzer WHERE nut_email = ?");
        check.setString(1, nutzer.email);
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            PreparedStatement preparedStmt = conn.prepareStatement("INSERT INTO nutzer(nut_vorname, nut_nachname, nut_email, nut_anrede," +
                    " nut_titel, nut_istadmin, nut_passwort) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStmt.setString(1, nutzer.vorname);
            preparedStmt.setString(2, nutzer.nachname);
            preparedStmt.setString(3, nutzer.email);
            preparedStmt.setString(4, nutzer.anrede);
            preparedStmt.setString(5, nutzer.titel);
            preparedStmt.setBoolean(6, nutzer.istAdmin);
            preparedStmt.setString(7, nutzer.passwort);

            preparedStmt.executeUpdate();
            System.out.println("Succesfully added user " + nutzer.anrede + " " + nutzer.nachname);
            preparedStmt.close();
        }
        else {
            System.out.println("This email is already registered.");
        }
        check.close();
        rs.close();
    }

}
