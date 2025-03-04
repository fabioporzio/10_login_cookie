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
        User user = userSessionService.getUserFromSession(idSession);
        Session session = userSessionService.getIdSession(user);

        userSessionService.logout(session);

        return Response.seeOther(URI.create("/login"))
                .cookie(new NewCookie.Builder("UTENTE").value(null).build())
                .build();
    }
}
