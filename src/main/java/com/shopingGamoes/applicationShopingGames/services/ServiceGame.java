package com.shopingGamoes.applicationShopingGames.services;

import com.shopingGamoes.applicationShopingGames.models.GameModel;
import com.shopingGamoes.applicationShopingGames.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceGame {

    @Autowired
    GameRepository gameRepository;

    // classe para fazer a paginação eu chamei a list coloquei o model  e coloquei as variaveis
    public List<GameModel> findAllPaginacao(int pagina , int itens){

        //estou retornando o metodo que esta no consoleRepository para o controller conseguir instanciar
        return gameRepository.findAll(PageRequest.of(pagina , itens)).getContent();
    }
}
