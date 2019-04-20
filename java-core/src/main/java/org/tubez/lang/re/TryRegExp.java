package org.tubez.lang.re;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 
<h2>查询：</h2>

String str="abc efg ABC";   
String regEx="a|f";   //表示a或f    
Pattern p=Pattern.compile(regEx);   
Matcher m=p.matcher(str);   
boolean rs=m.find();  

boolean rs=m.find(3); //从指定的字符（第三个字符）开始查找匹配

如果str中有regEx，那么rs为true，否则为flase。如果想在查找时忽略大小写，则可以写成：

Pattern p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);

String类提供的matches()方法也支持正则表达式，返回boolean
"-1234".matches("-?\\d+")


<h2>提取：</h2>

Pattern p = Pattern.compile("^lang(.+?)\n",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
Matcher m = p.matcher("lang has regex\nJava has regex\n"
	+ "JAVA has pretty good regular expressions\n"
	+ "Regular expressions are in Java");
while (m.find()) { 
	for (int j = 0; j <= m.groupCount(); j++) { 
		System.out.print("[" + m.group(j) + "]"); //此处可以通过start和end方法取得匹配的位置
	} 
	System.out.println(); 
}

 m.group(i)是指正则表达式中的第i个括号中的内容，m.groupCount()是括号的个数;


<h2>分割：</h2>

String regEx="::";   
Pattern p=Pattern.compile(regEx);   
String[] r=p.split("xd::abc::cde");  

执行后，r就是{"xd","abc","cde"}，其实分割时还有更简单的方法：

String str="xd::abc::cde";
String[] r=str.split("::");


<h2>替换（删除）：</h2>

String regEx="a+"; //表示一个或多个a   
Pattern p=Pattern.compile(regEx);   
Matcher m=p.matcher("aaabbced a ccdeaa");   
String s=m.replaceAll("A");  

结果为"Abbced A ccdeA" 。如果写成空串，既可达到删除的功能，比如：

String s=m.replaceAll("");
结果为"bbced  ccde"

String类提供replaceAll()和replaceFirst()方法也支持正则表达式：
String ss = s.replaceFirst("a+", "A"); 
String ss = s.replaceAll("shrubbery|tree|herring", "banana");


<h2>附：</h2>

\d 等於 [0-9] 数字 
\D 等於 [^0-9] 非数字 
\s 等於 [ \t\n\x0B\f\r] 空白字元 
\S 等於 [^ \t\n\x0B\f\r] 非空白字元 
\w 等於 [a-zA-Z_0-9] 数字或是英文字 
\W 等於 [^a-zA-Z_0-9] 非数字与英文字 

^ 表示每行的开头
$ 表示每行的结尾
 * 
 * @author guanjianghuai
 *
 */
public class TryRegExp {

    public static String knights = "Then, when you have found the shrubbery, "
            + "you must cut down the mightiest tree in the forest... "
            + "with... a herring!"; 


    @Test
    public void useMatch() { 
        assertTrue("-1234".matches("-?\\d+")); 
        assertTrue("5678".matches("-?\\d+")); 
        assertFalse("+911".matches("-?\\d+")); 
        assertTrue("+911".matches("(-|\\+)?\\d+")); 
    } 
    
    @Test
    public void useSplit() {
        // Non-word characters 
        assertEquals("[Then, when, you, have, found, the, shrubbery, you, must, cut, down, the, mightiest, tree, in, the, forest, with, a, herring]",
        		Arrays.toString(knights.split("\\W+")));  
    }

    @Test
    public void useReplace() { 
        String s = knights;

        assertEquals("Then, when you have located the shrubbery, you must cut down the mightiest tree in the forest... with... a herring!",
        		s.replaceFirst("f\\w+", "located")); 
        assertEquals("Then, when you have found the banana, you must cut down the mightiest banana in the forest... with... a banana!",
        		s.replaceAll("shrubbery|tree|herring", "banana")); 
    } 

    @Test
    public void useFind() { 
    	List<String> result = new ArrayList<String>();
    	
        Matcher m = Pattern.compile("\\w+").matcher(
                "Evening is full of the linnet¡¯s wings"); 
        while (m.find()) { 
        	result.add(m.group());
        } 
        assertEquals(8, result.size());
        assertEquals("[Evening, is, full, of, the, linnet, s, wings]", 
        		result.toString());

        if (m.find(3)) { 
        	assertEquals("ning", m.group()); 
        } 
    } 

    @Test
    public void useGroup() { 
        Matcher m = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$").matcher(
                knights);
        String[] result = new String[5];

        while (m.find()) {
        	assertEquals(4,m.groupCount());
        	
            for (int j = 0; j <= m.groupCount(); j++) { 
            	result[j] = m.group(j);
            }
            assertEquals("[with... a herring!, with..., a herring!, a, herring!]", 
            		Arrays.toString(result)); 
        } 
    } 

    @Test
    public void useReFlags() {
    	List<String> result = new ArrayList<String>();
    	
        Pattern p = Pattern.compile("^java", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); 
        Matcher m = p.matcher( 
                "lang has regex\nJava has regex\n"
                        + "JAVA has pretty good regular expressions\n"
                        + "Regular expressions are in Java"); 

        while (m.find()) {
        	result.add(m.group());
        }
        assertEquals("[lang, Java, JAVA]", result.toString());
    } 

    @Test
    public void useSplit2() { 
        String input = "This!!unusual use!!of exclamation!!points"; 

        assertEquals("[This, unusual use, of exclamation, points]",
        		Arrays.toString(Pattern.compile("!!").split(input))); 
        // Only do the first three: 
        assertEquals("[This, unusual use, of exclamation!!points]",
                Arrays.toString(Pattern.compile("!!").split(input, 3))); 
    } 

    @Test
    public void useReset() throws Exception {
    	List<String> result = new ArrayList<String>();
        Matcher m = Pattern.compile("[frb][aiu][gx]").matcher(
                "fix the rug with bags"); 

        while (m.find()) { 
        	result.add(m.group());
        }
        assertEquals("[fix, rug, bag]",result.toString());

        result.clear();
        m.reset("fix the rig with rags"); 
        while (m.find()) { 
        	result.add(m.group());
        } 
        assertEquals("[fix, rig, rag]",result.toString());
    }

    @Test
    public void useScanner() {
    	List<Integer> result = new ArrayList<Integer>();
        Scanner scanner = new Scanner("12 , 42, 78 , 99, 42"); 

        scanner.useDelimiter("\\s*,\\s*"); 
        while (scanner.hasNextInt()) {
        	result.add( scanner.nextInt());
        }
        scanner.close();
        assertEquals("[12, 42, 78, 99, 42]",result.toString());
    }

    @Test
    public void useScannerWithReg() { 
    	List<String> result = new ArrayList<String>();
        String threatData = "58.27.82.161@02/10/2005\n"
                + "204.45.234.40@02/11/2005\n" + "58.27.82.161@02/11/2005\n"
                + "58.27.82.161@02/12/2005\n" + "58.27.82.161@02/12/2005\n"
                + "[Next log section with different data format]"; 
        
        Scanner scanner = new Scanner(threatData); 
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@"
                + "(\\d{2}/\\d{2}/\\d{4})"; 

        while (scanner.hasNext(pattern)) { 
            scanner.next(pattern); 
            MatchResult match = scanner.match(); 
            String ip = match.group(1); 
            String date = match.group(2); 

            result.add( String.format("Threat on %s from %s", date, ip) ); 
        }
        assertEquals("Threat on 02/10/2005 from 58.27.82.161",result.get(0));
        assertEquals("Threat on 02/11/2005 from 204.45.234.40",result.get(1));
        assertEquals("Threat on 02/11/2005 from 58.27.82.161",result.get(2));
        assertEquals("Threat on 02/12/2005 from 58.27.82.161",result.get(3));
        assertEquals("Threat on 02/12/2005 from 58.27.82.161",result.get(4));
    }

    @Test
    public void useToken() {
    	List<String> result = new ArrayList<String>();
        String input = "But I¡¯m not dead yet! I feel happy!"; 
        StringTokenizer stoke = new StringTokenizer(input); 

        while (stoke.hasMoreElements()) {
        	result.add(stoke.nextToken());
        } 
        assertEquals("[But, I¡¯m, not, dead, yet!, I, feel, happy!]",
        		result.toString());
        assertEquals("[But, I¡¯m, not, dead, yet!, I, feel, happy!]",
        		Arrays.toString(input.split(" "))); 
        Scanner scanner = new Scanner(input); 

        result.clear();
        while (scanner.hasNext()) {
        	result.add(scanner.next());
        } 
        assertEquals("[But, I¡¯m, not, dead, yet!, I, feel, happy!]", 
        		result.toString());
    }

}
