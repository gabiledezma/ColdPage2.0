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

    @Query("SELECT u FROM Usuario u ORDER BY profesion ASC")
    public List<Usuario> ordenarPorProfesion();

    @Query("SELECT u FROM Usuario u ORDER BY nombre ASC")
    public List<Usuario> ordenarPorNombre();

    @Query("SELECT u FROM Usuario u ORDER BY pais ASC")
    public List<Usuario> ordenarPorPais();

    @Query("SELECT u FROM Usuario u ORDER BY provincia ASC")
    public List<Usuario> ordenarProvincia();

    @Query("SELECT u FROM Usuario u ORDER BY localidad ASC")
    public List<Usuario> ordenarPorLocalidad();

    @Query("SELECT u FROM Usuario u ORDER BY profesion DESC")
    public List<Usuario> ordenarPorProfesion2();

    @Query("SELECT u FROM Usuario u ORDER BY nombre DESC")
    public List<Usuario> ordenarPorNombre2();

    @Query("SELECT u FROM Usuario u ORDER BY pais DESC")
    public List<Usuario> ordenarPorPais2();

    @Query("SELECT u FROM Usuario u ORDER BY provincia DESC")
    public List<Usuario> ordenarProvincia2();

    @Query("SELECT u FROM Usuario u ORDER BY localidad DESC")
    public List<Usuario> ordenarPorLocalidad2();

}
