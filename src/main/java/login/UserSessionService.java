package login;

import fileManager.SessionManager;
import jakarta.enterprise.context.ApplicationScoped;
import fileManager.UserManager;
import model.Session;
import model.User;

@ApplicationScoped
public class UserSessionService {

    private String idSessionGenerated = null;
    private UserManager userManager;
    private SessionManager sessionManager;

    public String login(String email, String password){

        User user = userManager.getUserByCredentials(email, password);

        if (user != null) {
            double idSession = Math.random() * 3000.0;
            idSessionGenerated = String.valueOf(idSession);

            Session session = new Session(user.getId(), idSessionGenerated);
            sessionManager.saveSession(session);

            return idSessionGenerated;
        } else {
            return null;
        }
    }

    public String getUserFromSession(String session){
        if (isSessionValid(session)) {
            return "fabio.porzio00@gmail.com";
        } else {
            return null;
        }
    }

    public void logout(String idSession){
        if (isSessionValid(idSession)) {
            idSessionGenerated = null;
        }
    }

    private boolean isSessionValid(String idSession) {
        return idSession != null && !idSession.isEmpty() && idSession.equals(idSessionGenerated);
    }
}
