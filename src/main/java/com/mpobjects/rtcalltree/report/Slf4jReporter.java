/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public class Slf4jReporter implements CalltreeReporter {

	/**
	 * Minimum depth to be reached before reporting the calltree
	 */
	protected int minimumReportDepth = 1;

	protected Logger logger;

	public Slf4jReporter(@Nonnull String aLoggerName) {
		setLoggerName(aLoggerName);
	}

	/**
	 * @return the minimumReportDepth
	 */
	public int getMinimumReportDepth() {
		return minimumReportDepth;
	}

	@Override
	public void reportEndOfTree(List<? extends CalltreeEntry> aCallTree) {
		if (!logger.isDebugEnabled()) {
			return;
		}
		if (!shouldReport(aCallTree)) {
			return;
		}
		logger.debug("START Calltree #{}", System.identityHashCode(aCallTree));
		for (CalltreeEntry entry : aCallTree) {
			logger.debug("{}", entry);
		}
		logger.debug("END Calltree #{}", System.identityHashCode(aCallTree));
	}

	public void setLoggerName(@Nonnull String aLoggerName) {
		logger = LoggerFactory.getLogger(aLoggerName);
	}

	/**
	 * @param aMinimumReportDepth
	 *            the minimumReportDepth to set
	 */
	public void setMinimumReportDepth(int aMinimumReportDepth) {
		minimumReportDepth = aMinimumReportDepth;
	}

	/**
	 * Check if the minimum requirements for logging are met.
	 *
	 * @param aCallTree
	 * @return
	 */
	protected boolean shouldReport(List<? extends CalltreeEntry> aCallTree) {
		for (CalltreeEntry entry : aCallTree) {
			if (entry.getDepth() >= minimumReportDepth) {
				return true;
			}
		}
		return false;
	}

}
