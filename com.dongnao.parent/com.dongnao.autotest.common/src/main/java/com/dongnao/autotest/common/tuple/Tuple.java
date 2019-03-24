package com.dongnao.autotest.common.tuple;

import java.io.Serializable;

/**
 * 元组
 * 
 * @author Administrator
 *
 */
public abstract class Tuple implements Serializable {
	private static final long serialVersionUID = -8719929625763890301L;

	public static <A, B> Tuple newTuple(A a, B b) {
		return new Tuple2<A, B>(a, b);
	}

	public static <A, B, C> Tuple newTuple(A a, B b, C c) {
		return new Tuple3<A, B, C>(a, b, c);
	}

	public static <A, B, C, D> Tuple newTuple(A a, B b, C c, D d) {
		return new Tuple4<A, B, C, D>(a, b, c, d);
	}

	public static <A, B, C, D, E> Tuple newTuple(A a, B b, C c, D d, E e) {
		return new Tuple5<A, B, C, D, E>(a, b, c, d, e);
	}
}
