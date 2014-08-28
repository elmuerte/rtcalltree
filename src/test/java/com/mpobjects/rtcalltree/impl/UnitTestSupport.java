/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import com.mpobjects.rtcalltree.CalltreeRecord;
import com.mpobjects.rtcalltree.CalltreeRecordManager;

/**
 *
 */
public class UnitTestSupport {

	private UnitTestSupport() {
	}

	/**
	 * Try to reset the status of the calltree record for this thread
	 *
	 * @return
	 */
	public static final boolean resetThreadRecord() {
		CalltreeRecord rec = CalltreeRecordManager.getRecord();
		if (rec instanceof CalltreeRecordImpl) {
			((CalltreeRecordImpl) rec).initRecord();
			return true;
		}
		return false;
	}

}
