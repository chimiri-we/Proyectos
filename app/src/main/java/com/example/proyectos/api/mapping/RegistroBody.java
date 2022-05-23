package com.example.proyectos.api.mapping;

public class RegistroBody {

    private String id;
    private String token;
    private String nombre;
    private String apellido_pat;
    private String apellido_mat;
    private String telefono;
    private String correo;
    private String password;

    public RegistroBody(String id, String token, String nombre, String apellido_pat, String apellido_mat, String telefono, String correo, String password) {
        this.id = id;
        this.token = token;
        this.nombre = nombre;
        this.apellido_pat = apellido_pat;
        this.apellido_mat = apellido_mat;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
    }

    public RegistroBody(String nombre, String apellido_mat, String apellido_pat, String correo, String telefono, String password) {
        this.nombre = nombre;
        this.apellido_pat = apellido_pat;
        this.apellido_mat = apellido_mat;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) {
        this.apellido_pat = apellido_pat;
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat) {
        this.apellido_mat = apellido_mat;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
