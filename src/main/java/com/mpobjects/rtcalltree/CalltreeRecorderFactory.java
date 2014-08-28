/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.conv.ArgumentConverter;
import com.mpobjects.rtcalltree.conv.NullArgumentConverter;
import com.mpobjects.rtcalltree.impl.CalltreeRecorderImpl;
import com.mpobjects.rtcalltree.report.CalltreeReporter;
import com.mpobjects.rtcalltree.report.NullCalltreeReporter;

/**
 *
 */
public class CalltreeRecorderFactory {

	protected static final CalltreeRecorderFactory INSTANCE = new CalltreeRecorderFactory();

	protected ArgumentConverter argumentConverter;

	protected CalltreeReporter calltreeReporter;

	protected ThreadLocal<CalltreeRecorder> recorder;

	public CalltreeRecorderFactory() {
		argumentConverter = NullArgumentConverter.INSTANCE;
		calltreeReporter = NullCalltreeReporter.INSTANCE;
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
	 * @return the argumentConverter
	 */
	public ArgumentConverter getArgumentConverter() {
		return argumentConverter;
	}

	/**
	 * @return the calltreeReporter
	 */
	public CalltreeReporter getCalltreeReporter() {
		return calltreeReporter;
	}

	/**
	 * @param aArgumentConverter
	 *            the argumentConverter to set
	 */
	public void setArgumentConverter(@Nonnull ArgumentConverter aArgumentConverter) {
		argumentConverter = aArgumentConverter;
	}

	/**
	 * @param aCalltreeReporter
	 *            the calltreeReporter to set
	 */
	public void setCalltreeReporter(@Nonnull CalltreeReporter aCalltreeReporter) {
		calltreeReporter = aCalltreeReporter;
	}

	/**
	 * Create a new {@link CalltreeRecorder} instance
	 *
	 * @return a {@link CalltreeRecorder}
	 */
	protected CalltreeRecorder newCalltreeRecorder() {
		CalltreeRecorderImpl recorder = new CalltreeRecorderImpl();
		recorder.setArgumentConverter(argumentConverter);
		recorder.setCalltreeReporter(calltreeReporter);
		return recorder;
	}

}
