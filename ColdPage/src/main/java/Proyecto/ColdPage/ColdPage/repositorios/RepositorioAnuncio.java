package Proyecto.ColdPage.ColdPage.repositorios;

import Proyecto.ColdPage.ColdPage.entidades.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAnuncio extends JpaRepository<Anuncio, String>{
    
}
