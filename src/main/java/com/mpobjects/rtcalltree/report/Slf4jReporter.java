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
public class Slf4jReporter extends AbstractCalltreeReporter {

	protected Logger logger;

	public Slf4jReporter(@Nonnull String aLoggerName) {
		setLoggerName(aLoggerName);
	}

	@Override
	public void reportEndOfTree(@Nonnull List<? extends CalltreeEntry> aCallTree) {
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

}
