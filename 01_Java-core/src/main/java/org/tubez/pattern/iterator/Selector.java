package org.tubez.pattern.iterator;

//: innerclasses/Sequence.lang

//Holds a sequence of Objects.

interface Selector {
	boolean end();

	Object current();

	void next();
}