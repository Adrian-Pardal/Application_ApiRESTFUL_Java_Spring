package com.shopingGamoes.applicationShopingGames.controlers;

import com.shopingGamoes.applicationShopingGames.dtos.ConsoleRecordsDto;
import com.shopingGamoes.applicationShopingGames.models.ConsoleModel;
import com.shopingGamoes.applicationShopingGames.repositories.ConsoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ConsoleController {

    @Autowired
    ConsoleRepository consoleRepository;

    //Metodo para Criar o Console
    @PostMapping("/consoles")
    public ResponseEntity<ConsoleModel> saveConsole(@RequestBody @Valid ConsoleRecordsDto consoleRecordsDto){
        var consoleModel = new ConsoleModel();
        BeanUtils.copyProperties(consoleRecordsDto , consoleModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(consoleRepository.save(consoleModel));
    }

    //Metodo GetAll pesquisar todos os consoles no banco de dados
    @GetMapping("/consoles")
    public ResponseEntity<List<ConsoleModel>> getAllConsoles(){

        List<ConsoleModel> consoleList = consoleRepository.findAll();
        if(!consoleList.isEmpty()){
            for (ConsoleModel console : consoleList){

                UUID id = console.getIdConsole();
                console.add(linkTo(methodOn(ConsoleController.class).getOneConsole(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(consoleList);
    }

    //Metodo Getone que vai pesquisar um unico console
    @GetMapping("/consoles/{id}")
    public ResponseEntity<Object> getOneConsole(@PathVariable(value = "id") UUID id){

        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        consoleO.get().add(linkTo(methodOn(ConsoleController.class).getAllConsoles()).withRel("Lista de Consoles"));
        return ResponseEntity.status(HttpStatus.OK).body(consoleO.get());
    }

    //Metodo Put para atualizar os consoles
    @PutMapping("/consoles/{id}")
    public ResponseEntity<Object> updateConsole(@PathVariable(value = "id") UUID id , @RequestBody @Valid ConsoleRecordsDto consoleRecordsDto){

        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        var consoleModel = consoleO.get();
        BeanUtils.copyProperties(consoleRecordsDto , consoleModel);
        return ResponseEntity.status(HttpStatus.OK).body(consoleRepository.save(consoleModel));
    }

    //Metodo de Deletar Console
    @DeleteMapping("/consoles/{id}")
    public ResponseEntity<Object> deleteConsole(@PathVariable(value = "id") UUID id){
        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        consoleRepository.delete(consoleO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Console Deletado com Sucesso");
    }

}

