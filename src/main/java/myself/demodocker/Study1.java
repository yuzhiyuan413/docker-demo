package myself.demodocker;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Arrays;

/**
 * @className: Study1
 * @description:
 * @author: YuZhiYuan
 * @date: 2020-05-26 13:25
 **/
public class Study1 {
    public static void main(String[] args) {
        sort();
        Integer [] arr = {2,5,8};
        System.out.println(midFind(arr, 99));
    }

    public static void sort() {
        Integer [] arr = {1,19,2,54,5,3,23};
        for(int i = 0; i < arr.length; i++) {
            for (int j=i+1;j < arr.length;j++) {
                if(arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println(Arrays.asList(arr));
    }

    public static Integer midFind(Integer[] arr, int target) {
        int begin = 0;
        int end = arr.length;
        for (int i = 0; i < arr.length; i++) {
            int mid = (begin + end) / 2;

            if (target == arr[mid]) {
                return target;
            }

            if (target > arr[mid]) {
                if(mid + 1 < arr.length) {
                    begin = mid + 1;
                }
            }

            if (target < arr[mid]) {
                end = mid - 1;

            }
        }
        return 0;
    }

}