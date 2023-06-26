package main.org.example.utility;

import main.org.example.collection.City;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class Response implements Serializable {
    private String information;
    private City city;
    private Set<City> collection;
    private final TypeOfAnswer status;
    private Map<String, String> informationMap;



    public Response(String anInformation, TypeOfAnswer status){
         information = anInformation;
         this.status = status;
    }

    public Response(Set<City> aCollection, TypeOfAnswer status) {
        this.status = status;
        collection = aCollection;
    }

    public Response(City city, TypeOfAnswer status){
        this.city = city;
        this.status = status;
    }
    public Response(TypeOfAnswer status){
        this.status = status;
    }

    public Response(Map<String,String> informationMap, TypeOfAnswer status){
        this.informationMap = informationMap;
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public Set<City> getCollection() {
        return collection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (information != null)
            sb.append(information);

        if (city != null)
            sb.append(city).append("\n");

        if (collection != null)
            collection.stream().sorted(Comparator.comparing(City::getName)).
                    forEach(sg -> sb.append(sg).append("\n"));
        return sb.toString();
    }
    public int getSize() throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        return byteOutputStream.toByteArray().length;
    }
    public TypeOfAnswer getStatus(){
        return status;
    }
    public String getInformation(){

        return information;
    }
    public Map<String, String> getInformationMap() {
        return informationMap;
    }


}
