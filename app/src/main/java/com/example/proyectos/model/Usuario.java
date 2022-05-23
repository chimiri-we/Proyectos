package com.example.proyectos.model;

import android.graphics.Bitmap;

public class Usuario {


    private int id_local;
    private String id;
    private String token;
    private String nombre;
    private String apellido_pat;
    private String apellido_mat;


    //  private String FotoPerfil;
    private String genero;
    private String ciudad;
    private String urlImagen;
    private String colonia;
    private String calle;
    private String calle_n;
    private String estado;
    private String pais;
    private String cp;
    private String fechaNacimiento;
    private String telefono;
    private String correo;
    private String password;

    public Usuario(int id, String nombre, String apellido_mat, String apellido_pat, String correo, String telefono, String password) {
      this.id_local= id;
        this.nombre = nombre;
        this.apellido_mat = apellido_mat;
        this.apellido_pat = apellido_pat;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;

    }
    public Usuario(String id, String nombre, String apellido_mat, String apellido_pat, String correo, String telefono, String password, String token) {
this.id = id;
        this.nombre = nombre;
        this.apellido_mat = apellido_mat;
        this.apellido_pat = apellido_pat;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.token = token;

    }

    public Usuario() {

    }

    public Usuario(String FeatureName,String thoroughfare, String locality, String subAdminArea, String adminArea, String countryName, String postalCode) {

        this.calle_n = FeatureName;
        this.calle = thoroughfare;
            this.colonia = locality;
            this.ciudad = subAdminArea;
            this.estado = adminArea;
            this.pais = countryName;
            this.cp= postalCode;


    }

    public Usuario(String id, String thoroughfare, String locality, String subAdminArea, String adminArea) {

        this.id = id;
        this.calle = thoroughfare;
        this.colonia = locality;
        this.ciudad = subAdminArea;
        this.estado = adminArea;
    }


    // private Bitmap imagen;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getId_local() {
        return id_local;
    }

    public void setId_local(int id_local) {
        this.id_local = id_local;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle_n() {
        return calle_n;
    }

    public void setCalle_n(String calle_n) {
        this.calle_n = calle_n;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
