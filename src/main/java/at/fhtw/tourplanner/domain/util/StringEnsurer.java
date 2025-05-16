package at.fhtw.tourplanner.domain.util;

public record StringEnsurer(String value, String name) implements Ensurer<StringEnsurer, String>{

    public StringEnsurer isNotNull(){
        if(value == null)
            throw new IllegalArgumentException(String.format("%s must not be null!", name));
        return this;
    }

    public StringEnsurer isNotEmpty(){
        isNotNull();
        if(value.isEmpty())
            throw new IllegalArgumentException(String.format("%s must not be empty!", name));
        return this;
    }

    public StringEnsurer isNotBlank(){
        isNotNull();
        if(value.isBlank())
            throw new IllegalArgumentException(String.format("%s must not be blank!", name));
        return this;
    }

    public StringEnsurer contains(String partial){
        if (partial == null)
            throw new IllegalArgumentException("Partial must not be null!");

        if(!value.contains(partial)){
            String msg = String.format("%s should contain '%s'", name, partial, value);
            throw new IllegalArgumentException(msg);
        }
        return this;
    }
}
