package login;

import io.quarkus.qute.Template;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("registration")
public class RegistrationPageController {

    private final Template registration;
    private final UserManager userManager;

    public RegistrationPageController(Template registration, UserManager userManager) {
        this.registration = registration;
        this.userManager = userManager;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response showRegistrationPage() {
        return Response.ok(registration.render()).build();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response saveUser(
            @FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {
        List<User> utentiRegistrati = userManager.getUsersFromFile();
        User utente = new User(utentiRegistrati.size(), nome, email, password);

        if (!userManager.checkUserExistence(utentiRegistrati, utente)) {
            utentiRegistrati.add(utente);
            userManager.saveUsers(utentiRegistrati);
            return Response.seeOther(URI.create("/login")).build();
        }
        else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nome Utente o Email già in uso.").build();
        }
    }

}
