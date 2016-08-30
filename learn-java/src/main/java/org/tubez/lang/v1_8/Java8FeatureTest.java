package org.tubez.lang.v1_8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

public class Java8FeatureTest {
	class Person {
		String firstName;
		public Person(String firstName){
			this.firstName = firstName;
		}
		public String getFirstName() {
			return firstName;
		}
	}
	
	/**
	 * 一个所谓的函数式接口必须要有且仅有一个抽象方法声明。
	 * 每个与之对应的lambda表达式必须要与抽象方法的声明相匹配。
	 * 由于默认方法不是抽象的，因此你可以在你的函数式接口里任意添加默认方法。
	 */
	@Test
	public void convert() {
		Converter<String, Integer> c1 = (from) -> Integer.valueOf(from);
		int c1Val = c1.convert("1");
		assertEquals(1, c1Val);
		
		Converter<String, LocalDate> c2 = (f) -> {
			return LocalDate.parse(f);
		};
		
		String ds = "1922-01-01";
		LocalDate d = c2.convert(ds);
		
		assertEquals(ds, d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}
	
	@Test
	public void plus(){
		PlusMethod<Integer> p = (a1, a2) -> a1+a2;
		Assert.assertTrue(3==p.plus(1, 2));
	}

	/**
	 * Predicate是一个布尔类型的函数，该函数只有一个输入参数。
	 * Predicate接口包含了多种默认方法，用于处理复杂的逻辑动词（and, or，negate）
	 */
	@Test
	public void predicate1(){
		Predicate<String> predicate = (s) -> s.length() > 0;
		Assert.assertTrue(predicate.test("foo"));
		Assert.assertFalse(predicate.negate().test("foo"));

		Predicate<Boolean> nonNull = Objects::nonNull;
		Assert.assertTrue(nonNull.test(true));
		
		Predicate<String> isNull = Objects::isNull;
		Assert.assertFalse(isNull.test(""));
	}
	
	/**
	 * Function接口接收一个参数，并返回单一的结果。默认方法可以将多个函数串在一起（compse, andThen）
	 */
	@Test
	public void function1(){
		Function<String, Integer> toInteger = Integer::valueOf;
		
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		assertEquals("123", backToString.apply("123"));
	}
	
	/**
	 * Supplier接口产生一个给定类型的结果。与Function不同的是，Supplier没有输入参数。
	 */
	@Test
	public void supplier1(){
		Supplier<String> s = String::new;
		assertEquals(0, s.get().length());
	}

	/**
	 * Consumer代表了在一个输入参数上需要进行的操作
	 */
	@Test
	public void consumer1(){
		Consumer<Person> greeter  = (p) -> System.out.println("Hello, "+p.getFirstName());
		greeter.accept(new Person("Luke"));
		
		Consumer<String> hello  = (p) -> System.out.println("Hello, "+p);
		hello.accept("Luke");
		
	}
	
	/**
	 * Comparator接口在早期的Java版本中非常著名。Java 8 为这个接口添加了不同的默认方法。
	 */
	@Test
	public void comparator1(){
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		
		Person p1 = new Person("John");
		Person p2 = new Person("Alice");
		
		assertTrue(comparator.compare(p1, p2)> 0);
		Assert.assertTrue(comparator.reversed().compare(p1, p2) < 0);
	}
	
	/**
	 * Optional不是一个函数式接口，而是一个精巧的工具接口，用来防止NullPointerEception产生。
	 */
	@Test
	public void optional1(){
		Optional<String> optional = Optional.of("bam");
		 
		assertTrue(optional.isPresent());           // true
		assertEquals("bam", optional.get());                 // "bam"
		assertEquals("bam", optional.orElse("fallback"));    // "bam"
		 
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
	}
	
	/**
	 * java.util.Stream表示了某一种元素的序列，在这些元素上可以进行各种操作。
	 * Stream操作可以是中间操作，也可以是完结操作。完结操作会返回一个某种类型的值，
	 * 而中间操作会返回流对象本身，并且你可以通过多次调用同一个流操作方法来将操作结果串起来。
	 * 
	 * Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤。
	 * 
	 * ForEach接受一个consumer接口类型的变量，用来执行对每一个元素的操作。
	 * 
	 * sorted只是创建一个流对象排序的视图，而不会改变原来集合中元素的顺序。
	 * 
	 * map是一个对于流对象的中间操作，通过给定的方法，它能够把流对象中的每一个元素对应到另外一个对象上。
	 * 
	 * Match 匹配操作有多种不同的类型，都是用来判断某一种规则是否与流对象相互吻合的。
	 * 
	 * Count是一个终结操作，它的作用是返回一个数值，用来标识当前流对象中包含的元素数量。
	 * 
	 * Reduce 操作是一个终结操作，它能够通过某一个方法，对元素进行削减操作。
	 * 该操作的结果会放在一个Optional变量里返回。
	 */
	@Test
	public void stream1(){
		List<String> strList = Arrays.asList("aaa2", "ddd2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
		strList.stream().filter((s)->s.startsWith("a")).forEach(System.out::println);
		
		//sorted
		System.out.println();
		strList.stream().sorted().filter((s)->s.startsWith("a")).forEach(System.out::println);
		System.out.println();
		strList.stream().filter((s)->s.startsWith("a")).sorted().forEach(System.out::println);
		
		// map
		System.out.println();
		strList.stream().map((s)->s.toUpperCase()).sorted((a,b)->b.compareTo(a)).forEach(System.out::println);
		
		//match
		boolean anyMatch = strList.stream().anyMatch((s)->s.startsWith("a"));
		assertEquals(true, anyMatch);
		
		boolean allMatch = strList.stream().allMatch((s)->s.startsWith("a"));
		assertEquals(false, allMatch);
		
		boolean noneMatch = strList.stream().noneMatch((s)->s.startsWith("a"));
		assertEquals(false, noneMatch);
		
		//count
		long count = strList.stream().filter((s)->s.startsWith("b")).count();
		assertEquals(3, count);
		
		//reduce
		Optional<String> reduce = strList.stream().sorted().reduce((s1,s2)->s1+"#"+s2);
		assertEquals("aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2", reduce.get());
	}
	
	/**
	 * Clock提供了对当前时间和日期的访问功能。Clock是对当前时区敏感的，
	 * 并可用于替代System.currentTimeMillis()方法来获取当前的毫秒时间。
	 * 当前时间线上的时刻可以用Instance类来表示。
	 * Instance也能够用于创建原先的java.util.Date对象。
	 */
	@Test
	public void clock1(){
		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		 
		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);   // legacy Date
		assertEquals(new Date(millis), legacyDate);
	}
	
	/**
	 * 时区类可以用一个ZoneId来表示。
	 * 时区类还定义了一个偏移量，用来在当前时刻或某时间与目标时区时间之间进行转换。
	 */
	@Test
	public void timezone1(){
		// prints all available timezone ids
		System.out.println(ZoneId.getAvailableZoneIds());
		 
		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		assertEquals("ZoneRules[currentStandardOffset=+01:00]", zone1.getRules().toString());
		assertEquals("ZoneRules[currentStandardOffset=-03:00]", zone2.getRules().toString());
	}
	
	/**
	 * LocalTime类表示一个没有指定时区的时间，例如，10 p.m.或者17：30:15
	 */
	@Test
	public void localtime1(){
		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		 
		assertEquals(false, now1.isBefore(now2));
		 
		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
		assertEquals(-2, hoursBetween);
		assertEquals(-179, minutesBetween);
		
		//解析字符串并形成LocalTime对象
		LocalTime late = LocalTime.of(23, 59, 59);
		assertEquals("23:59:59", late.toString());
		
		DateTimeFormatter germanFormatter =
			DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);
		LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
		assertEquals("13:37", leetTime.toString());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void localdate1(){
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		 
		LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
		DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
		assertEquals("FRIDAY", dayOfWeek.toString());
		
		// 解析字符串并形成LocalDate对象
		DateTimeFormatter germanFormatter =
			DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
		assertEquals("2014-12-24", xmas);
	}
	
	/**
	 * 不同于java.text.NumberFormat，新的DateTimeFormatter类是不可变的，也是线程安全的。
	 */
	@Test
	public void localdateTime1(){
		LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
		 
		DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
		assertEquals("WEDNESDAY", dayOfWeek.toString());
		 
		Month month = sylvester.getMonth();
		assertEquals("DECEMBER", month.toString());
		 
		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		assertEquals(1439, minuteOfDay);    // 1439
		
		// 如果再加上的时区信息，LocalDateTime能够被转换成Instance实例。
		// Instance能够被转换成以前的java.util.Date对象。
		Instant instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();
		Date legacyDate = Date.from(instant);
		assertEquals("Wed Dec 31 23:59:59 GMT+08:00 2014", legacyDate.toString());
        
		//格式化日期-时间对象就和格式化日期对象或者时间对象一样
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			 
		LocalDateTime parsed = LocalDateTime.parse("2027-02-02 21:39:48", formatter);
		String string = formatter.format(parsed);
		assertEquals("2027-02-02 21:39:48", string);
	}
}
