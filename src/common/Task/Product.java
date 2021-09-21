package common.Task;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Main class for products.
 */
public class Product implements Serializable, Comparable<Product> {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 25, Поле может быть null
    private double manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Organization manufacturer; //Поле не может быть null

    public Product(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, int price, String partNumber, double manufactureCost,
                   UnitOfMeasure unitOfMeasure, Organization manufacturer) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
    }
    /**
     * @return ID of the product.
     */
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getMan() {
        return manufacturer.getId();
    }
    public int compareTo(Product prod) {

        return id.compareTo(prod.getId());
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", manufacturer=" + manufacturer.toString() +
                '}';
    }
}
