/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.conv;

/**
 * Convert the method arguments to values which are stored
 */
public interface ArgumentConverter {

	Object[] convertArguments(Object[] aArguments);
}
