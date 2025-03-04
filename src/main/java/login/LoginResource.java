package login;

import fileManager.SessionManager;
import fileManager.UserManager;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import model.Session;
import model.User;

import java.net.URI;

@Path("login")
public class LoginResource {
    private final Template login;
    private final UserSessionService userSessionService;
    private final UserManager userManager;
    private final SessionManager sessionManager;

    public LoginResource(Template login, UserSessionService userSessionService, UserManager userManager, SessionManager sessionManager) {
        this.login = login;
        this.userSessionService = userSessionService;
        this.userManager = userManager;
        this.sessionManager = sessionManager;
    }

    @GET
    public TemplateInstance drawLogin(){
        return login.instance();
    }

    @POST
    public Response processLogin(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        System.out.println(email + " " + password);

        User user = userManager.getUserByCredentials(email, password);

        if (user != null) {
            Session session = userSessionService.getIdSession(user);
            sessionManager.saveSession(session);

            return Response.seeOther(URI.create("/profile"))
                    .cookie(new NewCookie.Builder("sessione").value(session.getIdSession()).build())
                    .build();
        } else {
            return Response.status(401).entity(login.instance()).build();
        }
    }
}
