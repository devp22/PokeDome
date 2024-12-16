package edu.gatech.pokedome.pokemon;

import edu.gatech.pokedome.pokemon.modelConverters.ModelHitPointStrategyConverter;
import edu.gatech.pokedome.pokemon.modelConverters.ModelSkillConverter;
import edu.gatech.pokedome.pokemon.modelConverters.ModelStatGroupConverter;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PokemonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NonNull private final String nickname;
    @NonNull private final String type;

    @Column(length = 1024)
    @Convert(converter = ModelSkillConverter.class)
    private final Skill[] offensiveSkills;
    
    @Column(length = 1024)
    @Convert(converter = ModelSkillConverter.class)
    private final Skill[] defensiveSkills;

    @Convert(converter = ModelStatGroupConverter.class)
    private final StatGroup baseStats;

    @Convert(converter = ModelHitPointStrategyConverter.class)
    private final HitPointStrategy hitPointStrategy;
}
