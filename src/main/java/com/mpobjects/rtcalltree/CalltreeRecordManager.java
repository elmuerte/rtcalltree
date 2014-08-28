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
public class CalltreeRecordManager {

	protected static final CalltreeRecordManager INSTANCE = new CalltreeRecordManager();

	protected CalltreeReporter calltreeReporter;

	protected ThreadLocal<CalltreeRecord> recorder;

	public CalltreeRecordManager() {
		calltreeReporter = NullCalltreeReporter.INSTANCE;
		recorder = new ThreadLocal<CalltreeRecord>() {
			@Override
			protected CalltreeRecord initialValue() {
				return newCalltreeRecorder();
			}
		};
	}

	public static CalltreeRecord getRecord() {
		return INSTANCE.recorder.get();
	}

	/**
	 * @param aMethod
	 * @param aArguments
	 * @see CalltreeRecorder#start(Method, Object[])
	 */
	public static void start(@Nonnull MutableCalltreeEntry aRecord) {
		getRecord().start(aRecord);
	}

	public static void stop(@Nonnull MutableCalltreeEntry aRecord) {
		getRecord().stop(aRecord);
	}

	/**
	 * @return the calltreeReporter
	 */
	public CalltreeReporter getCalltreeReporter() {
		return calltreeReporter;
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
