/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.impl.CalltreeRecordImpl;
import com.mpobjects.rtcalltree.report.CalltreeReporter;
import com.mpobjects.rtcalltree.report.NullCalltreeReporter;

/**
 * Manager of {@link CalltreeRecord} instances. The managed is usually called by one of the call calltree recorders.
 */
public class CalltreeRecordManager implements CalltreeRecordProvider {

	private static final CalltreeRecordManager INSTANCE;

	protected CalltreeReporter calltreeReporter;

	protected ThreadLocal<CalltreeRecord> recorder;

	static {
		INSTANCE = new CalltreeRecordManager();
	}

	public CalltreeRecordManager() {
		calltreeReporter = NullCalltreeReporter.INSTANCE;
		recorder = new ThreadLocal<CalltreeRecord>() {
			@Override
			protected CalltreeRecord initialValue() {
				return newCalltreeRecorder();
			}
		};
	}

	public static final CalltreeRecordManager instance() {
		return INSTANCE;
	}

	/**
	 * @param aMethod
	 * @param aArguments
	 * @see CalltreeRecorder#start(Method, Object[])
	 */
	public static void start(@Nonnull MutableCalltreeEntry aRecord) {
		INSTANCE.getRecord().start(aRecord);
	}

	public static void stop(@Nonnull MutableCalltreeEntry aRecord) {
		INSTANCE.getRecord().stop(aRecord);
	}

	/**
	 * @return the calltreeReporter
	 */
	public CalltreeReporter getCalltreeReporter() {
		return calltreeReporter;
	}

	@Override
	public CalltreeRecord getRecord() {
		return recorder.get();
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
	protected CalltreeRecord newCalltreeRecorder() {
		CalltreeRecordImpl recorder = new CalltreeRecordImpl();
		recorder.setCalltreeReporter(calltreeReporter);
		return recorder;
	}

}
