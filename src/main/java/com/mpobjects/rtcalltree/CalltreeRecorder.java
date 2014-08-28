/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 */
public interface CalltreeRecorder {

	/**
	 * Start a call tree record with the provided information. A call to {@link #start(Method, Object[])} should always
	 * be matched with a call to {@link #stop(Method)}
	 *
	 * @param aMethod
	 *            The method that was called
	 * @param aArguments
	 *            Arguments passed to the called method.
	 */
	void start(@Nonnull Method aMethod, @Nullable Object[] aArguments);

	/**
	 * End the current call tree record.
	 *
	 * @param aMethod
	 */
	void stop(@Nonnull Method aMethod);

}