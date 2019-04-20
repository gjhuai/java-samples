package org.tubez.algo;

/**
 * 判断一个字符串是不是一个回文字符串。
 * 
 * 1、去掉干扰字符（非字母和非数字的字符）
 * 2、倒置去掉干扰后的字符串
 * 3、比较正序的字符串和倒序的字符串是否相等
 */
public class Palindrome {

	public static boolean isPalindrome(String stringToTest) {
		String workingCopy = removeJunk(stringToTest);
		String reversedCopy = reverse(workingCopy);

		return reversedCopy.equalsIgnoreCase(workingCopy);
	}

	/**
	 * 去掉字符串中的非字母和非数字的字符，并将字符串倒置(reserve)
	 * @param string
	 * @return
	 */
	private static String removeJunk(String string) {
		int i, len = string.length();
		StringBuilder dest = new StringBuilder(len);
		char c;

		for (i = (len - 1); i >= 0; i--) {
			c = string.charAt(i);
			if (Character.isLetterOrDigit(c)) {
				dest.append(c);
			}
		}

		return dest.toString();
	}

	private static String reverse(String string) {
		StringBuilder sb = new StringBuilder(string);

		return sb.reverse().toString();
	}

	public static void main(String[] args) {
		String string = "Madam, I'm Adam.";

		if (isPalindrome(string)) {
			System.out.printf("<%s> IS a palindrome!", string);
		} else {
			System.out.printf("<%s> is NOT a palindrome!", string);
		}
	}
}