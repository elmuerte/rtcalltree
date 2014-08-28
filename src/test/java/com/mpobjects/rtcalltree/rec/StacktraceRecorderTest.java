/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.rec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.impl.UnitTestSupport;

/**
 *
 */
public class StacktraceRecorderTest {

	@Before
	public void setUp() throws Exception {
		UnitTestSupport.resetThreadRecord();
	}

	@Test
	public void testCalltree() {
		StacktraceRecorder.start();
		try {
			ctHelperMethod();
		} finally {
			StacktraceRecorder.stop();
		}

		// TODO: validate the results
	}

	@Test
	public void testRecordCreation() {
		MutableCalltreeEntry record = stackHelperMethod();
		Assert.assertEquals("testRecordCreation", record.getMethodName());
		Assert.assertEquals("com.mpobjects.rtcalltree.rec.StacktraceRecorderTest", record.getClassName());
	}

	private void ctHelperMethod() {
		StacktraceRecorder.start();
		try {
			for (int i = 0; i < 5; ++i) {
				ctHelperMethodTwo(i);
			}
		} finally {
			StacktraceRecorder.stop();
		}

	}

	private void ctHelperMethodThree(String aString) {
		StacktraceRecorder.start();
		try {
			if (aString.indexOf("4") > -1) {
				ctHelperMethodTwo(5);
			}
		} finally {
			StacktraceRecorder.stop();
		}
	}

	private void ctHelperMethodTwo(int aIndex) {
		StacktraceRecorder.start();
		try {
			if (aIndex % 2 == 0) {
				ctHelperMethodThree("even number: " + aIndex);
			} else if (aIndex % 3 == 0) {
				ctHelperMethodThree("multiple of 3: " + aIndex);
			}
		} finally {
			StacktraceRecorder.stop();
		}
	}

	private MutableCalltreeEntry stackHelperMethod() {
		/*
		 * This method exists just to add one extra entry to the call stack which would usually be occupied by the
		 * start() method
		 */
		return StacktraceRecorder.createEntry();
	}

}
