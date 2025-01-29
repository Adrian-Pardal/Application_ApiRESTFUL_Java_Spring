package com.shopingGamoes.applicationShopingGames.controlers;

import com.shopingGamoes.applicationShopingGames.dtos.GameRecordsDto;
import com.shopingGamoes.applicationShopingGames.models.GameModel;
import com.shopingGamoes.applicationShopingGames.repositories.GameRepository;
import com.shopingGamoes.applicationShopingGames.services.ServiceGame;
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

    @Autowired
    ServiceGame serviceGame;

    //Metodo para Criar o Jogo na base de dados
    @PostMapping("/games")
    public ResponseEntity<GameModel> saveGame(@RequestBody @Valid GameRecordsDto gameRecordsDto){
        var gameModel = new GameModel();
        BeanUtils.copyProperties(gameRecordsDto, gameModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameRepository.save(gameModel));
    }

    //Metodo de GetAll
    @GetMapping("/games")
    public ResponseEntity<List<GameModel>> getAllGame(@RequestParam("pagina") int pagina , @RequestParam("itens")int itens){
        List<GameModel> gameList = gameRepository.findAll();
        if (!gameList.isEmpty()){
            for (GameModel game : gameList) {
                UUID id = game.getIdGame();
                game.add(linkTo(methodOn(GameController.class).getOneGame(id)).withSelfRel());
            }
        }
        // chamando classe Service para instanciar o findAll. Escolhendo qual Pagina e a quantidade de  Itens
        return ResponseEntity.status(HttpStatus.OK).body(serviceGame.findAllPaginacao(pagina , itens));
    }

    //Metodo Get One
    @GetMapping("/games/{id}")
    public ResponseEntity<Object> getOneGame(@PathVariable(value = "id") UUID id){
        Optional<GameModel> gameO = gameRepository.findById(id);
        if (gameO.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game não encontrado ! .");
        }
        gameO.get().add(linkTo(methodOn(GameController.class).getAllGame(0 , 5)).withRel("Lista Dos Games"));
        // vai ser redirecionado para o GetAll ja com esse input padrao de pagina 0 , itens 5
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
