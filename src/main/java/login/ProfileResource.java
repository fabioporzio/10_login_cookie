package login;

import fileManager.SessionManager;
import fileManager.UserManager;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import model.Session;
import model.User;

import java.util.List;

@Path("/profile")
public class ProfileResource {
    private final Template profile;
    private final UserSessionService userSessionService;
    private final SessionManager sessionManager;
    private final UserManager userManager;

    public ProfileResource(Template profile, UserSessionService userSessionService, SessionManager sessionManager, UserManager userManager) {
        this.profile = profile;
        this.userSessionService = userSessionService;
        this.sessionManager = sessionManager;
        this.userManager = userManager;
    }

    @GET
    public Response drawProfile(@CookieParam("sessione") String idSession) {
        User user = userSessionService.getUserFromSession(idSession);

        if (user == null) {
            return Response.status(401).build();
        }
        else {
            return Response.ok(profile.data("user", user)).build();
        }
    }
}
