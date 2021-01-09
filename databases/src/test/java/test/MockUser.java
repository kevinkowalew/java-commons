package test;

import databases.postgresql.PostgresqlDeserializer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MockUser {
    int id;
    String name;

    public MockUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class Deserializer implements PostgresqlDeserializer<MockUser> {
        @Override
        public MockUser deserialize(ResultSet resultSet) {
            try {
                System.out.println(resultSet.toString());
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                return new MockUser(id, name);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
    }
}