package model;

public class Session {
    private String idUtente;
    private String idSession;

    public Session(String idUtente, String idSession) {
        this.idUtente = idUtente;
        this.idSession = idSession;
    }

    public String getIdUtente() {
        return idUtente;
    }

    public String getIdSession() {
        return idSession;
    }
}
