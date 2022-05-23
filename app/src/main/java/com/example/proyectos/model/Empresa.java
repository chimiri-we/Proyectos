package com.example.proyectos.model;

public class Empresa {


    private String id_empresa;
    private String Nombre;
    private String tipo_empresa;
    private String correo_contacto;
    private String telefono_contacto;
    private String descripcion_corta;

    public Empresa(String id_empresa, String nombre, String tipo_empresa, String correo_contacto, String telefono_contacto, String descripcion_corta) {
        this.id_empresa = id_empresa;
        Nombre = nombre;
        this.tipo_empresa = tipo_empresa;
        this.correo_contacto = correo_contacto;
        this.telefono_contacto = telefono_contacto;
        this.descripcion_corta = descripcion_corta;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(String tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public String getCorreo_contacto() {
        return correo_contacto;
    }

    public void setCorreo_contacto(String correo_contacto) {
        this.correo_contacto = correo_contacto;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }
}
