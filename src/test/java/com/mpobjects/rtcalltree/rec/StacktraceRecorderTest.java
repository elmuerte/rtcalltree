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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		UnitTestSupport.resetThreadRecord();
	}

	@Test
	public void testRecordCreation() {
		MutableCalltreeEntry record = stackHelperMethod();
		Assert.assertEquals("testRecordCreation", record.getMethodName());
		Assert.assertEquals("com.mpobjects.rtcalltree.rec.StacktraceRecorderTest", record.getClassName());
	}

	private MutableCalltreeEntry stackHelperMethod() {
		/*
		 * This method exists just to add one extra entry to the call stack which would usually be occupied by the
		 * start() method
		 */
		return StacktraceRecorder.createEntry();
	}

}
