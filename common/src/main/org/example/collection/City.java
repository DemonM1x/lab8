package main.org.example.collection;

import main.org.example.annotation.Complex;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class City implements Comparable<City>, Serializable {
    public City(){
    }
    public City(Integer id, String name, Coordinates coordinates, Date creationDate, Float area, Long population, Double metersAboveSeaLevel,
                Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.creationDate = creationDate;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;

    }
    public City(Integer id, String name, Coordinates coordinates, Float area, Long population, Double metersAboveSeaLevel,
                Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;

    }
    public City(Integer id, String name, Coordinates coordinates, Date creationDate, Float area, Long population, Double metersAboveSeaLevel,
                Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor, String author){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.author = author;
    }

    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    @Complex
    private Coordinates coordinates; //Поле не может быть null

    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Float area; //Значение поля должно быть больше 0

    private Long population; //Значение поля должно быть больше 0, Поле не может быть null

    private Double metersAboveSeaLevel;

    @Complex
    private Climate climate; //Поле не может быть null

    @Complex
    private Government government; //Поле может быть null

    @Complex
    private StandardOfLiving standardOfLiving; //Поле может быть null

    @Complex
    private Human governor; //Поле не может быть null
    private String author;

    public Integer getId() {
        return id;
    }


    public City setId(int parseInt){
        this.id = parseInt;
        return this;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return author;
    }


    public String getName() {
        return name;
    }



    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Date getCreationDate(){
        return creationDate;
    }



    public Float getArea() {
        return area;
    }



    public Long getPopulation() {
        return population;
    }



    public Double getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }



    public Climate getClimate() {
        return climate;
    }



    public Government getGovernment() {
        return government;
    }



    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }



    public Human getGovernor() {
        return governor;
    }

    @Override
    public int compareTo(City o) {
        if (o.getName().compareTo(this.name) != 0) return o.getName().compareTo(this.name);
        if (o.getArea() - this.area != 0) return (int) (o.getArea() * 100 - this.area * 100);
        if (o.getPopulation().compareTo(this.population) != 0) return o.getPopulation().compareTo(this.population);
        if (o.getCoordinates().compareTo(this.coordinates) != 0) return o.getCoordinates().compareTo(this.coordinates);
        if (o.getMetersAboveSeaLevel() - this.metersAboveSeaLevel != 0) return (int) (o.getMetersAboveSeaLevel() * 100  - this.metersAboveSeaLevel * 100);
        return 0;
    }
    public String toString(){
        var output = "id: " + id + "\n" +
                "name: " + name + "\n" +
                "coordinates:\n" +
                " ".repeat(2) + "X: " + coordinates.getX() + "\n" +
                " ".repeat(2) + "Y: " + coordinates.getY() + "\n" +
                "creation date: " + creationDate + "\n" +
                "area: " + area + "\n"+
                "population: " + population + "\n"+
                "metersAboveSeaLevel: " + metersAboveSeaLevel;
        if(climate != null){
            output += "\n" + "climate: " + climate.toString();
        }
        if (government != null){
            output += "\n" + "government: " + government.toString();
        }
        if (standardOfLiving != null){
            output += "\n" + "standardOfLiving: " + standardOfLiving.toString();
        }
        if (governor != null){
            output += "\n" + "governor: \n" +
                    " ".repeat(2) + "birthday: " + governor.getBirthday();
        }
        output += "\n" + "author: " + author +"\n";
        return output;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (!(otherObject instanceof City)) return false;

        City other = (City) otherObject;

        return (this.getName().equals(other.getName())
                && this.getCoordinates().equals(other.getCoordinates())
                && this.getArea().equals(other.getArea())
                && this.getPopulation().equals(other.getPopulation())
                && this.getMetersAboveSeaLevel().equals(other.getMetersAboveSeaLevel())
                && this.getClimate().equals(other.getClimate())
                && this.getGovernment().equals(other.getGovernment())
                && this.getStandardOfLiving().equals(other.getStandardOfLiving())
                && this.getGovernor().equals(other.getGovernor()));
    }

}
