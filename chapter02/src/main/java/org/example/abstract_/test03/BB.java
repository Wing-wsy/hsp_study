package org.example.abstract_.test03;

/**
 * @author wing
 * @create 2023/12/13
 */
public class BB extends Template{
    @Override
    public void job(){
        long num = 0;
        for (long i = 0; i < 100000; i++) {
            num *= i;
        }
    }
}
