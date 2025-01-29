package com.shopingGamoes.applicationShopingGames.controlers;

import com.shopingGamoes.applicationShopingGames.dtos.GameRecordsDto;
import com.shopingGamoes.applicationShopingGames.models.GameModel;
import com.shopingGamoes.applicationShopingGames.repositories.GameRepository;
import com.shopingGamoes.applicationShopingGames.services.ServiceConsoles;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class GameController {

    @Autowired
    GameRepository gameRepository;



    //Metodo para Criar o Jogo na base de dados
    @PostMapping("/games")
    public ResponseEntity<GameModel> saveGame(@RequestBody @Valid GameRecordsDto gameRecordsDto){
        var gameModel = new GameModel();
        BeanUtils.copyProperties(gameRecordsDto, gameModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameRepository.save(gameModel));
    }

    //Metodo de GetAll
    @GetMapping("/games")
    public ResponseEntity<List<GameModel>> getAllGame(){
        List<GameModel> gameList = gameRepository.findAll();
        if (!gameList.isEmpty()){
            for (GameModel game : gameList) {
                UUID id = game.getIdGame();
                game.add(linkTo(methodOn(GameController.class).getOneGame(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameList);
    }

    //Metodo Get One
    @GetMapping("/games/{id}")
    public ResponseEntity<Object> getOneGame(@PathVariable(value = "id") UUID id){
        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado ! .");
        }
        gameO.get().add(linkTo(methodOn(GameController.class).getAllGame()).withRel("Lista Dos Games"));

        return ResponseEntity.status(HttpStatus.OK).body(gameO.get());
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable(value = "id") UUID id , @RequestBody @Valid GameRecordsDto gameRecordsDto){

        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado! .");
        }
        var gameModel = gameO.get();
        BeanUtils.copyProperties(gameRecordsDto , gameModel);
        return ResponseEntity.status(HttpStatus.OK).body(gameRepository.save(gameModel));
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable(value = "id") UUID id ){
        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado! ");

        }
        gameRepository.delete(gameO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Game Deletado com Sucesso");
    }
}
