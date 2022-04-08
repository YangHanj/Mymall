package iee.yh;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author yanghan
 * @date 2022/4/7
 */
public class Demo {
    @Test
    public void demo01(){
        int[] arr = {4,5,9,2,3,6,8,7};
        quickSort(arr,0,arr.length-1);
        System.out.println(arr);
    }
    public void quickSort(int[] arr,int start,int end){
        int mid = start;
        int p = start;
        int q = end;
        if (start >= end)
            return;
        while (p < q){
            while (arr[q] > arr[mid]) {
                q--;
            }
            swap(arr, q, mid);
            mid = q;
            while (arr[p] < arr[mid]){
                p++;
            }
            swap(arr,p,mid);
            mid = p;
        }
        quickSort(arr,start,mid-1);
        quickSort(arr,mid+1,end);
    }
    public void swap(int[] arr,int index1,int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
