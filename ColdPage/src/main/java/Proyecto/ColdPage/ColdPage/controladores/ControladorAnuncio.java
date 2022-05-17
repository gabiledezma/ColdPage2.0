package Proyecto.ColdPage.ColdPage.controladores;

import Proyecto.ColdPage.ColdPage.entidades.Anuncio;
import Proyecto.ColdPage.ColdPage.servicios.ServicioAnuncio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/anuncio")
public class ControladorAnuncio {
    
    @Autowired
    private ServicioAnuncio sa;
    
    @PostMapping("/crear")
    public String crearAnuncio(ModelMap model, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Long telefono, @RequestParam String presupuesto) throws Exception{
        
        sa.crearAnuncio(nombre, descripcion, telefono, presupuesto);
        
        return "index";
        
    }
    
    @GetMapping("/list")
    public String listAnuncio (ModelMap model){
        List<Anuncio> anuncios = sa.listarAnuncios();
        model.put("anuncios", anuncios);
        return "index";
    }
}
