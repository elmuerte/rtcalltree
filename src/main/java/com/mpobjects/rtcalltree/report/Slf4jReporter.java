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

	protected Logger logger;

	public Slf4jReporter(@Nonnull String aLoggerName) {
		setLoggerName(aLoggerName);
	}

	@Override
	public void reportEndOfTree(List<? extends CalltreeEntry> aCallTree) {
		if (!logger.isDebugEnabled()) {
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
