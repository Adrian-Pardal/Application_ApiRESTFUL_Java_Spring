package com.shopingGamoes.applicationShopingGames.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_GAMES")
public class GameModel extends RepresentationModel<GameModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID idGame;
    private String name;
    private String plataforma;
    private BigDecimal value;

    public UUID getIdGame(){
        return idGame;
    }
    public void setIdGame(UUID idGame){
        this.idGame = idGame;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPlataforma(){
        return plataforma;
    }
    public void setPlataforma(String plataforma){
        this.plataforma  = plataforma;
    }
    public BigDecimal getValue(){
        return value;
    }
    public void setValue(BigDecimal value){
        this.value = value;
    }
}
