package com.shopingGamoes.applicationShopingGames.services;

import com.shopingGamoes.applicationShopingGames.controlers.GameController;
import com.shopingGamoes.applicationShopingGames.dtos.GameRecordsDto;
import com.shopingGamoes.applicationShopingGames.models.GameModel;
import com.shopingGamoes.applicationShopingGames.repositories.GameRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ServiceGame {

    @Autowired
    GameRepository gameRepository;

    @Transactional
    public GameModel saveGame(@Valid GameRecordsDto gameRecordsDto){
        var gameModel = new GameModel();
        BeanUtils.copyProperties(gameRecordsDto, gameModel);
        return gameRepository.save(gameModel);
    }

    @Transactional(readOnly = true)
    public List<GameModel> findAllPaginacao(int pagina , int itens){
        List<GameModel> gameList = gameRepository.findAll();
        if (!gameList.isEmpty()){
            for (GameModel game : gameList) {
                UUID id = game.getIdGame();
                game.add(linkTo(methodOn(GameController.class).getOneGame(id)).withSelfRel());
            }
        }
        return gameRepository.findAll(PageRequest.of(pagina , itens)).getContent();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> getOneGame(@PathVariable(value = "id") UUID id){
        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado ! .");
        }
        gameO.get().add(linkTo(methodOn(GameController.class).getAllGame(0 , 5)).withRel("Lista Dos Games"));

        return ResponseEntity.status(HttpStatus.OK).body(gameO.get());
    }

    @Transactional
    public ResponseEntity<Object> updateGame(@PathVariable(value = "id") UUID id , @RequestBody @Valid GameRecordsDto gameRecordsDto){

        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado! .");
        }
        var gameModel = gameO.get();
        BeanUtils.copyProperties(gameRecordsDto , gameModel);
        return ResponseEntity.status(HttpStatus.OK).body(gameRepository.save(gameModel));
    }

    @Transactional
    public ResponseEntity<Object> deleteGame(@PathVariable(value = "id") UUID id ){
        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado! ");

        }
        gameRepository.delete(gameO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Game Deletado com Sucesso");
    }


}
