package com.wuw.knn;

/**
 * All rights Reserved, Designed By WUW
 *
 * @Title: TestKnn
 * @Package com.wuw.knn
 * @Description:
 * @author: WUW
 * @date: 2019/12/11 12:32
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestKnn {
    /**
     * 从文件中读取数据
     *
     * @param datas 存储数据的集合对象
     * @param path 数据文件的路径
     */

    public void read(List<List<Double>> datas, String path) {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(new File(path)));
            String reader;

            reader = bReader.readLine();
            while (reader != null) {
                String t[] = reader.split(" ");

                ArrayList<Double> list = new ArrayList<Double>();
                for (int i = 0; i < toString().length(); i++) {
                    list.add(Double.parseDouble(t[i]));
                }
                datas.add(list);
                reader = bReader.readLine();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 程序执行入口
     *
     * @param args
     *
     */
    public static void main(String[] args) {

        TestKnn testCNN=new TestKnn();
        String datafile=new File("").getAbsolutePath()+File.separator+"cqudata\\datafile.txt";
        String testfile=new File("").getAbsolutePath()+File.separator+"cqudata\\testfile.txt";
        List<List<Double>> datas=new ArrayList<List<Double>>();
        List<List<Double>> testDatas=new ArrayList<List<Double>>();
        testCNN.read(datas, datafile);
        testCNN.read(testDatas, testfile);

        KNN knn=new KNN();
        for(int i=0;i<testDatas.size();i++){

            List <Double> test=testDatas.get(i);
            System.out.println("测试元组为：");
            for (int j = 0; j < test.size(); j++) {
                System.out.println(test.get(j)+" ");
            }
            System.out.println("类别为： ");
            System.out.println(Math.round(Float.parseFloat(knn.knn(datas, test, 3))));
        }
    }
}