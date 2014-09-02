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
package com.mpobjects.rtcalltree;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.impl.CalltreeRecordImpl;
import com.mpobjects.rtcalltree.report.CalltreeReporter;
import com.mpobjects.rtcalltree.report.NullCalltreeReporter;

/**
 * Manager of {@link CalltreeRecord} instances.
 */
public class CalltreeRecordManager implements CalltreeRecordProvider {

	private static final CalltreeRecordManager INSTANCE;

	protected CalltreeReporter calltreeReporter;

	protected ThreadLocal<CalltreeRecord> recorder;

	static {
		INSTANCE = new CalltreeRecordManager();
	}

	public CalltreeRecordManager() {
		calltreeReporter = NullCalltreeReporter.INSTANCE;
		recorder = new ThreadLocal<CalltreeRecord>() {
			@Override
			protected CalltreeRecord initialValue() {
				return newCalltreeRecorder();
			}
		};
	}

	public static final CalltreeRecordManager instance() {
		return INSTANCE;
	}

	/**
	 * @return the calltreeReporter
	 */
	public CalltreeReporter getCalltreeReporter() {
		return calltreeReporter;
	}

	@Override
	public CalltreeRecord getRecord() {
		return recorder.get();
	}

	/**
	 * @param aCalltreeReporter
	 *            the calltreeReporter to set
	 */
	public void setCalltreeReporter(@Nonnull CalltreeReporter aCalltreeReporter) {
		calltreeReporter = aCalltreeReporter;
	}

	/**
	 * Create a new {@link CalltreeRecorder} instance
	 *
	 * @return a {@link CalltreeRecorder}
	 */
	protected CalltreeRecord newCalltreeRecorder() {
		CalltreeRecordImpl recorder = new CalltreeRecordImpl();
		recorder.setCalltreeReporter(calltreeReporter);
		return recorder;
	}

}
