package com.shopingGamoes.applicationShopingGames.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConsoleRecordsDto(@NotBlank String name, @NotBlank String plataforma , @NotNull BigDecimal value) {
}
