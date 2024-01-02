package org.example.generic.test;

/**
 * @author wing
 * @create 2024/1/2
 */
public class Address implements Comparable<Address>{
    String province;
    public Address(String province) {
        this.province = province;
    }
    @Override
    public int compareTo(Address o) {
        return this.province.compareTo(o.province);
    }
    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                '}';
    }
}
