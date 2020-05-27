package org.nixk.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/27
 */

public class CommonTest {


    @Test
    public void filter() {

        List<String> strArr = Arrays.asList("1", "2", "3", "4");

        strArr.stream().filter(str -> {
            return "2".equals(str) ? true : false;
        }).forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void map() {

        List<String> strArr = Arrays.asList("1", "2", "3", "4");

        strArr.stream().map(str -> {
            return "2".equals(str) ? true : false;
        }).forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void mapToInt() {

        List<String> strArr = Arrays.asList("1", "2", "3", "4");

        strArr.stream().mapToInt(str->Integer.parseInt(str)).forEach(str -> {
            System.out.println(str);
        });
    }

    //flatMap：和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中；
    @Test
    public void flatMap() {

        List<String> teamIndia = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> teamAustralia = Arrays.asList("Warner", "Watson", "Smith");
        List<String> teamEngland = Arrays.asList("Alex", "Bell", "Broad");
        List<String> teamNewZeland = Arrays.asList("Kane", "Nathan", "Vettori");
        List<String> teamSouthAfrica = Arrays.asList("AB", "Amla", "Faf");
        List<String> teamWestIndies = Arrays.asList("Sammy", "Gayle", "Narine");
        List<String> teamSriLanka = Arrays.asList("Mahela", "Sanga", "Dilshan");
        List<String> teamPakistan = Arrays.asList("Misbah", "Afridi", "Shehzad");

        List<List<String>> playersInWorldCup2016 = new ArrayList<>();
        playersInWorldCup2016.add(teamIndia);  //这里不是使用的addAll
        playersInWorldCup2016.add(teamAustralia);
        playersInWorldCup2016.add(teamEngland);
        playersInWorldCup2016.add(teamNewZeland);
        playersInWorldCup2016.add(teamSouthAfrica);
        playersInWorldCup2016.add(teamWestIndies);
        playersInWorldCup2016.add(teamSriLanka);
        playersInWorldCup2016.add(teamPakistan);

        // Let's print all players before Java 8
        List<String> listOfAllPlayers = new ArrayList<>();

        for(List<String> team : playersInWorldCup2016){
            for(String name : team){
                listOfAllPlayers.add(name);
            }
        }

        System.out.println("Players playing in world cup 2016");
        System.out.println(listOfAllPlayers);


        // Now let's do this in Java 8 using FlatMap
        List<String> flatMapList = playersInWorldCup2016.stream()
                .flatMap(pList -> pList.stream())
                .collect(Collectors.toList());

        System.out.println("List of all Players using Java 8");
        System.out.println(flatMapList);
    }


}
