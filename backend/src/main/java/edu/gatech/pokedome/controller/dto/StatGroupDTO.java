package edu.gatech.pokedome.controller.dto;

import java.io.Serializable;

public record StatGroupDTO(int hitPoints, int attack, int defense) implements Serializable {
}