package com.shopingGamoes.applicationShopingGames.services;

import com.shopingGamoes.applicationShopingGames.models.ConsoleModel;
import com.shopingGamoes.applicationShopingGames.repositories.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceConsole {
    
    @Autowired
    ConsoleRepository consoleRepository;
    // classe para fazer a paginação eu chamei a list coloquei o model  e coloquei as variaveis
    public List<ConsoleModel> findAllPaginacao(int pagina , int itens){

        //estou retornando o metodo que esta no consoleRepository para o controller conseguir instanciar
        return consoleRepository.findAll(PageRequest.of(pagina, itens)).getContent();
    }
}
