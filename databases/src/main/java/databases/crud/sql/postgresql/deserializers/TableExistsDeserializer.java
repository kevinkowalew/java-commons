package databases.crud.sql.postgresql.deserializers;

import databases.crud.core.Deserializer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableExistsDeserializer implements Deserializer {
    public TableExistsDeserializer() {
    }

    public Object deserialize(Object object) {
        if (!(object instanceof ResultSet)) {
            return false;
        } else {
            try {
                return !((ResultSet)object).next() ? false : ((ResultSet)object).getBoolean("Exists");
            } catch (SQLException var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }
}
