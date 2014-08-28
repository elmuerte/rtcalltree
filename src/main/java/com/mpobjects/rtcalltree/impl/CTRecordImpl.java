/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.CTRecord;

/**
 *
 */
public class CTRecordImpl implements CTRecord {

	private final Object[] arguments;

	private long deltaTime;

	private final int depth;

	private final Method method;

	private final long startTime;

	public CTRecordImpl(int aDepth, long aStartTime, @Nonnull Method aMethod, Object[] aArguments) {
		deltaTime = -1;
		startTime = aStartTime;
		depth = aDepth;
		method = aMethod;
		arguments = aArguments;
	}

	/**
	 * End the record and set the delta time using the current time.
	 *
	 * @see #endRecord(long)
	 */
	public void endRecord() {
		endRecord(System.nanoTime());
	}

	/**
	 * End the record with the provided end timestamp and computes the delta time. Calling this method multiple times
	 * has no effect.
	 *
	 * @see #endRecord()
	 */
	public void endRecord(long aTimestamp) {
		if (deltaTime > -1) {
			return;
		}
		deltaTime = Math.max(0, aTimestamp - startTime);
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public long getDeltaTime() {
		return deltaTime;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

}
