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
import java.util.Set;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public class CompositeCalltreeReporter implements CalltreeReporter {

	protected Set<CalltreeReporter> reporters;

	public CompositeCalltreeReporter() {
	}

	/**
	 * @return the reporters
	 */
	public Set<CalltreeReporter> getReporters() {
		return reporters;
	}

	@Override
	public void reportEndOfTree(String aThreadName, List<? extends CalltreeEntry> aCallTree) {
		if (reporters == null) {
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
	 *            the reporters to set
	 */
	public void setReporters(Set<CalltreeReporter> aReporters) {
		reporters = aReporters;
	}

}