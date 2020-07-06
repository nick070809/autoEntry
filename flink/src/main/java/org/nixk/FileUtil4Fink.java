package org.nixk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sunkx on 2017/5/17.
 */
public class FileUtil4Fink {

    /**
     * 返回当前目录所有文件(包含子目录)
     *
     * @param dir
     * @return list
     * @throws Exception
     */
    public static List<File> showListFile(File dir) throws Exception {
        List<File> list = new ArrayList<>();
        //查找参数文件是否存在,只检查第一个入参
        if (!dir.exists()) {

            System.out.println(dir.getAbsolutePath());
           // throw new FilerException("找不到文件");
            return list;
        }
        //如果是目录那么进行递归调用
        if (dir.isDirectory()) {
            if(dir.getName().equals("**")){
                return list;
            }
            //获取目录下的所有文件
            File[] f = dir.listFiles();
            //进行递归调用,最后总会返回一个list
            for (File file : f) {
                list.addAll(showListFile(file));
            }
        } else {//不是目录直接添加进去
            list.add(dir);
        }
        return list;
    }

    /**
     * 是否为文本
     *
     * @return
     */
    public static boolean isTxt(String fileSuffix) {

      //  List<String> strs = Arrays.asList("txt", "json", "md", "sql", "java", "xml", "properties");

        List<String> strs = Arrays.asList("java");

        return strs.stream().filter(str -> str.equalsIgnoreCase(fileSuffix)).findAny().isPresent();
    }


    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    public static boolean filterHidden(String dirpath,File file) {
        String fileName = file.getAbsolutePath().substring(dirpath.length());
        return !fileName.startsWith(".");

    }

    public static List<File> filterHidden(String dirpath,List<File> files) {
        return files.stream().filter(e ->  filterHidden(dirpath,e)).collect(Collectors.toList());
    }


    public static void main(String[] args) throws Exception {
        String dirpath = "/Users/**/IdeaProjects";
        File dir = new File(dirpath);

        List<File> list = FileUtil4Fink.showListFile(dir);
    }

}


