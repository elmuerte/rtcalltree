/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.impl.CalltreeRecordImpl;
import com.mpobjects.rtcalltree.report.CalltreeReporter;
import com.mpobjects.rtcalltree.report.NullCalltreeReporter;

/**
 * Manager of {@link CalltreeRecord} instances.
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
