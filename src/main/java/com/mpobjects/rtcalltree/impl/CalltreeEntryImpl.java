/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.MutableCalltreeEntry;

/**
 *
 */
public class CalltreeEntryImpl implements MutableCalltreeEntry {

	private String[] parameterTypes;

	private final String className;

	private final String methodName;

	private Object[] parameterValues;

	private String sourceFilename;

	private int sourceLine;

	private long deltaTime;

	private int depth;

	private final long startTime;

	public CalltreeEntryImpl(long aStartTime, @Nonnull String aClassName, @Nonnull String aMethodName) {
		depth = -1;
		deltaTime = -1;
		startTime = aStartTime;
		className = aClassName;
		methodName = aMethodName;
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
	public String getClassName() {
		return className;
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
	public String getMethodName() {
		return methodName;
	}

	@Override
	public String[] getParameterTypes() {
		return parameterTypes;
	}

	@Override
	public Object[] getParameterValues() {
		return parameterValues;
	}

	@Override
	public String getSourceFilename() {
		return sourceFilename;
	}

	@Override
	public int getSourceLine() {
		return sourceLine;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param aDepth
	 *            the depth to set
	 */
	public void setDepth(int aDepth) {
		depth = aDepth;
	}

	public void setParameterTypes(String[] aParameterTypes) {
		parameterTypes = aParameterTypes;
	}

	public void setParameterValues(Object[] aParameterValues) {
		parameterValues = aParameterValues;
	}

	public void setSourceFilename(String aSourceFilename) {
		sourceFilename = aSourceFilename;
	}

	public void setSourceLine(int aSourceLine) {
		sourceLine = aSourceLine;
	}

}
