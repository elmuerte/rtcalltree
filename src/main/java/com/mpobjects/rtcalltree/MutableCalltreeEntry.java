/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import javax.annotation.CheckForNull;

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

	/**
	 * @return
	 */
	@Override
	@CheckForNull
	MutableCalltreeEntry getParent();

	void setDepth(int aDepth);

	/**
	 * @param aParent
	 */
	void setParent(MutableCalltreeEntry aParent);

	/**
	 * Set the start timestamp. Can only be called once, subsequent calls should have no effect.
	 *
	 * @param aTimestamp
	 */
	void setStartTime(long aTimestamp);
}
