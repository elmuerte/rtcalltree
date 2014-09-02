/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public abstract class AbstractCalltreeReporter implements CalltreeReporter {

	/**
	 * Minimum depth to be reached before reporting the calltree
	 */
	protected int minimumReportDepth = 1;
	/**
	 * Minimum size of the recorded tree before reporting it.
	 */
	protected int minimumReportSize = 2;

	/**
	 *
	 */
	protected AbstractCalltreeReporter() {
		super();
	}

	/**
	 * @return the minimumReportDepth
	 */
	public int getMinimumReportDepth() {
		return minimumReportDepth;
	}

	/**
	 * @return the minimumReportSize
	 */
	public int getMinimumReportSize() {
		return minimumReportSize;
	}

	/**
	 * @param aMinimumReportDepth
	 *            the minimumReportDepth to set
	 */
	public void setMinimumReportDepth(int aMinimumReportDepth) {
		minimumReportDepth = aMinimumReportDepth;
	}

	/**
	 * @param aMinimumReportSize
	 *            the minimumReportSize to set
	 */
	public void setMinimumReportSize(int aMinimumReportSize) {
		minimumReportSize = aMinimumReportSize;
	}

	/**
	 * Check if the minimum requirements for logging are met.
	 *
	 * @param aCallTree
	 * @return
	 */
	protected boolean shouldReport(@Nonnull List<? extends CalltreeEntry> aCallTree) {
		if (aCallTree.size() < minimumReportSize) {
			return false;
		}
		for (CalltreeEntry entry : aCallTree) {
			if (entry.getDepth() >= minimumReportDepth) {
				return true;
			}
		}
		return false;
	}

}