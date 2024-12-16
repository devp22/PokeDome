package edu.gatech.pokedome.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Map;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonHelperTests {
    private static final String TEST_JSON_STRING = "test json string";
    private static final Map<String, String> TEST_MAP_FROM_JSON = Map.of("key", "val");

    @Mock
    Gson gson;

    @InjectMocks
    JsonHelper jsonHelper;

    @Test
    public void testToJson_whenValidInput_thenJsonStringReturned() {
        doReturn(TEST_JSON_STRING).when(gson).toJson(eq(TEST_MAP_FROM_JSON));
        final String jsonString = jsonHelper.toJson(TEST_MAP_FROM_JSON);
        assertThat(jsonString, equalTo(TEST_JSON_STRING));
    }
}
