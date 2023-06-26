package main.org.example.dataBase;

public enum Statements {

    addCity("INSERT INTO s367487Cities "+
            "(id, name, xCoordinate, yCoordinate, area, population, metersAboveSeaLevel, climate, government, "+
            "standardOfLiving, humanBirthday, author) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),

    generateId("SELECT nextval('ids')"),

    addUserWithPassword("INSERT INTO s367487Users (username, hashPassword) VALUES(?, ?)"),

    checkUser("SELECT * FROM s367487Users WHERE username=? AND hashPassword=?"),

    updateCity("UPDATE s367487Cities SET "+
            "name=?, xCoordinate=?, yCoordinate=?, area=?, population=?, metersAboveSeaLevel=?, "+
            "climate=?, government=?, standardOfLiving=?, humanBirthday=? "+
            "WHERE id = ?"),
    getById("SELECT * FROM s367487Cities WHERE id = ?"),

    deleteById("DELETE FROM s367487Cities WHERE id = ?"),

    clearAllByUser("DELETE FROM s367487Cities WHERE author = ?"),



    takeAll("SELECT * FROM s367487Cities");
    private final String statement;

    Statements(String aStatement) {
        statement = aStatement;
    }

    public String getStatement() {
        return statement;
    }

}
