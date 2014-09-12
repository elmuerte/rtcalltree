/*
 * Runtime Call Tree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2014, Michiel Hendriks <michiel.hendriks@mp-objects.com>
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 * Reporter which sends the report to SLF4J using the logger category constructed using the base logger name, and a
 * sanitized thread name.
 */
public class Slf4jReporter extends AbstractCalltreeReporter {

	protected String baseLoggerName;

	protected Logger baseLogger;

	public Slf4jReporter(@Nonnull String aLoggerName) {
		setLoggerName(aLoggerName);
	}

	@Override
	public void reportEndOfTree(String aThreadName, @Nonnull List<? extends CalltreeEntry> aCallTree) {
		if (!baseLogger.isDebugEnabled()) {
			return;
		}
		if (!shouldReport(aCallTree)) {
			return;
		}
		Logger logger = LoggerFactory.getLogger(getLoggerName(aThreadName));
		if (!baseLogger.isDebugEnabled()) {
			return;
		}
		logger.debug("START Calltree #{}", System.identityHashCode(aCallTree));
		for (CalltreeEntry entry : aCallTree) {
			logger.debug("{}", entry);
		}
		logger.debug("END Calltree #{}", System.identityHashCode(aCallTree));
	}

	public void setLoggerName(@Nonnull String aLoggerName) {
		baseLoggerName = aLoggerName;
		baseLogger = LoggerFactory.getLogger(aLoggerName);
	}

	/**
	 * @param aThreadName
	 * @return
	 */
	private String getLoggerName(String aThreadName) {
		return baseLoggerName + "." + aThreadName.replaceAll("[^a-zA-Z0-9_-]+", "_");
	}

}
