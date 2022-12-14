/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.example.redesocial.client.PageController;
import com.example.redesocial.client.UsuarioSessionBean;
import com.example.redesocial.usuario.UsuarioServiceLocal;
import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author andre-barros
 */
@Named
@RequestScoped
public class LoginController {

    @Inject
    PageController pageController;

    @NotEmpty
    private String email = "thigas@gmail.com";

    @NotEmpty
    private String password = "senha123";

    @Inject
    FacesContext facesContext;

    @Inject
    SecurityContext securityContext;
    
    @Inject UsuarioServiceLocal usuarioService;

    @Inject UsuarioSessionBean usuarioSession;
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //</editor-fold>

    public void execute() throws IOException {
        switch (processAuthentication()) {
            case SEND_CONTINUE:
                saveUserInSession();
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials", null));
                break;
            case SUCCESS:
                saveUserInSession();
                pageController.goToHome();
                break;
        }
    }

    private void saveUserInSession() {
        usuarioSession.conectar(usuarioService.buscarPorEmail(email));
    }

    private AuthenticationStatus processAuthentication() {
        ExternalContext ec = getExternalContext();
        return securityContext.authenticate(
                (HttpServletRequest) ec.getRequest(),
                (HttpServletResponse) ec.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(email, password)));
    }

    private ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }
}
