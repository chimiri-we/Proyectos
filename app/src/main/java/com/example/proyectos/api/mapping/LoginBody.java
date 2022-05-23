package com.example.proyectos.api.mapping;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto plano Java para representar el cuerpo de la petición POST /usuario/login
 */
public class LoginBody {
    private String correo;
    private String password;

    public LoginBody(String correo, String password) {
        this.correo = correo;
        this.password = password;
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
