package main.org.example.dataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DBInitializer {
    private final Connection dbConnection;
    private final Logger logger;

    public DBInitializer(Connection aDbConnection, Logger logger) {
        dbConnection = aDbConnection;
        this.logger = logger;
    }

    public void initialize() throws SQLException {

        Statement stmt = dbConnection.createStatement();

        stmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s367487Cities ("+
                "id int PRIMARY KEY," +
                "name varchar(255) NOT NULL CHECK(name<>'')," +
                "xCoordinate int," +
                "yCoordinate int NOT NULL," +
                "creationDate date DEFAULT (current_date)," +
                "area float NOT NULL CHECK(area > 0),"+
                "population bigint NOT NULL CHECK(population > 0),"+
                "metersAboveSeaLevel float NOT NULL,"+
                "climate varchar(20) CHECK(climate = 'TROPICAL_SAVANNA' OR "+
                "climate = 'HUMIDSUBTROPICAL' OR climate = 'STEPPE' OR "+
                "climate = 'TUNDRA' OR climate = 'POLAR_ICECAP'),"+
                "government varchar(16) CHECK(government = 'DICTATORSHIP' OR "+
                "government = 'KRITARCHY' OR government = 'PLUTOCRACY'),"+
                "standardOfLiving varchar(16) CHECK(standardOfLiving = 'MEDIUM' OR "+
                "standardOfLiving = 'VERY_LOW' OR standardOfLiving = 'ULTRA_LOW'),"+
                "humanBirthday date NOT NULL,"+
                "author varchar(255))");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s367487Users (" +
                "username varchar(255) PRIMARY KEY," +
                "hashPassword BYTEA NOT NULL" +
                ")");

    }
}
