package com.example.redesocial.usuario.telefone;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Telefone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    // <editor-folder  defaultstate="collapsed" desc="Getters/Setters" >


    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // </editor-folder>
}
