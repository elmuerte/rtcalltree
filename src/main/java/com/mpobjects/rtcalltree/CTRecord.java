/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

import javax.annotation.CheckForNull;

/**
 * A recorded entry in the call tree
 */
public interface CTRecord {
	/**
	 * Returns the converted arguments passed to the method. Passed objects will converted to strings using the
	 * registered converter. Primitives will be stores as their boxed counterparts. So you will not get the actual
	 * arguments
	 *
	 * @return the method arguments, or null when not recorded
	 */
	@CheckForNull
	Object[] getArguments();

	/**
	 * Difference in nanoseconds between start and end. This will be -1 if call has not returned
	 *
	 * @return nanoseconds
	 */
	long getDeltaTime();

	/**
	 * Depth in the tree
	 *
	 * @return
	 */
	int getDepth();

	/**
	 * Method which was called
	 *
	 * @return the method signature
	 */
	Method getMethod();

	/**
	 * The time the entry was created (and the call was started).
	 *
	 * @return nanoseconds
	 */
	long getStartTime();
}
