package src.main.java.com.hspedu;

/**
 * @author wing
 * @create 2024/3/10
 */
public class Car {
    public String brand = "宝马";
    public int price = 10000;
    public String color = "白色";

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                '}';
    }
}
