/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.rec;

import javax.annotation.Nonnull;

/**
 * A calltree recorder service. Can be used in a Spring environment together with the
 * {@link NullCalltreeRecorderService} or {@link BasicRecorder}. But it you probably want to use the
 * {@link AspectJRecorder} instead because it is less invasive.
 */
public interface CalltreeRecorderService {

	/**
	 * A token returned by the start methods to be used to end the recording when passing to the stop method. You cannot
	 * use tokens created by one recorder instance to pass it to another instance.
	 */
	public static interface RecordToken {
	}

	/**
	 * Start a calltree record entry
	 *
	 * @param aClass
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull Class<?> aClass, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Start a calltree record entry
	 *
	 * @param aInstance
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull Object aInstance, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Start a calltree record entry
	 *
	 * @param aClassname
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull String aClassname, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Stop a calltree record. Use the roken previously provided by one of the start methods.
	 *
	 * @param aToken
	 */
	void stop(RecordToken aToken);

}