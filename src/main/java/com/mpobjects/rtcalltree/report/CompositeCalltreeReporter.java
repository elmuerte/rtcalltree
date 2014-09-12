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

import java.util.Collection;
import java.util.List;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 * This reporter simply forwards the report requests to a set of reporters.
 */
public class CompositeCalltreeReporter extends AbstractCalltreeReporter {

	protected Collection<? extends CalltreeReporter> reporters;

	public CompositeCalltreeReporter() {
		super();
		minimumReportDepth = 0;
		minimumReportSize = 0;
	}

	public CompositeCalltreeReporter(Collection<? extends CalltreeReporter> aReporters) {
		this();
		setReporters(aReporters);
	}

	/**
	 * @return the reporters
	 */
	public Collection<? extends CalltreeReporter> getReporters() {
		return reporters;
	}

	@Override
	public void reportEndOfTree(String aThreadName, List<? extends CalltreeEntry> aCallTree) {
		if (reporters == null) {
			return;
		}
		if (!shouldReport(aCallTree)) {
			return;
		}
		for (CalltreeReporter rep : reporters) {
			if (rep == null) {
				continue;
			}
			rep.reportEndOfTree(aThreadName, aCallTree);
		}
	}

	/**
	 * @param aReporters
	 *            the reporters to call
	 */
	public void setReporters(Collection<? extends CalltreeReporter> aReporters) {
		reporters = aReporters;
	}

}
