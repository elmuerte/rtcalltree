/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.conv;

import javax.annotation.CheckForNull;

/**
 * No conversion done, arguments are simply not recorded.
 */
public final class NullArgumentConverter implements ArgumentConverter {

	public static final NullArgumentConverter INSTANCE = new NullArgumentConverter();

	public NullArgumentConverter() {
	}

	@Override
	@CheckForNull
	public Object[] convertArguments(@CheckForNull Object[] aArguments) {
		return null;
	}

}
