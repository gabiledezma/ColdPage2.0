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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario su;
    @Autowired
    private RepositorioUsuario ru;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String login, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = ru.findAll();
            model.put("profesionales", profesionales);
            model.put("exito", "Logueado con exito");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "index";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String email, @RequestParam String pw1, @RequestParam String pw2, @RequestParam String nombre, @RequestParam String contacto, @RequestParam String fechaDeNacimiento, @RequestParam String pais, @RequestParam String provincia, @RequestParam String localidad, @RequestParam String profesion) {
        try {
            Usuario u = su.crearUsuario(email, pw1, pw2, nombre, contacto, fechaDeNacimiento, pais, provincia, localidad, profesion);
            modelo.put("exito", "Registro exitoso.");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelo.put("error", "Faltó algún dato.");
            return "registro";
        }
    }

    @GetMapping("/editar")
    public String editar(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
        } catch (Exception e) {
        }
        return "editar-perfil";
    }

    @PostMapping("/editar")
    public String editarPerfil(@RequestParam String id, @RequestParam String email, @RequestParam String pw1, RedirectAttributes redirectAttributes, ModelMap model, @RequestParam String profesion, @RequestParam String pais, @RequestParam String provincia, @RequestParam String localidad, @RequestParam String nombre, @RequestParam String contacto, @RequestParam String fechaDeNacimiento, @RequestParam String fotourl) {
        try {
            Usuario u = su.modificarUsuario(id, email, pw1, profesion, pais, provincia, localidad, nombre, contacto, fechaDeNacimiento, fotourl);
            model.put("exito", "Usuario modificado con exito");
            redirectAttributes.addFlashAttribute("exito", "Usuario modificado con exito");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuario/perfil";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
        } catch (Exception e) {

        }
        return "perfil";
    }

    @GetMapping("/perfil2/{id}")
    public String perfil2(ModelMap model, HttpSession session, @PathVariable String id) {
        try {
            Usuario sesion = (Usuario) session.getAttribute("usuariosession");
            Usuario u = su.getOne(id);
            model.put("usuario", u);
            model.put("sesion", sesion);
        } catch (Exception e) {

        }
        return "perfil2";
    }

    @GetMapping("/editarFoto")
    public String editarFoto(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
        } catch (Exception e) {

        }
        return "edit-foto";
    }

    @PostMapping("/editarFoto")
    public String editarFoto(@RequestParam String id, @RequestParam String fotourl, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            Usuario u = su.subirFoto(id, fotourl);
            model.put("exito", "Foto modificada con exito");
            redirectAttributes.addFlashAttribute("exito", "Usuario modificado con exito");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuario/perfil";
    }

    @PostMapping("/eliminarFoto")
    public String eliminarFoto(@RequestParam String id, @RequestParam String foto, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            Usuario u = su.eliminarFoto(id);
            model.put("exito", "Foto eliminada con exito");
            redirectAttributes.addFlashAttribute("exito", "Usuario modificado con exito");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuario/perfil";
    }

    @GetMapping("/cambiarPassword")
    public String cambiarPassword(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
        } catch (Exception e) {

        }
        return "edit-password";
    }

    @PostMapping("/cambiarPassword")
    public String cambiarPassword(@RequestParam String id, @RequestParam String nueva1, @RequestParam String nueva2, @RequestParam String anterior, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            Usuario u = su.cambiarPassword(id, nueva1, nueva2, anterior);
            model.put("exito", "Contraseña modificada con exito");
            redirectAttributes.addFlashAttribute("exito", "Usuario modificado con exito");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuario/perfil";
    }

    @GetMapping("/listarPorNombre")
    public String listarPorNombre(@RequestParam String nombre, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.buscarPorNombre(nombre);
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/listarPorProfesion")
    public String listarPorProfesion(@RequestParam String profesion, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.buscarPorProfesion(profesion);
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/listarPorPais")
    public String listarPorPais(@RequestParam String pais, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.buscarPorPais(pais);
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/listarPorProvincia")
    public String listarPorProvincia(@RequestParam String provincia, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.buscarPorProvincia(provincia);
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/listarPorLocalidad")
    public String listarPorLocalidad(@RequestParam String localidad, ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.buscarPorLocalidad(localidad);
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/listarTodos")
    public String listarTodos(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.findAll();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorProfesion")
    public String ordenarPorProfesion(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorProfesion();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorNombre")
    public String ordenarPorNombre(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorNombre();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorPais")
    public String ordenarPorPais(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorPais();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorProvincia")
    public String ordenarPorProvincia(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorProvincia();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorLocalidad")
    public String ordenarPorLocalidad(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorLocalidad();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorProfesion2")
    public String ordenarPorProfesion2(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorProfesion2();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorNombre2")
    public String ordenarPorNombre2(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorNombre2();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorPais2")
    public String ordenarPorPais2(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorPais2();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorProvincia2")
    public String ordenarPorProvincia2(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorProvincia2();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

    @GetMapping("/ordenarPorLocalidad2")
    public String ordenarPorLocalidad2(ModelMap model, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            model.put("usuario", u);
            List<Usuario> profesionales = su.ordenarPorLocalidad2();
            model.put("profesionales", profesionales);
        } catch (Exception e) {
        }
        return "list-profesionales";
    }

}
