package fileManager;

import jakarta.enterprise.context.ApplicationScoped;
import model.Session;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SessionManager {
    private final static String PATH_USERS = "C:\\ITS\\JAVA-Projects\\quarkus-login-demo-master\\src\\main\\resources\\templates\\users.csv";

    public List<Session> getSessionsFromFile() {
        List<Session> sessions = new ArrayList<>();
        try (Reader reader = new FileReader("sessions.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())) {

            for (CSVRecord record : csvParser)
            {
                String id = record.get("ID_UTENTE");
                String idSession = record.get("ID_SESSION");

                Session session = new Session(id, idSession);
                sessions.add(session);
            }
        } catch (IOException e) {
        }
        return sessions;
    }

    public void appendSession(Session session) {

        try (Writer writer = new FileWriter("sessions.csv", true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL)) {

                csvPrinter.printRecord(session.getIdUtente(), session.getIdSession());
                csvPrinter.flush();
                System.out.println("Updated file");

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public List<Session> filterSessions(Session session) {
        List<Session> sessions = new ArrayList<>();
        String id = session.getIdSession();
        System.out.println("ID_SESSION nel filtro:" + id);
        try (Reader reader = new FileReader("sessions.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader())) {

            for (CSVRecord record : csvParser)
            {
                if (!id.equals(record.get("ID_SESSION"))) {
                    String idUtente = record.get("ID_UTENTE");
                    String idSession = record.get("ID_SESSION");

                    Session sessionToAdd = new Session(idUtente, idSession);
                    sessions.add(sessionToAdd);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return sessions;
    }

    public void overwriteSession(List<Session> sessions) {

        try (Writer writer = new FileWriter("sessions.csv");
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL.withHeader("ID_UTENTE", "ID_SESSION"))) {

            for (Session session : sessions) {
                csvPrinter.printRecord(session.getIdUtente(), session.getIdSession());
            }
            csvPrinter.flush();
            System.out.println("Updated file");

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public Session getSessionByIdSession (String idSession) {
        List<Session> sessionsById = new ArrayList<>();

        List<Session> sessions = getSessionsFromFile();

        for (Session session : sessions) {
            if (session.getIdSession().equals(idSession)) {
                return session;
            }
        }
        return null;
    }
}
