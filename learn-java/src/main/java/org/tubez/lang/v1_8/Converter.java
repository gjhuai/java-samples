package org.tubez.lang.v1_8;

@FunctionalInterface
public interface Converter<F, T> {
	T convert(F from);
}
