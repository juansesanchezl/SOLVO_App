package com.SQLib;

import android.net.ConnectivityManager;

public class ConductorSolvo {

    private String Nombre;
    private String Usuario;
    private String NumContacto;
    private String FechaNac;
    private String Genero;
    private String Correoe;
    private String Ciudad;
    private String Puntos;


    public ConductorSolvo(String Nombre, String Usuario, String NumContacto, String FechaNac, String Genero, String Correoe, String Ciudad, String Puntos ){
        this.Nombre = Nombre;
        this.Usuario = Usuario;
        this.NumContacto = NumContacto;
        this.FechaNac = FechaNac;
        this.Genero = Genero;
        this.Correoe = Correoe;
        this.Ciudad = Ciudad;
        this.Puntos = Puntos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getNumContacto() {
        return NumContacto;
    }

    public void setNumContacto(String numContacto) {
        NumContacto = numContacto;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getCorreoe() {
        return Correoe;
    }

    public void setCorreoe(String correoe) {
        Correoe = correoe;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getPuntos() {
        return Puntos;
    }

    public void setPuntos(String puntos) {
        Puntos = puntos;
    }
}
