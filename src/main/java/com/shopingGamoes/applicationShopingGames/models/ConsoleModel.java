package com.shopingGamoes.applicationShopingGames.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_CONSOLES")
public class ConsoleModel extends RepresentationModel<ConsoleModel> implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    //Atributos
    private UUID idConsole;
    private String name;
    private String plataforma;
    private BigDecimal value;

    public UUID getIdConsole(){
        return idConsole;
    }
    public void setIdConsole(UUID idConsole){
        this.idConsole = idConsole;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPlataforma(){
        return  plataforma;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public BigDecimal getValue(){
        return value;
    }
    public void setValue(BigDecimal value){
        this.value = value;
    }
}
