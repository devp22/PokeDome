package edu.gatech.pokedome.pokemon.modelConverters;

import java.util.Optional;

import edu.gatech.pokedome.helper.JsonHelper;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

/**
 * Converts the HitPointStrategy model to a string for the database.
 */
@Converter
@RequiredArgsConstructor
public class ModelHitPointStrategyConverter implements AttributeConverter<HitPointStrategy, String> {

    private final JsonHelper jsonHelper;

    @Override
    public String convertToDatabaseColumn(final HitPointStrategy statGroup) {
        return jsonHelper.toJson(statGroup);
    }

    @Override
    public HitPointStrategy convertToEntityAttribute(final String string) {
        final Optional<HitPointStrategy> hitPointStrategy = jsonHelper.fromJson(string, HitPointStrategy.class);
        return hitPointStrategy.orElse(new HitPointStrategy(5, 5));
    }
}