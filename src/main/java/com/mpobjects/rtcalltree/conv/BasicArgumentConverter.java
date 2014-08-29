/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.conv;

/**
 * The basic argument converter shows the value of basic types like String, Number, Boolean and Character. For all other
 * classes it will show the classname and the identity hash.
 */
public class BasicArgumentConverter implements ArgumentConverter {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	public BasicArgumentConverter() {
	}

	@Override
	public Object[] convertArguments(Object[] aArguments) {
		if (aArguments == null) {
			return null;
		}
		if (aArguments.length == 0) {
			return EMPTY_ARRAY;
		}
		Object[] res = new Object[aArguments.length];
		for (int i = 0; i < res.length; ++i) {
			res[i] = convert(aArguments[i]);
		}
		return res;
	}

	private Object convert(Object aObject) {
		if (aObject == null) {
			return "<<null>>";
		}
		if (aObject instanceof String || aObject instanceof Number || aObject instanceof Boolean || aObject instanceof Character) {
			return "(" + aObject.getClass().getSimpleName() + ") " + aObject.toString();
		}
		return aObject.getClass().getName() + "#" + System.identityHashCode(aObject);
	}
}
