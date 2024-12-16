package edu.gatech.pokedome.pokemon.modelConverters;

import java.util.Optional;

import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.pokemon.skill.Skill;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

/**
 * Converts the Skill model to a string for the database.
 */
@Converter
@RequiredArgsConstructor
public class ModelSkillConverter implements AttributeConverter<Skill[], String> {

    private final JsonHelper jsonHelper;

    @Override
    public String convertToDatabaseColumn(final Skill[] skills) {
        return jsonHelper.toJson(skills);
    }

    @Override
    public Skill[] convertToEntityAttribute(final String string) {
        final Optional<Skill[]> skillList = jsonHelper.fromJson(string, Skill[].class);
        return skillList.orElse(new Skill[] {});
    }
}