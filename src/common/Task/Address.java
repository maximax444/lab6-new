package common.Task;

import java.io.Serializable;

public class Address implements Serializable {
    private String zipCode; //Поле может быть null
    private Location town; //Поле может быть null
    public Address(String zipCode, Location town) {
        this.zipCode = zipCode;
        this.town = town;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", town=" + town.toString() +
                '}';
    }
}
