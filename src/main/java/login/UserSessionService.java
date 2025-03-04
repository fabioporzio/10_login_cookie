package login;

import fileManager.SessionManager;
import jakarta.enterprise.context.ApplicationScoped;
import fileManager.UserManager;
import model.Session;
import model.User;

import java.util.List;

@ApplicationScoped
public class UserSessionService {

    private final UserManager userManager;
    private final SessionManager sessionManager;

    public UserSessionService(UserManager userManager, SessionManager sessionManager) {
        this.userManager = userManager;
        this.sessionManager = sessionManager;
    }

    public Session getIdSession(User user){
        String idSessionGenerated;

        double idSession = Math.random() * 3000.0;
        idSessionGenerated = String.valueOf(idSession);

        Session session = new Session(user.getId(), idSessionGenerated);

        return session;
    }

    public User getUserFromSession(String idSession){
        List<Session> loadedSession = sessionManager.getSessionsFromFile();

        for (Session session1 : loadedSession) {
            if (session1.getIdSession().equals(idSession)) {
                User user = userManager.getUserById(session1.getIdUtente());
                return user;
            }
        }

        return null;
    }

    public Session logout(Session session){
        List<Session> loadedSession = sessionManager.getSessionsFromFile();

        for (Session session1 : loadedSession) {
            if (session1.getIdSession().equals(session.getIdSession())) {
                session = null;
                return session;
            }
        }
        return session;
    }
}
