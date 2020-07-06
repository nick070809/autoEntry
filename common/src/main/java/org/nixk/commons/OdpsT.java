package org.nixk.commons;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/6
 */

public class OdpsT {

    @Test
    public  void generateBatchInsertSql() throws Exception {
        String filePath =  "/Users/xianguang/temp/2559526500090588" ;
        String preSwtt  = "INSERT INTO TABLE    PARTITION(ds='test') values \n";

        StringBuffer lxbuffer = new StringBuffer();
        InputStream is = new FileInputStream(filePath);
        InputStreamReader ireader = new InputStreamReader(is, "UTF-8");
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(ireader);
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            String[] ss = line.split("\t");
            StringBuffer str = new StringBuffer();
            int index = 0 ;
            for(String word :ss){
                if(StringUtils.isNotBlank(word)){
                    index ++ ;
                    String newWord = newWord(index, word );
                    if(StringUtils.isBlank(newWord)){
                        continue;
                    }
                    if(str.length() >0){
                        str.append(",") .append(newWord) ;
                    }else {
                        str.append(newWord);
                    }
                }
            }
            String newLine = "("+str.toString()+")";
            if(lxbuffer.length() >0){
                lxbuffer.append(",\n").append(newLine);
            }else {
                lxbuffer.append(newLine);
            }

            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        System.out.println(preSwtt + lxbuffer.toString() +" ;");
    }


    private  String newWord(int index,String word){
        if(index == 3){
            return  "NULL,\'" +word+"\'";
         }
        if(index == 5){
            return  "\'20190319848101\'" ;
        }
        return  "\'"+word+"\'";
    }


}
