/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.impl.CalltreeRecorderImpl;

/**
 *
 */
public class CalltreeRecorderFactory {

	protected ThreadLocal<CalltreeRecorder> recorder;

	protected static final CalltreeRecorderFactory INSTANCE = new CalltreeRecorderFactory();

	public CalltreeRecorderFactory() {
		recorder = new ThreadLocal<CalltreeRecorder>() {
			@Override
			protected CalltreeRecorder initialValue() {
				return newCalltreeRecorder();
			}
		};
	}

	public static CalltreeRecorder getRecorder() {
		return INSTANCE.recorder.get();
	}

	/**
	 * @param aMethod
	 * @param aArguments
	 * @see CalltreeRecorder#start(Method, Object[])
	 */
	public static void start(@Nonnull Method aMethod, Object[] aArguments) {
		getRecorder().start(aMethod, aArguments);
	}

	/**
	 * @param aMethod
	 * @see CalltreeRecorder#stop(Method)
	 */
	public static void stop(@Nonnull Method aMethod) {
		getRecorder().stop(aMethod);
	}

	/**
	 * Create a new {@link CalltreeRecorder} instance
	 *
	 * @return a {@link CalltreeRecorder}
	 */
	protected CalltreeRecorder newCalltreeRecorder() {
		CalltreeRecorderImpl recorder = new CalltreeRecorderImpl();
		return recorder;
	}

}
