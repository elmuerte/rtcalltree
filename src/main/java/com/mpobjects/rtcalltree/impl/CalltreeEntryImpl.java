/*
 * Runtime Call Tree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2014, Michiel Hendriks <michiel.hendriks@mp-objects.com>
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

	private long startTime;

	private MutableCalltreeEntry parent;

	private long timestamp;

	public CalltreeEntryImpl(@Nonnull String aClassName, @Nonnull String aMethodName) {
		startTime = -1;
		depth = -1;
		deltaTime = -1;
		timestamp = -1;
		className = aClassName;
		methodName = aMethodName;
	}

	/**
	 * End the record and set the delta time using the current time.
	 *
	 * @see #endRecord(long)
	 */
	@Override
	public void endRecord() {
		endRecord(System.nanoTime());
	}

	/**
	 * End the record with the provided end timestamp and computes the delta time. Calling this method multiple times
	 * has no effect.
	 *
	 * @see #endRecord()
	 */
	@Override
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
	};

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
	public MutableCalltreeEntry getParent() {
		return parent;
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
	 * @return the timestamp
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setDepth(int aDepth) {
		depth = aDepth;
	}

	public void setParameterTypes(String[] aParameterTypes) {
		parameterTypes = aParameterTypes;
	}

	public void setParameterValues(Object[] aParameterValues) {
		parameterValues = aParameterValues;
	}

	@Override
	public void setParent(MutableCalltreeEntry aParent) {
		parent = aParent;
	}

	public void setSourceFilename(String aSourceFilename) {
		sourceFilename = aSourceFilename;
	}

	public void setSourceLine(int aSourceLine) {
		sourceLine = aSourceLine;
	}

	@Override
	public void setStartTime(long aStartTime) {
		if (startTime > -1) {
			return;
		}
		startTime = aStartTime;
	}

	@Override
	public void setTimestamp(long aTimestamp) {
		if (timestamp > -1) {
			return;
		}
		timestamp = aTimestamp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; ++i) {
			sb.append('\t');
		}
		sb.append("+ [").append(depth).append("] ");
		sb.append("(").append(deltaTime).append("ns) ");
		sb.append(className).append('.');
		sb.append(methodName);
		return sb.toString();
	}

}
