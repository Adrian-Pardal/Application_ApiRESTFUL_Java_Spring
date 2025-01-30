package com.shopingGamoes.applicationShopingGames.services;

import com.shopingGamoes.applicationShopingGames.controlers.ConsoleController;
import com.shopingGamoes.applicationShopingGames.dtos.ConsoleRecordsDto;
import com.shopingGamoes.applicationShopingGames.models.ConsoleModel;
import com.shopingGamoes.applicationShopingGames.repositories.ConsoleRepository;
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
public class ServiceConsole {
    
    @Autowired
    ConsoleRepository consoleRepository;

    @Transactional
    public ConsoleModel saveConsole(@Valid ConsoleRecordsDto consoleRecordsDto){

        var consoleModel = new ConsoleModel();
        BeanUtils.copyProperties(consoleRecordsDto , consoleModel);
        return consoleRepository.save(consoleModel);
    }

    @Transactional(readOnly = true)
    public List<ConsoleModel> findAllPaginacao(int pagina , int itens){
        List<ConsoleModel> consoleList = consoleRepository.findAll();
        if(!consoleList.isEmpty()){
            for (ConsoleModel console : consoleList){

                UUID id = console.getIdConsole();
                console.add(linkTo(methodOn(ConsoleController.class).getOneConsole(id)).withSelfRel());

            }
        }

        return consoleRepository.findAll(PageRequest.of(pagina, itens)).getContent();
    }

    @Transactional
    public ResponseEntity<Object> getOneConsole(@PathVariable(value = "id") UUID id){

        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        consoleO.get().add(linkTo(methodOn(ConsoleController.class).getAllConsoles(0,5 )).withRel("Lista de Consoles"));

        return ResponseEntity.status(HttpStatus.OK).body(consoleO.get());
    }

    @Transactional
    public ResponseEntity<Object>  updateConsole(@PathVariable(value = "id") UUID id , @RequestBody @Valid ConsoleRecordsDto consoleRecordsDto){

        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        var consoleModel = consoleO.get();
        BeanUtils.copyProperties(consoleRecordsDto , consoleModel);
        return ResponseEntity.status(HttpStatus.OK).body(consoleRepository.save(consoleModel));
    }

    @Transactional
    public  ResponseEntity<Object> deleteConsole(@PathVariable(value = "id") UUID id){
        Optional<ConsoleModel> consoleO = consoleRepository.findById(id);
        if (consoleO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Console não encontrado! .");
        }
        consoleRepository.delete(consoleO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Console Deletado com Sucesso");
    }
}
