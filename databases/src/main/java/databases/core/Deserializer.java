package databases.core;

public interface Deserializer<InputType, OutputType> {
    OutputType deserialize(InputType object);
}


