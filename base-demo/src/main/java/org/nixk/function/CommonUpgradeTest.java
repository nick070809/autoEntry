package org.nixk.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/27
 */

public class CommonUpgradeTest {


    @Test
    public void filter() {
        List<String> strArr = Arrays.asList("1", "2", "3", "4");
        strArr.stream().filter(new Mylitter()).forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void map() {
        List<String> strArr = Arrays.asList("1", "2", "3", "4");
        strArr.stream().map(new MyMap()).forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void flatmap() {
        List<String> strArr1 = Arrays.asList("1", "2", "3", "4");
        List<String> strArr2 = Arrays.asList("1a", "2b", "3c", "4d");
        Stream.of(strArr1,strArr2).flatMap(new MyFlatMap()).forEach(System.out :: println);

    }




    public static final class Mylitter implements Predicate {
        @Override
        public boolean test(Object o) {
            return "3".equals(o);
        }
    }

    public static final class MyMap implements Function {

        @Override
        public Object apply(Object o) {
            return "S"+o;
        }
    }

    public static final class MyFlatMap  implements Function {

        @Override
        public Object apply(Object o) {
            //System.out.println(o.getClass());
            List<Object> typeList =     new ArrayList<Object>((Collection<?>) o);

            return   typeList.stream().flatMap(e -> Arrays.stream(e.toString().split("")));
        }
    }

}
