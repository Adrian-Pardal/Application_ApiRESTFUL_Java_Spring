package com.shopingGamoes.applicationShopingGames.repositories;

import com.shopingGamoes.applicationShopingGames.models.GameModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameModel , UUID>{
    Page<GameModel> findAll(Pageable pageable);
}
