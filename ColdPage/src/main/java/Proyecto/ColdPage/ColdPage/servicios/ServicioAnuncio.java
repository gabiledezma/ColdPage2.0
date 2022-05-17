package Proyecto.ColdPage.ColdPage.servicios;

import Proyecto.ColdPage.ColdPage.entidades.Anuncio;
import Proyecto.ColdPage.ColdPage.repositorios.RepositorioAnuncio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioAnuncio {
    
    @Autowired
    private RepositorioAnuncio ra;
    
    @Transactional
    public void crearAnuncio(String nombre, String descripcion, Long telefono, String presupuesto)throws Exception{
        validacion(nombre, descripcion, telefono, presupuesto);
        Anuncio a = new Anuncio();
        a.setNombre(nombre);
        a.setDescripcion(descripcion);
        a.setTelefono(telefono);
        a.setPresupuesto(presupuesto);
        
        ra.save(a);
    }
    @Transactional
    public List<Anuncio> listarAnuncios(){
        return  ra.findAll();
    }
    
    public void validacion(String nombre, String descripcion, Long telefono, String presupuesto) throws Exception
    {
        if(nombre == null || nombre.trim().isEmpty()){
            throw new Exception("Debe ingresar su nombre");
        }
        if(descripcion == null || descripcion.trim().isEmpty()){
            throw new Exception("Debe ingresar una descripcion del trabajo requerido");
        }
        if(telefono == null){
            throw new Exception("Debe ingresar un telefono con el cual contactarse");
        }
        if(presupuesto == null || presupuesto.trim().isEmpty()){
            throw new Exception("Debe ingresar un presupuesto");
        }
    }

    
}
