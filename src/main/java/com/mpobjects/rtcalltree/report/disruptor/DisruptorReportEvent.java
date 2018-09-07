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

package com.mpobjects.rtcalltree.report.disruptor;

import java.util.List;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public class DisruptorReportEvent {

	protected String threadName;

	protected List<? extends CalltreeEntry> calltree;

	public DisruptorReportEvent() {
	}

	/**
	 * @return the calltree
	 */
	public List<? extends CalltreeEntry> getCalltree() {
		return calltree;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	public void set(String aThreadName, List<? extends CalltreeEntry> aCalltree) {
		threadName = aThreadName;
		calltree = aCalltree;
	}

}