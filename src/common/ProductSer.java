package common;

import common.Task.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductSer implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 25, Поле может быть null
    private double manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private String orgName;
    private String orgFullName;
    private Long annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле может быть null

    public ProductSer(String name, Coordinates coordinates, LocalDateTime creationDate, int price, String partNumber, double manufactureCost,
                   UnitOfMeasure unitOfMeasure, String orgName, String orgFullName, Long annualTurnover, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.orgName = orgName;
        this.orgFullName = orgFullName;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;

    }
    /**
     * @return ID of the product.
     */
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {return coordinates;}
    public java.time.LocalDateTime getLDT() {return creationDate;}
    public int getPrice() {
        return price;
    }
    public String getPartNumber() {
        return partNumber;
    }
    public double getManufactureCost() {
        return manufactureCost;
    }
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }
    public String getOrgName() {
        return orgName;
    }
    public String getOrgFullName() {
        return orgFullName;
    }

    public Long getAnnualTurnover() {
        return annualTurnover;
    }
    public OrganizationType getType() {
        return type;
    }
    public Address getPostalAddress() {
        return postalAddress;
    }

    @Override
    public String toString() {
        return "ProductSer{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", orgName='" + orgName + '\'' +
                ", orgFullName='" + orgFullName + '\'' +
                ", annualTurnover=" + annualTurnover +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }
}
