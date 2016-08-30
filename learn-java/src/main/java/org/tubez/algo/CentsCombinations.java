package org.tubez.algo;

/**
 * 硬币组合问题
 * 
这道题老套了，即2角，5角，1角硬币，问有多少种组合可得到1块钱（一块钱？一块钱以前能够买奶油雪糕，一块钱以前可以买一个铅笔盒，一块钱以前可以吃到大排面，现在能干吗？）
 * @author gjh
 *
 */


public class CentsCombinations {
    public static void main(String[] args) {
        int i, j, k, total;
        System.out.println("1 cent"+"  2 cents"+"  5 cents");
        for (i = 0; i <= 10; i++)
            for (j = 0; j <= 5; j++)
                for (k = 0; k <= 2; k++) {
                    total = i * 1 + j * 2 + k * 5;
                    if (total > 10)
                        break;
                    if (10 == total)
                        System.out.println("    " + i + ",    " + j + ",    " + k);
                }
 
    }
}
