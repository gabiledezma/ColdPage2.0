package Proyecto.ColdPage.ColdPage.entidades;

import Proyecto.ColdPage.ColdPage.enums.Role;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String email;
    private String password;
    private Role role;
    private String nombre;
    private String profesion;
    private String contacto;
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;
    private String domicilio;
    private String pais;
    private String provincia;
    private String localidad;
    private String foto;

    public Usuario() {
    }

    public Usuario(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;

    }

    public Usuario(String email, String password, Role role, String nombre, String contacto, Date fechaDeNacimiento, String domicilio) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.nombre = nombre;
        this.contacto = contacto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.domicilio = domicilio;
    }

    public Usuario(String email, String password, String nombre, String profesion, String contacto, Date fechaDeNacimiento, String domicilio, String foto) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.nombre = nombre;
        this.profesion = profesion;
        this.contacto = contacto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.domicilio = domicilio;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    
}
