package com.shopingGamoes.applicationShopingGames.repositories;

import com.shopingGamoes.applicationShopingGames.models.GameModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameModel , UUID>{
    //Estou chamando a função  Page<ConsoleModel> queremos retornar um número limitado de registros em uma única requisição.
    //O Pageable estou representando a pagina e quantos itens vou querer por pagina
    Page<GameModel> findAll(Pageable pageable);
}
