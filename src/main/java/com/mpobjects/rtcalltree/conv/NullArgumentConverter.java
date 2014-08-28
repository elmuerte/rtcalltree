/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.conv;

/**
 * Nullifies the arguments
 */
public final class NullArgumentConverter implements ArgumentConverter {

	public static final NullArgumentConverter INSTANCE = new NullArgumentConverter();

	public NullArgumentConverter() {
	}

	@Override
	public Object[] convertArguments(Object[] aArguments) {
		return null;
	}

}
