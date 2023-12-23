package org.example.innerclass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2023/12/21
 */
public class Order {

    public static void main(String[] args) {
        Order order = new Order("12345");
        order.addOrderItem("A001",200);
        order.addOrderItem("B002",300);
        order.printOrderItems();
    }
    private String orderId;
    private List<OrderItem> orderItems;
    public Order(String orderId) {
        this.orderId = orderId;
        this.orderItems = new ArrayList<>();
    }
    public void addOrderItem(String productId, int quantity){
        OrderItem item = new OrderItem(productId, quantity);
        orderItems.add(item);
    }
    public void printOrderItems(){
        for (OrderItem item : orderItems) {
            System.out.println(item.productId + ":" + item.getQuantity());
        }
    }
    // 解释说明：这里可以定义为静态内部类，也可以定义为成员内部类
    // 如果这个内部类不会去访问外部类的属性（或者只会访问外部类的静态属性，不会访问非静态的属性），那么定义成静态的最合适
    // 如果这个内部类有使用到外部类的非静态属性，就定义为成员内部类
    // public class OrderItem{
    public static class OrderItem{
        private String productId;
        private int quantity;
        public OrderItem(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
        public String getProductId() {
            return productId;
        }
        public int getQuantity() {
            return quantity;
        }
    }
}
