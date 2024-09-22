package hexlet.code;

import hexlet.code.schemas.StringSchema;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        var v = new Validator();
        var schema1 = v.string();
        var schema2 = v.number();
        var schema3 = v.map();

        System.out.println(schema1.isValid("String"));
        System.out.println(schema2.isValid(4));
        System.out.println(schema3.isValid(new HashMap<String,String>()));
        System.out.println(schema3.isValid(new HashMap<Integer,String>()));
    }
}
