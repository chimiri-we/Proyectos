package com.example.proyectos.api.mapping;

import com.example.proyectos.model.User;
import com.example.proyectos.model.Usuario;

import java.util.List;

public class ListUsuario {

    private List<User> user;

    public ListUsuario(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }
}
