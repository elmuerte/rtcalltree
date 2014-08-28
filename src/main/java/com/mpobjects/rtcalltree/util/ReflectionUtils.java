/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.util;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

/**
 *
 */
public final class ReflectionUtils {

	private ReflectionUtils() {
	}

	/**
	 * Return a string array containing the names of the parameters
	 *
	 * @param aMethod
	 * @return
	 */
	public static String[] getParameterTypes(@Nonnull Method aMethod) {
		final Class<?>[] params = aMethod.getParameterTypes();
		final String[] res = new String[params.length];
		for (int i = 0; i < params.length; ++i) {
			res[i] = params[i].getName();
		}
		return res;
	}
}
