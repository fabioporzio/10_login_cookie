package login;

import fileManager.SessionManager;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import model.Session;
import model.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("logout")
public class LogoutResource {
    private final UserSessionService userSessionService;
    private final SessionManager sessionManager;

    public LogoutResource(UserSessionService userSessionService, SessionManager sessionManager) {
        this.userSessionService = userSessionService;
        this.sessionManager = sessionManager;
    }

    @POST
    public Response logout(@CookieParam("sessione") String idSession) {
        System.out.println("ID_SESSION nella pagina di logout:" + idSession);

        Session session = sessionManager.getSessionByIdSession(idSession);
        System.out.println("ID_SESSION della sessione recuperata" + session.getIdSession());

        List<Session> filteredSessions = sessionManager.filterSessions(session);
        sessionManager.overwriteSession(filteredSessions);

        for (Session loadedSession : filteredSessions) {
            System.out.println(loadedSession.getIdSession());
        }

        userSessionService.logout(session);

        return Response.seeOther(URI.create("/login"))
                .cookie(new NewCookie.Builder("UTENTE").value(null).build())
                .build();
    }
}
