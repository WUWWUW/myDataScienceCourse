package com.wuw.kmeans;

/**
 * All rights Reserved, Designed By WUW
 *
 * @Title: Kmeans
 * @Package com.wuw
 * @Description:
 * @author: WUW
 * @date: 2019/12/11 11:45
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Kmeans {

    public void run(int k, double[][] data, int maxIter) {
        // 0 随机选出k个样本作为初始中心
        Set<Integer> indices = new HashSet<>(k);
        while (indices.size() != k) {
            int index = (int) (Math.random() * (data.length - 1));
            indices.add(index);
        }
        double[][] center = new double[k][];
        int c = 0;
        for (int index : indices) {
            center[c] = data[index];
            c++;
        }
        //1迭代
        //1.1 将样本分类到距离最近的中心
        //1.2 修正中心为当前簇的平均值
        //1.3 若达到最大迭代次数或者中心和上次相比没有变化，则结束
        int i = 0;
        while (true) {
            Object[] rs = classify(center, data);
            int[] labels = (int[]) rs[0];
            int[] count = (int[]) rs[1];
            double[][] newCenter = (double[][]) rs[2];
            //labels,count,newCenter
            //比较新的中心是否和旧的一样
            boolean convergent = true;
            for (int j = 0; j < center.length; j++) {
                for (int m = 0; m < center.length; m++) {
                    if (center[j][m] != newCenter[j][m])
                        convergent = false;
                }
            }
            i++;
            System.out.println("iter " + i);
            if (convergent) {
                printResult(count, newCenter, data.length);
                break;
            }
            center = newCenter;
            if (i >= maxIter) {
                printResult(count, newCenter, data.length);
                break;
            }
        }
    }

    public void printResult(int[] count, double[][] center, int dataLen) {
        for (int i = 0; i < center.length; i++) {
            System.out.println("class " + i + ":" + (count[i] * 1.0 / dataLen) + " " + Arrays.toString(center[i]));
        }
    }

    /**
     * 根据当前中心划分类别
     * 并返回新的中心
     *
     * @param center
     * @param data
     * @return object数组，只包含3个元素，第一个数据的类别标签，第2个个类别的个数，第3个新的簇中心，
     */
    public Object[] classify(double[][] center, double[][] data) {
        int[] labels = new int[data.length];
        int[] count = new int[center.length];
        for (int i = 0; i < data.length; i++) {
            double minDist = distance(center[0], data[i]);
            for (int j = 1; j < center.length; j++) {
                double dist = distance(center[j], data[i]);
                if (dist < minDist) {
                    minDist = dist;
                    labels[i] = j;
                }
            }
        }
        //计算新的中心
        double[][] newCenter = new double[center.length][center[0].length];
        for (int i = 0; i < data.length; i++) {
            count[labels[i]]++;
            for (int j = 0; j < data[0].length; j++) {
                newCenter[labels[i]][j] += data[i][j];
            }
        }
        for (int i = 0; i < newCenter.length; i++) {
            for (int j = 0; j < newCenter[0].length; j++) {
                newCenter[i][j] = newCenter[i][j] / count[i];
            }
        }
        return new Object[]{labels, count, newCenter};
    }


    /**
     * 计算向量之间的欧式距离
     *
     * @param v1
     * @param v2
     * @return
     */
    public double distance(double[] v1, double[] v2) {
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static double[][] loadIrisData() {
        double[][] data = new double[150][];
        BufferedReader reader2 = null;
        try {
            FileReader reader = new FileReader("./data/iris.arff");
            reader2 = new BufferedReader(reader);
            String line = null;
            int count = 0;
            while ((line = reader2.readLine()) != null) {
                String[] strs = line.split(",");
                double[] sample = new double[4];
                for (int i = 0; i < 4; i++) {
                    sample[i] = Double.parseDouble(strs[i]);
                }
                data[count++] = sample;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void main(String[] args) {
        Kmeans kmeans = new Kmeans();
        double[][] data = loadIrisData();
        kmeans.run(3, data, Integer.MAX_VALUE);
        //double[][] data2 = new double[][] {{1,2,1},{2,1,2},{3,3,3},{4,4,4}};
        //kmeans.run(2, data2, Integer.MAX_VALUE);

    }

}
