package edu.gatech.pokedome.pokemon.modelConverters;

import com.google.gson.*;
import edu.gatech.pokedome.pokemon.effect.Effect;

import java.lang.reflect.Type;

/**
 * Converts the Effect model to a string for the database.
 */
public class EffectSerializer implements JsonSerializer<Effect>, JsonDeserializer<Effect> {
    @Override
    public Effect deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String effectType = jsonObject.get("effectType").getAsString();
        try {
            return jsonDeserializationContext.deserialize(jsonObject.get("effect"), Class.forName(effectType));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Effect effect, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("effectType", effect.getClass().getName());
        jsonObject.add("effect", jsonSerializationContext.serialize(effect));
        return jsonObject;
    }
}
