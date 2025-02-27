package fileManager;

import model.Session;
import model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final static String PATH_USERS = "C:\\ITS\\JAVA-Projects\\quarkus-login-demo-master\\src\\main\\resources\\templates\\users.csv";

    public List<Session> getSessionsFromFile() {
        List<Session> sessions = new ArrayList<>();
        try (Reader reader = new FileReader("sessions.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())) {

            for (CSVRecord record : csvParser)
            {
                int idUser = Integer.parseInt(record.get("ID_UTENTE"));
                String idSession = record.get("ID_SESSION");

                Session session = new Session(idUser, idSession);
                sessions.add(session);
            }
        } catch (IOException e) {
        }
        return sessions;
    }

    public void saveSession(Session session) {

        try (Writer writer = new FileWriter("sessions.csv", true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withHeader("ID_UTENTE", "ID_SESSION"))) {

                csvPrinter.printRecord(session.getIdUtente(), session.getIdSession());
                csvPrinter.flush();
                System.out.println("Updated file");

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public List<Session> getSessionById(String id) {
        List<Session> sessionsById = new ArrayList<>();

        List<Session> sessions = getSessionsFromFile();

        for (Session session : sessions) {
            if (session.getIdSession().equals(id)) {
                sessionsById.add(session);
            }
        }

        return sessionsById;
    }
}
