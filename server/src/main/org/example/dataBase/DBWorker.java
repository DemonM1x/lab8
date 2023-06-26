package main.org.example.dataBase;

import main.org.example.collection.City;
import main.org.example.utility.TypeOfAnswer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Logger;

public class DBWorker {
    private final Connection db;
    private final MessageDigest digest;
    private final Logger logger;

    public DBWorker(Connection connection, Logger logger) throws NoSuchAlgorithmException {
        db = connection;
        digest = MessageDigest.getInstance("MD2");
        this.logger = logger;
    }

    public ResultSet getCollection(){
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            logger.info("SQL problem with taking all collection!");
            return null;
        }
    }
    public Integer addCity(City city){
        try {

            System.out.println("add");
            PreparedStatement preparedStatement = db.prepareStatement(Statements.addCity.getStatement());
            Integer newId = setCityToStatement(preparedStatement, city);
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        }catch (SQLException throwables){
            logger.info("SQL problem with adding new element!");
            return 0;
        }
    }

    public TypeOfAnswer updateById(City city, Integer anId){
        try {
            TypeOfAnswer status = getById(anId, city.getAuthor());
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.updateCity.getStatement());
            setUpdatedCityToStatement(preparedStatement, city, anId);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        }catch (SQLException throwables){
            logger.info("SQL problem with updating element !");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }
    public TypeOfAnswer removeById(int anId, String anUsername) {
        try {
            TypeOfAnswer status = getById(anId, anUsername);
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.deleteById.getStatement());
            preparedStatement.setInt(1, anId);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.info("SQL problem with removing element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer clear(String username) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.clearAllByUser.getStatement());
            preparedStatement.setString(1, username);
            System.out.println(username);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.info("SQL problem with removing elements!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }
    public TypeOfAnswer getById(int anId, String anUsername) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = db.prepareStatement(Statements.getById.getStatement());
            preparedStatement.setInt(1, anId);
            ResultSet deletingCity = preparedStatement.executeQuery();

            if (!deletingCity.next())
                return TypeOfAnswer.OBJECTNOTEXIST;

            if (!deletingCity.getString("author").equals(anUsername))
                return TypeOfAnswer.PERMISSIONDENIED;

            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.info("SQL problem with getting element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }
    public boolean addUser(String anUsername, String aPassword) {
        try {
            PreparedStatement insertStatement = db.prepareStatement(Statements.addUserWithPassword.getStatement());
            insertStatement.setString(1, anUsername);
            insertStatement.setBytes(2, getHash(aPassword));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.info("SQL problem with adding user!");
            return false;
        }
    }
    public boolean loginUser(String anUsername, String aPassword) {
        try {
            PreparedStatement checkStatement = db.prepareStatement(Statements.checkUser.getStatement());
            checkStatement.setString(1, anUsername);
            checkStatement.setBytes(2, getHash(aPassword));
            ResultSet user = checkStatement.executeQuery();
            return user.next();
        } catch (SQLException e) {
            logger.info("SQL problem with logging user!");
            return false;
        }
    }
    private Integer generateId() {
        try {
            Statement statement = db.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.generateId.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return null;
        } catch (SQLException throwables) {
            logger.info("SQL problem with generating id!");
            return null;
        }

    }

    private Integer setCityToStatement(PreparedStatement ps, City city) throws SQLException{
        Integer newId = generateId();
        if (newId == null) return null;

        city.setId(newId);
        System.out.println(newId);
        ps.setInt(1, city.getId());
        ps.setString(2, city.getName());
        ps.setInt(3, city.getCoordinates().getX());
        ps.setInt(4, city.getCoordinates().getY());
        ps.setFloat(5, city.getArea());
        ps.setLong(6, city.getPopulation());
        ps.setDouble(7, city.getMetersAboveSeaLevel());
        ps.setString(8, city.getClimate().toString());
        if(city.getGovernment() == null){
            ps.setNull(9, Types.VARCHAR);
        }
        else {
            ps.setString(9, city.getGovernment().toString());
        }
        if (city.getStandardOfLiving() == null){
            ps.setNull(10, Types.VARCHAR);
        }
        else {
            ps.setString(10, city.getStandardOfLiving().toString());
        }

        ps.setDate(11,  new Date(city.getGovernor().getBirthday().getTime()));
        ps.setString(12, city.getAuthor());
        System.out.println(ps);
        return newId;
    }

    private void setUpdatedCityToStatement(PreparedStatement ps, City city, Integer id) throws SQLException{
        city.setId(id);
        ps.setString(1, city.getName());
        ps.setInt(2, city.getCoordinates().getX());
        ps.setInt(3, city.getCoordinates().getY());
        ps.setFloat(4, city.getArea());
        ps.setLong(5, city.getPopulation());
        ps.setDouble(6, city.getMetersAboveSeaLevel());
        ps.setString(7, city.getClimate().toString());
        if(city.getGovernment() == null){
            ps.setNull(8, Types.VARCHAR);
        }
        else {
            ps.setString(8, city.getGovernment().toString());
        }
        if (city.getStandardOfLiving() == null){
            ps.setNull(9, Types.VARCHAR);
        }
        else {
            ps.setString(9, city.getStandardOfLiving().toString());
        }
        ps.setDate(10, (Date) city.getGovernor().getBirthday());
        ps.setInt(11, city.getId());
    }
    private byte[] getHash(String str) {
        return (str == null)
                ? digest.digest("null".getBytes(StandardCharsets.UTF_8))
                : digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }

}