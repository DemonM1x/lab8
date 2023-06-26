package main.org.example;

import main.org.example.utility.TypeOfAnswer;
import main.org.example.collection.*;
import main.org.example.dataBase.DBWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.Stack;

public class Receiver {
    private final Stack<Integer> usedId;
    private final DBWorker dbWorker;
    private int highUsedId;

    private final LocalDateBase localDateBase;

    public Receiver(LocalDateBase localDateBase, DBWorker dbWorker) {
        this.dbWorker = dbWorker;
        usedId = new Stack<>();
        highUsedId = 0;

        this.localDateBase = localDateBase;
        getCollection();
        System.out.println(getMainCollection().toString());
    }

    private void getCollection() {
        try {
            ResultSet data = dbWorker.getCollection();
            while (data.next()) {
                localDateBase.add(new City(
                        data.getInt(1),
                        data.getString(2),
                        new Coordinates(data.getInt(3), data.getInt(4)),
                        new Date(data.getDate(5).getTime()),
                        data.getFloat(6),
                        data.getLong(7),
                        data.getDouble(8),
                        Climate.valueOf(data.getString(9)),
                        data.getString(10) != null ? Government.valueOf(data.getString(10)) : null,
                        data.getString(11) != null ? StandardOfLiving.valueOf(data.getString(11)) : null,
                        new Human(new Date(data.getDate(12).getTime())),
                        data.getString(13)
                ));

            }
        } catch (SQLException ignored) {

        }
    }

    public Date getDateOfInitialization() {
        return localDateBase.getDateOfInitialization();
    }

    public Set<City> getMainCollection() {
        return localDateBase.getMainCollection();
    }

    public Date getDateOfLastChange() {
        return localDateBase.getDateOfLastChange();
    }

    public void setDateOfLastChange() {
        localDateBase.setDateOfLastChange(new Date());
    }

    public LocalDateBase getLocalDateBase() {
        return localDateBase;
    }


    public TypeOfAnswer add(City city) {
        Integer id = dbWorker.addCity(city);

        if (id != 0) {
            getMainCollection().add(city.setId(id));
            return TypeOfAnswer.SUCCESSFUL;
        } else {
            return TypeOfAnswer.DUPLICATESDETECTED;
        }
    }

    public City getId(Integer key) {
        return getMainCollection().stream().filter(sg -> sg.getId().equals(key)).findAny().orElse(null);
    }

    public City getMax() {
        return getMainCollection().stream().max(City::compareTo).orElse(null);
    }

    public City getMin() {
        return getMainCollection().stream().min(City::compareTo).orElse(null);
    }

    public TypeOfAnswer clearCollection(String userName) {
        TypeOfAnswer status = dbWorker.clear(userName);
        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            localDateBase.clear(userName);
            setDateOfLastChange();
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public synchronized TypeOfAnswer removeGreater(City city, String username) {
        int countRemoved = 0;

        for (City cities : this.getMainCollection()) {
            if (cities.compareTo(city) > 0 && cities.getAuthor().equals(city.getAuthor())) {
                TypeOfAnswer status = dbWorker.removeById(cities.getId(), username);
                if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
                    remove(cities);
                    countRemoved += 1;
                }
            }

        }
        if (countRemoved > 0) {
            setDateOfLastChange();
            return TypeOfAnswer.SUCCESSFUL;
        }
            else return TypeOfAnswer.NOGREATER;
    }

    public synchronized TypeOfAnswer removeLower(City city, String username ) {
        int countRemoved = 0;
        for (City cities : this.getMainCollection()) {
            if (cities.compareTo(city) < 0 && cities.getAuthor().equals(city.getAuthor())) {
                TypeOfAnswer status = dbWorker.removeById(cities.getId(), username);
                if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
                    remove(cities);
                    countRemoved += 1;
                }
            }

        }
        if (countRemoved > 0) {
            setDateOfLastChange();
            return TypeOfAnswer.SUCCESSFUL;
        }
        return TypeOfAnswer.NOLOWER;
    }
    public TypeOfAnswer updateId(City city, int anId) {
        TypeOfAnswer status = dbWorker.updateById(city, anId);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            City city1 = localDateBase.getId(anId);
            getMainCollection().remove(city1);
            city.setId(anId);
            getMainCollection().add(city);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public Set<City> show() {
        if (getMainCollection().size() == 0) return null;
        else return getMainCollection();
    }

    public boolean remove(City city) {
        return getMainCollection().remove(city);
    }

    public TypeOfAnswer removeID(String anUserName, int idValue) {
        TypeOfAnswer status = dbWorker.removeById(idValue, anUserName);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            City city = getId(idValue);
            getMainCollection().remove(city);
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }
    public TypeOfAnswer addIfMax(City city) {
        if (getMax() != null && city.compareTo(getMax()) <= 0)
            return add(city);
        else return TypeOfAnswer.ISNTMAX;
    }
    public TypeOfAnswer addIfMin(City city) {
        if (getMin() != null && city.compareTo(getMin()) >= 0)
            return add(city);
        else return TypeOfAnswer.ISNTMIN;
    }
    public boolean registerUser(String username, String password) {
        return dbWorker.addUser(username, password);
    }

    public boolean loginUser(String anUsername, String aPassword) {
        return dbWorker.loginUser(anUsername, aPassword);
    }
}
