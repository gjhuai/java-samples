package org.tubez.algo;

/**
 * 一道题即考了冒泡又考了二分
说有一数组int[]{1,2,2,4,6,8,9,5,3,7,5}，里面可能还有很多，想要知道”两两相加等于10“的这样的数有几对，可以重复如：2+6，6+2
上手就是冒泡

 * @author gjh
 *
 */
public class BubbleAndBinarySearch {

	/*
public Vector<SumPossibleBean> checkSumPossibleBinSearch(
            ArrayList<Integer> intList, int sumResult) {
        Vector<SumPossibleBean> possibleList = new Vector<SumPossibleBean>();
        int size = intList.size();
        Collections.sort(intList);
        for (int i = 0, j = (intList.size() - 1); i < j;) {
            Integer s = intList.get(i);
            Integer e = intList.get(j);
            if ((s + e) == sumResult) {
                SumPossibleBean spBean = new SumPossibleBean();
                spBean.setFirst(intList.get(i).intValue());
                spBean.setSecond(intList.get(j).intValue());
                possibleList.addElement(spBean);
                i++;
                j--;
            } else if ((s + e) > sumResult) {
                j--;
            } else if ((s + e) < sumResult) {
                i++;
            }
        }
        return possibleList;
}
*/
}
