/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

/**
 *
 */
public interface MutableCalltreeEntry extends CalltreeEntry {

	void endRecord();

	void endRecord(long aTimestamp);

	void setDepth(int aDepth);
}
