package Proyecto.ColdPage.ColdPage.repositorios;

import Proyecto.ColdPage.ColdPage.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.profesion = :profesion")
    public List<Usuario> buscarPorProfesion(@Param("profesion") String profesion);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public List<Usuario> buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT u FROM Usuario u WHERE u.pais = :pais")
    public List<Usuario> buscarPorPais(@Param("pais") String pais);
    
    @Query("SELECT u FROM Usuario u WHERE u.provincia = :provincia")
    public List<Usuario> buscarPorProvincia(@Param("provincia") String provincia);
    
    @Query("SELECT u FROM Usuario u WHERE u.localidad = :localidad")
    public List<Usuario> buscarPorLocalidad(@Param("localidad") String localidad);

}
