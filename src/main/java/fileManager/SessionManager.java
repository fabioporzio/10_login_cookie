package fileManager;

import model.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final static String PATH_USERS = "C:\\ITS\\JAVA-Projects\\quarkus-login-demo-master\\src\\main\\resources\\templates\\users.csv";

    public List<Session> getUsers() {
        List<Session> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_USERS))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] valori = line.split(",");
                if (valori.length >= 3) { // Evita errori se ci sono righe incomplete
                    String idutente = valori[0];
                    String idSession = valori[1];
                    users.add(new Session(idutente, idSession));
                }
            }
        } catch (IOException e) {
            return null;
        }

        return users;
    }

    public void writeSession(String content) {

        //controllo doppioni

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_USERS, true))) {
            writer.write(content + "\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSessionById(String id) {

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_USERS))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] valori = line.split(",");
                if (valori.length >= 3 && valori[0].equals(id)) { // Evita errori se ci sono righe incomplete
                    String idUtente = valori[0];
                    String idSession = valori[1];
                    return new Session(idUtente, idSession);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }
}
