package com.example.proyectos.api.mapping;

import com.example.proyectos.model.Empresa;

import java.util.List;

public class ListaEmpresa {

   private List<Empresa> empresa;

    public ListaEmpresa(List<Empresa> empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresa() {
        return empresa;
    }
}
