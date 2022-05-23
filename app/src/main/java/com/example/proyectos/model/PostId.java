package com.example.proyectos.model;

public class PostId {


    public String PostId;

    public <T extends PostId> T withId( final String id) {
        this.PostId = id;
        return (T) this;
    }
}
