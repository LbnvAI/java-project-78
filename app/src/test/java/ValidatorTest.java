import hexlet.code.Validator;
import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    @Test
    public void stringSchemaTest() {
        var v = new Validator();
        // NO REQUIRED
        var schema1 = v.string();
        assertTrue(schema1.isValid(""));
        assertTrue(schema1.isValid(null));
        assertTrue(schema1.isValid("abcde"));
        // test minLength
        schema1.minLength(2);
        assertFalse(schema1.isValid(""));
        assertFalse(schema1.isValid(null));
        assertFalse(schema1.isValid("a"));
        assertTrue(schema1.isValid("abcde"));
        // test contains
        var schema2 = v.string().contains("");
        assertTrue(schema2.isValid(""));
        assertFalse(schema2.isValid(null));
        schema2.contains("aaa");
        assertTrue(schema2.isValid("aabbbaaaaabbbaaa"));
        assertFalse(schema2.isValid("bbbbb"));
        // test all checks
        var schema3 = v.string().minLength(3).contains("aa");
        assertTrue(schema3.isValid("babaabab"));
        assertFalse(schema3.isValid("babababab"));
        assertFalse(schema3.isValid(""));
        assertFalse(schema3.isValid(null));
        // REQUIRED
        var schema4 = v.string().required();
        assertFalse(schema4.isValid(null));
        assertFalse(schema4.isValid(""));
        // test all checks
        schema4.minLength(10).contains("anton");
        assertTrue(schema4.isValid("my name is anton"));
        assertFalse(schema4.isValid("anton"));
        schema4.minLength(20);
        assertFalse(schema4.isValid("my name is anton"));
        assertFalse(schema4.isValid(""));
        assertFalse(schema4.isValid(null));
    }

    @Test
    public void numberSchemaTest() {
        var v = new Validator();
        // NO REQUIRED
        var schema1 = v.number();
        assertTrue(schema1.isValid(null));
        assertTrue(schema1.isValid(15));
        assertTrue(schema1.isValid(-15));
        assertTrue(schema1.isValid(0));
        // test positive
        schema1.positive();
        assertFalse(schema1.isValid(0));
        assertFalse(schema1.isValid(-15));
        assertTrue(schema1.isValid(15));
        assertTrue(schema1.isValid(null));
        // test range
        var schema2 = v.number().range(-10, 20);
        assertTrue(schema2.isValid(0));
        assertTrue(schema2.isValid(-10));
        assertTrue(schema2.isValid(20));
        assertFalse(schema2.isValid(-21));
        assertFalse(schema2.isValid(34));
        // test wrong range
        var schema3 = v.number().range(10, -10);
        assertFalse(schema3.isValid(0));
        assertFalse(schema3.isValid(10));
        assertFalse(schema3.isValid(-10));
        assertFalse(schema3.isValid(-15));
        assertFalse(schema3.isValid(15));
        // test positive range
        var schema4 = v.number().positive().range(-10, 10);
        assertFalse(schema3.isValid(-5));
        assertFalse(schema3.isValid(0));
        assertTrue(schema2.isValid(5));
        assertFalse(schema3.isValid(15));
        // REQUIRED
        var schema5 = v.number().required();
        assertFalse(schema5.isValid(null));
        assertFalse(schema5.positive().isValid(-1));
        assertTrue(schema5.positive().isValid(1));
        // test all checks
        schema5.positive().range(-5, 5);
        assertFalse(schema5.isValid(null));
        assertTrue(schema5.isValid(3));
        assertFalse(schema5.isValid(-3));
        assertFalse(schema5.isValid(0));
        assertFalse(schema5.isValid(7));
    }

    @Test
    public void mapSchemaTest() {
        var v = new Validator();
        var schema1 = v.map();
        var map1 = new HashMap<String, String>();
        var map2 = new HashMap<String, Integer>();
        assertTrue(schema1.isValid(null));
        assertTrue(schema1.isValid(map1));
        assertTrue(schema1.isValid(map2));
        // test sizeof
        schema1.sizeof(2);
        assertFalse(schema1.isValid(null));
        map1.put("a", "a");
        map1.put("aa", "aa");
        assertTrue(schema1.isValid(map1));
        map1.put("aaa", "aaa");
        assertFalse(schema1.isValid(map1));
        // test shape
        schema1.sizeof(3);
        Map<String, BaseSchema<String>> schemas1 = new HashMap<>();
        schemas1.put("a", v.string().required().contains("a").minLength(0));
        schemas1.put("aa", v.string().required().contains("a").minLength(0));
        schema1.shape(schemas1);
        assertTrue(schema1.isValid(map1));
        schemas1.put("aaaa", new StringSchema());
        assertTrue(schema1.isValid(map1));
        // REQUIRED
        Map<String, BaseSchema<String>> schemas2 = new HashMap<>();
        schemas1.put("a", v.string().required().contains("a").minLength(0));
        schemas1.put("aa", v.string().required().contains("a").minLength(0));
        var schema2 = v.map().required().shape(schemas2);
        assertFalse(schema2.isValid(null));
        assertTrue(schema2.isValid(map1));
        assertFalse(v.map().required().sizeof(2).isValid(null));
        assertTrue(v.map().required().sizeof(3).isValid(map1));
    }
}
