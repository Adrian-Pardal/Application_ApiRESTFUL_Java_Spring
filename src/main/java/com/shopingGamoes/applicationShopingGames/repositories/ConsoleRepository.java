package com.shopingGamoes.applicationShopingGames.repositories;

import com.shopingGamoes.applicationShopingGames.models.ConsoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsoleRepository extends JpaRepository<ConsoleModel , UUID> {
    Page<ConsoleModel> findAll(Pageable pageable);

}
