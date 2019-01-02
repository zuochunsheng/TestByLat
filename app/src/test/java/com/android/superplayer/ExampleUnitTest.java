package com.android.superplayer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void checkTest() {
        int[] strs = {1,4,3,6};

        int [][] array= {{1, 2, 8, 9},
                         {2, 4, 9, 12},
                         {4, 7, 10, 13},
                         {6, 8, 11, 15}};

        boolean b = find(array, 8);
        System.out.println(b);
        assertEquals(4, 2 + 2);

    }

    @Test
    public void checkSortTest(){
        int[] data = {8,6,1,4,7,2,9,6,4} ;

        //sort(data);
        partitionArray(data);
        System.out.println(data);
        System.out.println(Arrays.toString(data));


        assertEquals(4, 2 + 2);
    }


    // 替换空格为指定字符
    private String replaceBlankSpace(String strings){

        if(strings!=null && strings.trim()!="") {
            //方法一
            //return strings.replaceAll("\\s", "%20");// " "

            //方法二
            StringBuffer stringBuffer = new StringBuffer(strings);
            for (int i = stringBuffer.length() -1; i >=0 ; i--) {// 从后往前
                char c = stringBuffer.charAt(i);
                String str = String.valueOf(c);

                if(str.equals(" ")){
                    stringBuffer.replace(i ,i+1,"%20");

                }


            }
            return stringBuffer.toString();
           

        }
        return "";
    }


    //java实现在二维数组中查找一个数
    public boolean find(int[][] array,int target ) {
        int rows = array.length;
        int columns = array[0].length;

        boolean found = false ;

        if(array!=null && rows >0 && columns>0){
            int row = 0 ;
            int column = columns-1;
            while (row <rows && column >= 0){

                if (array[row][column] == target){
                    found = true;
                    break;

                }
                else if (array[row][column] > target){
                    --column ;
                }else {
                        ++row ;
                }
            }


        }
        return  found;

    }




    //偶数顺序改变
    private void sort(int[]  data){
        for (int i = 0; i < data.length-1; i++) {
            if( data[i] %2 == 0){ //能被2整除的数都是偶数，反之为奇数

                int j = i + 1;
                while (j < data.length) {
                    if (data[j] % 2 != 0) {
                        int temp = data[i];
                        data[i] = data[j];
                        data[j] = temp;
                        break;
                    }
                    j++;
                }
                //说明后面的全部均为偶数，没必须要往下循环。
                if (j == data.length) {
                    break;
                }


            }

        }


    }

    public void partitionArray(int[] nums) {
        // write your code here;
        int i=0;
        int j=nums.length - 1;
        while(i<j){
            while(nums[i]%2==1){
                i++;
            }
            while(nums[j]%2==0){
                j--;
            }
            if(i<j){
                nums[i]=nums[i]+nums[j];
                nums[j]=nums[i]-nums[j];
                nums[i]=nums[i]-nums[j];
            }

        }
    }




}