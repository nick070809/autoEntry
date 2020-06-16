package org.nixk;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.io.File;
import java.util.List;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/26
 */

//不是直接运行的 ，
public class FileTextStreamWordCount {
    public static void main(String[] args) throws Exception {

        try {
            final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.enableCheckpointing(5000); // 非常关键，一定要设置启动检查点！！
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
            String dirpath = "";
            String dirpath2 ="";
            File dir = new File(dirpath);
            File dir2 = new File(dirpath2);
            DataStream unionDataStream = env.readTextFile("/Users/xianguang/IdeaProjects/nick070809/autoEntry/base-demo/src/main/java/org/nixk/App.java");

            List<File> files = FileUtil4Fink.filterHidden(dirpath ,FileUtil4Fink.showListFile(dir));
            for(File file :files){
                if (FileUtil4Fink.isTxt(FileUtil4Fink.getSuffix(file.getName()))) {
                    unionDataStream = unionDataStream.union(env.readTextFile(file.getAbsolutePath()));
                }
            }

            List<File> files2 = FileUtil4Fink.filterHidden(dirpath2 ,FileUtil4Fink.showListFile(dir2));
            for(File file :files2){
                if (FileUtil4Fink.isTxt(FileUtil4Fink.getSuffix(file.getName()))) {
                    unionDataStream = unionDataStream.union(env.readTextFile(file.getAbsolutePath()));
                }
            }

            //计数
            SingleOutputStreamOperator<Tuple2<String, Integer>> sum = unionDataStream.flatMap(new LineSplitter())
                    .keyBy(0)
                    .sum(1);

           // sum.print();
            String filepath = System.getProperty("user.home") + File.separator + "filedb" + File.separator + "wordCount.csv";
            sum.writeAsCsv(filepath);

            env.execute("Java WordCount from SocketTextStream Example");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) {
            //String[] tokens = s.toLowerCase().split("\\W+"); // 不区分大小写

            String[] tokens = s.split("\\W+"); // 区分大小写
            for (String token : tokens) {
                if (token.length() > 0) {
                    collector.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}

