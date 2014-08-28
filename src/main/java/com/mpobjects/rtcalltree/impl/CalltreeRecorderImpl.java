/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mpobjects.rtcalltree.CalltreeRecorder;
import com.mpobjects.rtcalltree.conv.ArgumentConverter;
import com.mpobjects.rtcalltree.report.CalltreeReporter;

/**
 *
 */
public class CalltreeRecorderImpl implements CalltreeRecorder {

	protected ArgumentConverter argumentConverter;

	protected CalltreeReporter calltreeReporter;

	protected int currentDepth;

	protected CTRecordImpl lastEntry;

	protected List<CTRecordImpl> stack;

	public CalltreeRecorderImpl() {
		initRecorder();
	}

	/**
	 * @return the converter
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
	 * @param aConverter
	 *            the converter to set
	 */
	public void setArgumentConverter(@Nonnull ArgumentConverter aConverter) {
		argumentConverter = aConverter;
	}

	/**
	 * @param aCalltreeReporter
	 *            the calltreeReporter to set
	 */
	public void setCalltreeReporter(CalltreeReporter aCalltreeReporter) {
		calltreeReporter = aCalltreeReporter;
	}

	@Override
	public void start(@Nonnull final Method aMethod, @Nullable Object[] aArguments) {
		if (aMethod == null) {
			return;
		}
		if (aArguments != null && aArguments.length > 0) {
			aArguments = argumentConverter.convertArguments(aArguments);
		}
		stack.add(lastEntry = new CTRecordImpl(currentDepth++, System.nanoTime(), aMethod, aArguments));
	}

	@Override
	public void stop(@Nonnull final Method aMethod) {
		if (aMethod == null) {
			return;
		}

		final long endTime = System.nanoTime();
		if (lastEntry != null) {
			if (!lastEntry.getMethod().equals(aMethod)) {
				// TODO: make sure the stop is called on the proper entry
			} else {
				lastEntry.endRecord(endTime);
				--currentDepth;
			}
		}
		if (currentDepth == -1) {
			callTreeEnded();
		} else if (currentDepth < -1) {
			corruptCallTree();
		}

	}

	/**
	 * The call tree has ended properly
	 */
	protected void callTreeEnded() {
		// TODO Auto-generated method stub

	}

	/**
	 * The call tree became corrupt
	 */
	protected void corruptCallTree() {
		// TODO: send to reporter
		initRecorder();
	}

	/**
	 * (re)initialize the recorder
	 */
	protected void initRecorder() {
		stack = new ArrayList<CTRecordImpl>();
		lastEntry = null;
		currentDepth = 0;
	}
}
