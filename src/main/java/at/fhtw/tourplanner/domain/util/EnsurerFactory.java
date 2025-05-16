package at.fhtw.tourplanner.domain.util;

public class EnsurerFactory {

    public static ObjectEnsurer when(Object value) {
        return new ObjectEnsurer(value, "value");
    }

    public static ObjectEnsurer when(Object value, String name) {
        return new ObjectEnsurer(value, name);
    }

    public static StringEnsurer when(String value){
        return new StringEnsurer(value, "value");
    }

    public static StringEnsurer when(String value, String name){
        return new StringEnsurer(value, name);
    }

    public static IntegerEnsurer when(Integer value){
        return new IntegerEnsurer(value, "value");
    }

    public static IntegerEnsurer when(Integer value, String name){
        return new IntegerEnsurer(value, name);
    }

    public static LongEnsurer when(Long value){
        return new LongEnsurer(value, "value");
    }

    public static LongEnsurer when(Long value, String name){
        return new LongEnsurer(value, name);
    }
}
