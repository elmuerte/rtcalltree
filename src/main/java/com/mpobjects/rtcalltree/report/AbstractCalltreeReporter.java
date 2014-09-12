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
	 * Enable/disable a reporter completely.
	 */
	protected boolean enabled;

	/**
	 *
	 */
	protected AbstractCalltreeReporter() {
		super();
		enabled = true;
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
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param aEnabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean aEnabled) {
		enabled = aEnabled;
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
		if (!enabled) {
			return false;
		}
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