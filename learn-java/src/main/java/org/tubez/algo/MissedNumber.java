package org.tubez.algo;

import org.junit.Test;

/**
 * Missed Number，找丢失的那个数
上次有朋友在美国去yahoo面试，说一道题把它给整了，题目如下：
说有一数组如 int[]{0,1,2} 这样的一个数组，这个数组的第一个必须从0开始，以次+1列出，该数组内最后一个数是这个数组的长度，因此：
int[]{1,2}, missed number为0
int[]{0,1,2}, missed number为3
int[]{0,2}, missed number为1
我朋友和我说这看上去有点像等差数列，我当时就在MSN里和他说：等差数你个头啊！
然后我朋友说，他用了两个循环嵌套也搞不定
我和他说：两个循环你个头啊
他在MSN上又要和我说什么，我还没等他把它打出来直接就是”你个头啊“回过去了。
大家看，这个找missed number是很好玩的一个东西，如果你想着用什么循环，什么算法，什么数据结构，我一律在这边回”你个头啊“，为什么，这么简单的东西，直接套公式啊，唉。。。
 * @author gjh
 *
 */
public class MissedNumber {
	 
    public int findMissedOne(int[] numArray) {
        int sum = 0;
        int idx = -1;
        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] == 0) {
                idx = i;
            } else {
                sum += numArray[i];
            }
        }
 
        // the total sum of numbers between 1 and arr.length.
        int total = (numArray.length + 1) * numArray.length / 2;
        int missedNumber = total - sum;
        return missedNumber;
 
    }
    
    protected MissedNumber mNum = new MissedNumber();
    
    @Test
    public void testFindMissedOne() {
        int[] testArray = new int[] {0,2};
        int missedNumber = mNum.findMissedOne(testArray);
        System.out.println(missedNumber);
    }
}