/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

/**
 *
 */
public interface MutableCalltreeEntry extends CalltreeEntry {

	/**
	 * Shorthand for {@link #endRecord(long)} with the current nano-time.
	 */
	void endRecord();

	/**
	 * @param aTimestamp
	 */
	void endRecord(long aTimestamp);

	void setDepth(int aDepth);

	/**
	 * Set the start timestamp. Can only be called once, subsequent calls should have no effect.
	 *
	 * @param aTimestamp
	 */
	void setStartTime(long aTimestamp);
}
