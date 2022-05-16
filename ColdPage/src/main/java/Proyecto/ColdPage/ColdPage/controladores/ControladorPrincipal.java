package Proyecto.ColdPage.ColdPage.controladores;

import Proyecto.ColdPage.ColdPage.entidades.Usuario;
import Proyecto.ColdPage.ColdPage.repositorios.RepositorioUsuario;
import Proyecto.ColdPage.ColdPage.servicios.ServicioUsuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")//http://localhost:8080
public class ControladorPrincipal {

    @Autowired
    private ServicioUsuario su;
    @Autowired
    private RepositorioUsuario ru;

    @GetMapping("/")//localhost:8080/
    public String index(@RequestParam(required = false) String login, ModelMap model, HttpSession session) {
        if (login != null) {
            model.put("exito", "Logueado con exito");
        }
        Usuario u = (Usuario) session.getAttribute("usuariosession");
        model.put("usuario", u);
        List<Usuario> profesionales = ru.findAll();
        model.put("profesionales", profesionales);
        return "index";
    }

    @PostMapping("/")
    public String registro(ModelMap modelo, @RequestParam String email, @RequestParam String pw1, @RequestParam String pw2, @RequestParam String nombre, @RequestParam String contacto, @RequestParam String fechaDeNacimiento, @RequestParam String pais, @RequestParam String provincia, @RequestParam String localidad) {
        try {
            Usuario u = su.crearUsuario(email, pw1, pw2, nombre, contacto, fechaDeNacimiento, pais, provincia, localidad);
            modelo.put("exito", "Registro exitoso.");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelo.put("error", "Faltó algún dato.");
            return "registro";

        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o Contraseña incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Desconectado correctamente");
        }
        return "index";
    }

}
