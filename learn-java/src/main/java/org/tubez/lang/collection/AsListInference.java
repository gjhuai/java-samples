package org.tubez.lang.collection;

//Arrays.asList() makes its best guess about type.
import java.util.*;

class Snow {}
class Powder extends Snow {}
class Light extends Powder {}
class Heavy extends Powder {}
class Crusty extends Snow {}
class Slush extends Snow {}

public class AsListInference {
	public static void main(String[] args) {
		List<Snow> snow1 = Arrays.asList(new Crusty(), new Slush(),
				new Powder());

		// Won't compile: // 子类和父类不兼容
		// List<Snow> snow2 = Arrays.asList(new Light(), new Heavy());
		// Compiler says:
		// found : lang.util.List<Powder>
		// required: lang.util.List<Snow>

		// Collections.addAll() doesn't get confused:
		List<Snow> snow3 = new ArrayList<Snow>();
		Collections.addAll(snow3, new Light(), new Heavy());

		// Give a hint using an explicit type argument specification:
		// 显示类型参数说明
		List<Snow> snow4 = Arrays.<Snow> asList(new Light(), new Heavy());
	}
}