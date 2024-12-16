package edu.gatech.pokedome.controller.dto;

import java.io.Serializable;

public record HitPointStrategyDTO (int aggression, int caution) implements Serializable {
}
