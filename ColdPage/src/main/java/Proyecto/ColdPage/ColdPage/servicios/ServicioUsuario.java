package Proyecto.ColdPage.ColdPage.servicios;

import Proyecto.ColdPage.ColdPage.repositorios.RepositorioUsuario;
import Proyecto.ColdPage.ColdPage.entidades.Usuario;
import Proyecto.ColdPage.ColdPage.enums.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ServicioUsuario implements UserDetailsService {

    @Autowired
    private RepositorioUsuario ru;

    @Transactional
    public Usuario crearUsuario(String email, String pw1, String pw2, String nombre, String contacto, String fechaDeNacimiento, String pais, String provincia, String localidad, String profesion) throws Exception {
        try {
            validar(email, pw1, pw2, nombre, contacto, fechaDeNacimiento, pais, provincia, localidad);
            Usuario usuario = new Usuario();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setEmail(email);
            usuario.setPassword(encoder.encode(pw1));
            usuario.setRole(Role.USER);
            usuario.setNombre(nombre);
            usuario.setContacto(contacto);
            String anio = fechaDeNacimiento.substring(0, 4);
            String mes = fechaDeNacimiento.substring(5, 7);
            String dia = fechaDeNacimiento.substring(8, 10);
            int anioInt = Integer.parseInt(anio);
            int mesInt = Integer.parseInt(mes);
            int diaInt = Integer.parseInt(dia);
            Date fecha = new Date(anioInt - 1900, mesInt - 1, diaInt);
            usuario.setFechaDeNacimiento(fecha);
            String domicilio = localidad + ", " + provincia + ", " + pais;
            usuario.setDomicilio(domicilio);
            usuario.setPais(pais);
            usuario.setProvincia(provincia);
            usuario.setLocalidad(localidad);
            usuario.setFoto(null);
            usuario.setProfesion(profesion);
            return ru.save(usuario);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Usuario modificarUsuario(String id, String email, String pw1, String profesion, String pais, String provincia, String localidad, String nombre, String contacto, String fechaDeNacimiento, String fotourl) throws Exception {
        Usuario u = getOne(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (u == null) {
            throw new Exception("No existe un usuario con esa ID");
        }
        if (encoder.matches(pw1, u.getPassword())) {
            if (email == null || email.trim().isEmpty()) {
                u.setEmail(u.getEmail());
            } else {
                u.setEmail(email);
            }
            if (pw1 == null || pw1.trim().isEmpty()) {
                throw new Exception("Las contraseña no pueden estar vacia");
            }
            u.setRole(u.getRole());
            if (profesion == null || profesion.trim().isEmpty()) {
                u.setProfesion(u.getProfesion());
            } else {
                u.setProfesion(profesion);
            }
            if (localidad == null || localidad.trim().isEmpty() || provincia == null || provincia.trim().isEmpty() || pais == null || pais.trim().isEmpty()) {
                u.setDomicilio(u.getDomicilio());
            } else {
                String domicilio = localidad + ", " + provincia + ", " + pais;
                u.setDomicilio(domicilio);
                u.setPais(pais);
                u.setProvincia(provincia);
                u.setLocalidad(localidad);
            }
            if (nombre == null || nombre.trim().isEmpty()) {
                u.setNombre(u.getNombre());
            } else {
                u.setNombre(nombre);
            }
            if (contacto == null || contacto.trim().isEmpty()) {
                u.setContacto(u.getContacto());
            } else {
                u.setContacto(contacto);
            }
            if (fechaDeNacimiento == null || fechaDeNacimiento.trim().isEmpty()) {
                u.setFechaDeNacimiento(u.getFechaDeNacimiento());
            } else {
                String anio = fechaDeNacimiento.substring(0, 4);
                String mes = fechaDeNacimiento.substring(5, 7);
                String dia = fechaDeNacimiento.substring(8, 10);
                int anioInt = Integer.parseInt(anio);
                int mesInt = Integer.parseInt(mes);
                int diaInt = Integer.parseInt(dia);
                Date fecha = new Date(anioInt - 1900, mesInt - 1, diaInt);
                u.setFechaDeNacimiento(fecha);
            }
            if (fotourl == null || fotourl.trim().isEmpty()) {
                u.setFoto(u.getFoto());
            } else {
                u.setFoto(fotourl);
            }

        } else {
            throw new Exception("Las contraseñas no coinciden");
        }
        return ru.save(u);
    }

    @Transactional
    public void eliminarUsuario(String id) {
        Usuario u = getOne(id);
        ru.delete(u);
    }

    @Transactional
    public List<Usuario> findAll() {
        return ru.findAll();
    }

    @Transactional
    public Usuario getOne(String id) {
        return ru.getOne(id);
    }

    @Transactional
    public Usuario buscarPorEmail(String email) {
        return ru.findByEmail(email);
    }

    public void validar(String email, String pw1, String pw2, String nombre, String contacto, String fechaDeNacimiento, String pais, String provincia, String localidad) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("Email no puede estar vacio");
        }
        if (ru.findByEmail(email) != null) {
            throw new Exception("Ya existe un usuario con ese email");
        }
        if (pw1 == null || pw2 == null || pw1.isEmpty() || pw2.isEmpty()) {
            throw new Exception("Las contraseñas no pueden estar vacias");
        }
        if (!pw1.equals(pw2)) {
            throw new Exception("Las contraseñas no coinciden");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
        if (contacto == null || contacto.trim().isEmpty()) {
            throw new Exception("El contacto no puede estar vacio");
        }
        if (fechaDeNacimiento == null || fechaDeNacimiento.trim().isEmpty()) {
            throw new Exception("La fecha de nacimiento no puede estar vacia");
        }
        if (pais == null || pais.trim().isEmpty()) {
            throw new Exception("El pais no puede estar vacio");
        }
        if (provincia == null || provincia.trim().isEmpty()) {
            throw new Exception("La provincia no puede estar vacia");
        }
        if (localidad == null || localidad.trim().isEmpty()) {
            throw new Exception("La localidad no puede estar vacia");
        }

    }

    public void validarPassword(String pw1, String pw2) throws Exception {
        if (pw1 == null || pw2 == null || pw1.isEmpty() || pw2.isEmpty()) {
            throw new Exception("Las contraseñas no pueden estar vacias");
        }
        if (!pw1.equals(pw2)) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = ru.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());//ROLE_ADMIN O ROLE_USER
            permisos.add(p1);
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) throws Exception {
        Optional<Usuario> respuesta = ru.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRole().equals(Role.USER)) {
                usuario.setRole(Role.ADMIN);
            } else if (usuario.getRole().equals(Role.ADMIN)) {
                usuario.setRole(Role.USER);
            }
        }
    }

    public List<Usuario> buscarPorProfesion(String profesion) {
        return ru.buscarPorProfesion(profesion);
    }

    public Usuario subirFoto(String id, String fotourl) {
        Usuario u = getOne(id);
        if (fotourl == null || fotourl.trim().isEmpty()) {
            u.setFoto(u.getFoto());
        } else {
            u.setFoto(fotourl);
        }
        return ru.save(u);
    }

    public Usuario eliminarFoto(String id) {
        Usuario u = getOne(id);
        u.setFoto(null);
        return ru.save(u);
    }

    public Usuario cambiarPassword(String id, String nueva1, String nueva2, String anterior) throws Exception {
        Usuario u = getOne(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (u == null) {
            throw new Exception("No existe un usuario con esa ID");
        }
        if (encoder.matches(anterior, u.getPassword())) {
            validarPassword(nueva1, nueva2);
            u.setPassword(encoder.encode(nueva1));
        } else {
            throw new Exception("Las contraseñas no coinciden");
        }
        return ru.save(u);
    }

    @Transactional
    public List<Usuario> buscarPorNombre(String nombre) {
        return ru.buscarPorNombre(nombre);
    }

    @Transactional
    public List<Usuario> buscarPorPais(String pais) {
        return ru.buscarPorPais(pais);
    }

    @Transactional
    public List<Usuario> buscarPorProvincia(String provincia) {
        return ru.buscarPorProvincia(provincia);
    }

    @Transactional
    public List<Usuario> buscarPorLocalidad(String localidad) {
        return ru.buscarPorLocalidad(localidad);
    }

    public List<Usuario> ordenarPorProfesion() {
        return ru.ordenarPorProfesion();
    }

    public List<Usuario> ordenarPorNombre() {
        return ru.ordenarPorNombre();
    }

    public List<Usuario> ordenarPorPais() {
        return ru.ordenarPorPais();
    }

    public List<Usuario> ordenarPorProvincia() {
        return ru.ordenarProvincia();
    }

    public List<Usuario> ordenarPorLocalidad() {
        return ru.ordenarPorLocalidad();
    }

    public List<Usuario> ordenarPorProfesion2() {
        return ru.ordenarPorProfesion2();
    }

    public List<Usuario> ordenarPorNombre2() {
        return ru.ordenarPorNombre2();
    }

    public List<Usuario> ordenarPorPais2() {
        return ru.ordenarPorPais2();
    }

    public List<Usuario> ordenarPorProvincia2() {
        return ru.ordenarProvincia2();
    }

    public List<Usuario> ordenarPorLocalidad2() {
        return ru.ordenarPorLocalidad2();
    }

}
