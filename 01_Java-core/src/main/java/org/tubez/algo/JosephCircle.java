package org.tubez.algo;

/**
 * 假设有20个人手拉手围成一圈，顺时针开始报号，报到3的人出圈，然后继续往后报，报到3的人出圈，依次把所有报到3的人都踢出圈，最后剩下一人也踢出圈，问先后被踢出圈的那些人原来是圈内的几号？

不难不难，关键记住这个环的算法（有人看到这个”环“字，要笑了，打住，别想歪了，俗人！）
环的算法即闭合算法，永远依次1，2，3，4，5...20...1再2,3,4,5...20这样一直转下去，假设要让20以内的20个数以环型转起来，它的核心算法如下：
int flag=0;       
while(true){
        flag=(flag+1)%n;
}

即”取模运算“，循环继续。

 * @author gjh
 *
 */
public class JosephCircle {

	public static void main(String[] args) {
		JosephCircle jCircle = new JosephCircle();
		jCircle.josephCircle(20, 3);
	}

	/**
	 * 
	 * @param n
	 * @param k
	 */
	public void josephCircle(int n, int k) {
		int flag = 0;
		boolean[] kick = new boolean[n];
		// set kick flag to False;
		for (int i = 0; i < n - 1; i++) {
			kick[flag] = false;
		}
		int counter = 0;
		int accumulate = 0;
		while (true) {
			if (!kick[flag]) {
				accumulate++;
				if (counter == n - 1) {
					System.out.println("kick last person====" + (flag + 1));
					break;
				}
				if (accumulate == k) {
					kick[flag] = true;
					System.out.println("kick person====" + (flag + 1));
					accumulate = 0;
					counter++;
				}
			}
			flag = (flag + 1) % n;
		}

	}

}