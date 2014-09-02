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
