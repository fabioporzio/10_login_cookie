package fileManager;

import jakarta.enterprise.context.ApplicationScoped;
import model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserManager {
    public List<User> getUsersFromFile()  {
        List<User> utentiRegistrati = new ArrayList<>();
        try (Reader reader = new FileReader("users.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())) {

            for (CSVRecord record : csvParser)
            {
                int id = Integer.parseInt(record.get("ID"));
                String email = record.get("EMAIL");
                String password = record.get("PASSWORD");

                User utenteRegistrato = new User(id, email, password);
                utentiRegistrati.add(utenteRegistrato);
            }
        } catch (IOException e) {
        }
        return utentiRegistrati;
    }

    public void saveUsers(List<User> utenti) {
        try (Writer writer = new FileWriter("users.csv");
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withHeader("ID", "EMAIL", "PASSWORD"))) {
            for (User utente : utenti) {
                csvPrinter.printRecord(utente.getId(), utente.getEmail(), utente.getPassword());
            }
            csvPrinter.flush();
            System.out.println("Updated file");

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public boolean checkUserExistence(List<User> utentiRegistrati, User utente) {
        for (User utenteRegistrato : utentiRegistrati) {
            if (utenteRegistrato.getEmail().equals(utente.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public User getUserByCredentials(String email, String password) {
        List<User> utentiRegistrati = getUsersFromFile();
        for (User utenteRegistrato : utentiRegistrati) {
            if (email.equals(utenteRegistrato.getEmail()) && password.equals(utenteRegistrato.getPassword())) {
                return utenteRegistrato;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        List<User> utentiRegistrati = getUsersFromFile();
        for (User utenteRegistrato : utentiRegistrati) {
            if (id == utenteRegistrato.getId()) {
                return utenteRegistrato;
            }
        }
        return null;
    }
}
