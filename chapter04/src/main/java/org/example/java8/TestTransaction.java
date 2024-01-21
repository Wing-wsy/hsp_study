package org.example.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wing
 * @create 2024/1/20
 */
public class TestTransaction {
    List<Transaction> ts = null;

    @Before
    public void before() {
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        ts = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
                );
    }

    //1．找出2011年发生的所有交易， 并按交易额排序（从低到高）
    @Test
    public void test1() {
        ts.stream()
                .filter((t) -> t.getYear() == 2011)
                .sorted((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()))
                .forEach(System.out::println);
    }
    //2．交易员都在哪些不同的城市工作过？。
    @Test
    public void test2() {
        ts.stream()
                .map((t) -> t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }
    //3.查找所有来自剑桥的交易员，并按姓名排序。
    @Test
    public void test3() {
        ts.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                .forEach(System.out::println);
    }
    //4．有没有交易员是在米兰工作的？
    @Test
    public void test4() {
        boolean b = ts.stream()
                .anyMatch((t) -> t.getTrader().getCity().equals("Milan"));
        System.out.println(b);

    }
    //5．打印生活在剑桥的交易员的所有交易额。
    @Test
    public void test5() {
        Optional<Integer> sum = ts.stream()
                .filter((e) -> e.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(sum.get());

    }
    //6．所有交易中，最高的交易额是多少。
    @Test
    public void test6() {
        Optional<Integer> max = ts.stream()
                .map((t) -> t.getValue())
                .max(Integer::compare);
        System.out.println(max.get());

    }
    //7. 找到交易额最小的交易
    @Test
    public void test7() {
        Optional<Transaction> op = ts.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(),t2.getValue()));
        System.out.println(op.get());

    }
}
