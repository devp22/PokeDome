package edu.gatech.pokedome.pokemon.modelConverters;

import java.util.Optional;

import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

/**
 * Converts the StatGroup model to a string for the database.
 */
@Converter
@RequiredArgsConstructor
public class ModelStatGroupConverter implements AttributeConverter<StatGroup, String> {

    private final JsonHelper jsonHelper;

    @Override
    public String convertToDatabaseColumn(final StatGroup statGroup) {
        return jsonHelper.toJson(statGroup);
    }

    @Override
    public StatGroup convertToEntityAttribute(final String string) {
        final Optional<StatGroup> statGroup = jsonHelper.fromJson(string, StatGroup.class);
        return statGroup.orElse(new StatGroup(-1, -1, -1));
    }
}