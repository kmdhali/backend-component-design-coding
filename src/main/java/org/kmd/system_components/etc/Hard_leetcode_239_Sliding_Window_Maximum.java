package org.kmd.system_components.etc;

import java.util.Arrays;
import java.util.OptionalInt;

public class Hard_leetcode_239_Sliding_Window_Maximum {


    public static int[] maxSlidingWindow(int[] nums, int k) {

        int [] maxSliding= new int[nums.length-k+1];
        for (int i = 0; i <= nums.length - k; i++) {
            int[] temp=Arrays.copyOfRange(nums, i, i+k);
            OptionalInt max = Arrays.stream(temp).max();
            maxSliding[i]=max.getAsInt();
            max.getAsInt();
        }
        return maxSliding;
    }


    public static void main(String[] args) {
        int[] num= {1,3,-1,-3,5,3,6,7};

        int[] result=maxSlidingWindow(num, 3);
        System.out.println(Arrays.toString(result));

    }

}
