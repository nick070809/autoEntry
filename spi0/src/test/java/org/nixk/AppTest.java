package org.nixk;

import org.junit.Test;
import org.kx.util.FileUtil;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void spi()  {
        ServiceLoader<QueryInterface> serviceLoader = ServiceLoader.load(QueryInterface.class);//查找spi的实现,未实例化
        Iterator<QueryInterface> searchs = serviceLoader.iterator(); //重新了iterator ，进行实例化
        while(searchs.hasNext()) {
            QueryInterface queryInterface = searchs.next();
            System.out.println(queryInterface.getClass());
            System.out.println(queryInterface.query());
            System.out.println("=======");
        }
    }


    @Test
    public void getSystemProperty(){
        System.out.println( System.getProperty("java.home"));
        System.out.println( System.getProperty("user.dir"));
        System.out.println( System.getProperty("java.vm.version"));
    }


    @Test
    public void parse() throws IOException {
        String sss = FileUtil.readFile("/Users/xianguang/Downloads/down/0513.log");

        String[] ssss  = sss.split(",");
        StringBuilder sbt = new StringBuilder();
        for(String s :ssss){
            String all = s.replaceAll("\\p{C}", "");
            if(all.contains("[K")){
                all = all.replace("[K", "");
            }
            if(all.equals(",")){
                continue;
            }
            if(all.startsWith("--")){
                continue;
            }
            System.out.println(all);
            if(s.contains("\n")){
                sbt.append("\n");
            }
            sbt.append(all).append(",");
         }
        FileUtil.writeStringToFile(sbt.toString(),"/Users/xianguang/Downloads/down/05131.log");
    }


    @Test
    public void getMapList(){
        List<Map<String, String>> mapList = new ArrayList<>();

        mapList.add(new HashMap<String, String>() {{
                put("order_id", "123456789");
                put("age", "20");
            }});
        //第一层括弧实际是定义了一个匿名内部类 (Anonymous Inner Class)，第二层括弧实际上是一个实例初始化块 (instance initializer block)，这个块在内部匿名类构造时被执行。
        mapList.add(new HashMap<String, String>() {{
            put("order_id", "987654321");
            put("age", "21");
        }});
        mapList.add(new HashMap<String, String>() {{
            put("order_id", "5566");
            put("age", "22");
        }});


        StringBuilder sbt = new StringBuilder();

        boolean title = false ;
        for (Map<String, String> map : mapList) {
            if (map == null) {
                continue;
            }
            if(!title){
                Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    if(!title){
                        String keyStr = entry.getKey();
                        sbt.append(entry.getKey() + ",");
                     }
                }
                sbt.append("\n");
                title = true;
            }

            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                sbt.append(entry.getValue() + ",");
            }
            sbt.append("\n");

        }
        System.out.println(sbt.toString());

    }


    @Test
    public void addSecurity(){
        SecurityManager sm=new SecurityManager();

        System.setSecurityManager(sm);

        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead("/Users/xianguang/IdeaProjects/nick070809/autoEntry/quick-star/src/main/java/org/nixk/clazz/loader/NObjectUser.java");
        }
    }

    @Test
    public void addSecurity2(){
        SecurityManager sm=new SecurityManager();

        //System.setSecurityManager(sm);
        String userName = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty("jdbc.drivers");
                //return System.getProperty("user.name");
            }
        });

        System.out.println(userName);
    }


    @Test
    public void chiness(){
        String m ="的";
        System.out.println(m);
    }

}
