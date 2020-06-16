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

public class Read2From {


    public static void main(String[] args) throws IOException {
        String fromFile ="/Users/xianguang/IdeaProjects/nick070809/autoEntry/a-docs/target/li/test.txt";
        File file = new File(fromFile);
        String  sourceFile =  getSourceFilePath(file);
        String secretContent = FileUtil.readFile(fromFile);
        String content = Cx.show(secretContent );
        FileUtil.writeStringToFile(content,sourceFile);
    }

    public static String getSourceFilePath(File targetFile) {
        String targetPath = targetFile.getAbsolutePath();
        File projectDir = new File(System.getProperty("user.dir"));
        String parentProjectPath =  projectDir.getAbsolutePath();
        if(!parentProjectPath.endsWith(Read2Target.ROORNAME)){
            parentProjectPath = projectDir.getParent() ;
        }
        String fromDirPath = parentProjectPath + File.separator + Read2Target.DOCS  + File.separator + Read2Target.FROM  ;
        String targetDirPath = parentProjectPath + File.separator  + Read2Target.DOCS  + File.separator + Read2Target.TARGET  ;

        if (targetPath.startsWith(targetDirPath)) {
            String detailPath = targetPath.substring(targetDirPath.length());
            return fromDirPath + detailPath;
        }
        return fromDirPath  + File.separator +Read2Target.DEFAULT+ File.separator+ targetFile.getName();
    }



}
