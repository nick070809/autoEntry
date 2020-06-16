package org.nixk.commons;

import org.fla.nnd.s1.Cx;
import org.kx.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/28
 */

public class Read2Target {


    public  final  static String FROM = "from";
    public  final  static String TARGET = "to";
    public final  static String DOCS = "a-docs";
    public final  static String DEFAULT = "default";
    public final  static String ROORNAME = "autoEntry";


    public static void main(String[] args) throws IOException {
        String fromFile ="/Users/xianguang/IdeaProjects/nick070809/autoEntry/eweb/src/main/java/org/nixk/bizModules/NbizModule.txt";
        File file = new File(fromFile);
        String targetFile =  getTargetFilePath(file);
        String content = FileUtil.readFile(fromFile);
        String secretContent = Cx.encrypt(content );
        FileUtil.writeStringToFile(secretContent,targetFile);
    }

    public static String getTargetFilePath(File fromFile) {
        String fromPath = fromFile.getAbsolutePath();
        File projectDir = new File(System.getProperty("user.dir"));
        String parentProjectPath =  projectDir.getAbsolutePath();
        if(!parentProjectPath.endsWith(ROORNAME)){
            parentProjectPath = projectDir.getParent() ;
        }
        String fromDirPath = parentProjectPath + File.separator + DOCS  + File.separator + FROM  ;
        String targetDirPath = parentProjectPath + File.separator  + DOCS  + File.separator + TARGET  ;

        if (fromPath.startsWith(fromDirPath)) {
            String detailPath = fromPath.substring(fromDirPath.length());
            return targetDirPath + detailPath;
        }
        return targetDirPath  + File.separator +DEFAULT+ File.separator+ fromFile.getName();
    }





}
