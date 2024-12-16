package edu.gatech.pokedome.helper;

import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.Gson;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonHelper {
    private final Gson gson;

    /**
     * Converts Java object to Json.
     * @param src a Java object
     * @return Json String
     */
    public String toJson(final Object src) {
        return gson.toJson(src);
    }

    /**
     * Converts Json to Java object of the given type.
     *
     * @param json Json String
     * @param typeOfT the type of the Java object
     * @return Optional of the Java object
     */
    public <T> Optional<T> fromJson(final String json, @NonNull final Type typeOfT) {
        try {
            final T objectFromGson = gson.fromJson(json, typeOfT);
            return Optional.ofNullable(objectFromGson);
        } catch (final Exception ex) {
            System.out.println(
                "Failed to convert a json " + json + " to type " + typeOfT + ". Returning empty instead.");
        }
        return Optional.empty();
    }
}
