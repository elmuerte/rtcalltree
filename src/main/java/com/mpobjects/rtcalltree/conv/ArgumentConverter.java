/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.conv;

import javax.annotation.CheckForNull;

/**
 * Convert the method arguments to values which are stored
 */
public interface ArgumentConverter {

	@CheckForNull
	Object[] convertArguments(@CheckForNull Object[] aArguments);
}
