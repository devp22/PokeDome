package edu.gatech.pokedome.modelConverters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.gatech.pokedome.pokemon.effect.Effect;
import edu.gatech.pokedome.pokemon.effect.StatChangeEffect;
import edu.gatech.pokedome.pokemon.modelConverters.EffectSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import edu.gatech.pokedome.pokemon.effect.DamageEffect;
import edu.gatech.pokedome.pokemon.modelConverters.ModelSkillConverter;
import edu.gatech.pokedome.pokemon.modelConverters.ModelHitPointStrategyConverter;

@ExtendWith(MockitoExtension.class)
public class ModelSkillConverterTests {

    @Test
    public void testConvertSkills_EntityToDatabaseToEntity_expectMaintainEntityData() {
        // Arrange
        final List<Effect> attackEffects = List.of(
            DamageEffect.builder().damage(2).build()
        );
        final List<Effect> defenseEffects = List.of(
            StatChangeEffect.builder().defenseChange(2).turnsActive(1).build()
        );
        System.out.println(defenseEffects.toString());
        final Skill[] skills = new Skill[] {
            Skill.builder().name("Tackle").effects(attackEffects).build(),
            Skill.builder().name("Protect").effects(defenseEffects).build(),
        };
        final ModelSkillConverter modelSkillConverter = new ModelSkillConverter(new JsonHelper(new GsonBuilder().registerTypeAdapter(Effect.class, new EffectSerializer()).create()));

        // Act
        String databaseEntry = modelSkillConverter.convertToDatabaseColumn(skills);
        Skill[] convertedSkills = modelSkillConverter.convertToEntityAttribute(databaseEntry);

        // Assert
        // Assert number of skills
        assertThat(convertedSkills.length, equalTo(skills.length));
        // Assert skill parameters
        assertThat(convertedSkills[0].getName(), equalTo(skills[0].getName()));
        assertThat(convertedSkills[1].getName(), equalTo(skills[1].getName()));
        // Assert number of effects
        assertThat(convertedSkills[0].getEffects().size(), equalTo(skills[0].getEffects().size()));
        assertThat(convertedSkills[1].getEffects().size(), equalTo(skills[1].getEffects().size()));
        // Assert effect parameters
        DamageEffect convDmgEffect = (DamageEffect) convertedSkills[0].getEffects().getFirst();
        DamageEffect origDmgEffect = (DamageEffect) skills[0].getEffects().getFirst();
        StatChangeEffect convStatChangeEffect = (StatChangeEffect) convertedSkills[1].getEffects().getFirst();
        StatChangeEffect origStatChangeEffect = (StatChangeEffect) skills[1].getEffects().getFirst();

        assertThat(convDmgEffect.getDamage(), equalTo(origDmgEffect.getDamage()));
        assertThat(convStatChangeEffect.getDefenseChange(), equalTo(origStatChangeEffect.getDefenseChange()));
    }

    @Test
    public void testConvertHitPointStrategy_EntityToDatabaseToEntity_expectMaintainEntityData() {
        // Arrange
        final HitPointStrategy hitPointStrategy = new HitPointStrategy(5, 6);
        final ModelHitPointStrategyConverter modelHitPointStrategyConverter = new ModelHitPointStrategyConverter(new JsonHelper(new Gson()));

        // Act
        String databaseEntry = modelHitPointStrategyConverter.convertToDatabaseColumn(hitPointStrategy);
        HitPointStrategy converted = modelHitPointStrategyConverter.convertToEntityAttribute(databaseEntry);

        // Assert
        // Assert parameters
        assertThat(converted.getAggression(), equalTo(hitPointStrategy.getAggression()));
        assertThat(converted.getCaution(), equalTo(hitPointStrategy.getCaution()));
    }
}
