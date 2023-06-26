package main.org.example;

import main.org.example.collection.City;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class LocalDateBase {
    private Set<City> mainCollection;
    private Date dateOfLastChange;
    private final Date dateOfInitialization;

    public LocalDateBase() {
        this.mainCollection = new ConcurrentSkipListSet<>();
        dateOfInitialization = new Date();
        dateOfLastChange = new Date();
    }

    public Set<City> getMainCollection() {
        return mainCollection;
    }

    public void setMainCollection(TreeSet<City> mainCollection) {
        this.mainCollection = mainCollection;
    }

    public void add (City city){
        mainCollection.add(city);
    }
    public Date getDateOfLastChange() {
        return dateOfLastChange;
    }

    public Date getDateOfInitialization() {
        return dateOfInitialization;
    }

    public void setDateOfLastChange(Date date){
        this.dateOfLastChange = new Date();
    }
    public City getId(Integer key) {
        return mainCollection
                .stream()
                .filter(city -> city.getId().equals(key))
                .findAny()
                .orElse(null);
    }
    public void clear(String username) {
        mainCollection
                .stream()
                .filter(sg -> sg != null && sg.getAuthor().equals(username))
                .forEach(sg -> mainCollection.remove(sg));
    }
}
