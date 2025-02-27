package model;

public class Session {
    private int idUtente;
    private String idSession;

    public Session(int idUtente, String idSession) {
        this.idUtente = idUtente;
        this.idSession = idSession;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public String getIdSession() {
        return idSession;
    }
}
