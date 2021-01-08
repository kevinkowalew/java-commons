package databases.core;

import java.util.List;
import java.util.Optional;

public interface Database<Model, ReturnType> {
    Optional<List<Model>> processRequest(Request<Model> request);
}

