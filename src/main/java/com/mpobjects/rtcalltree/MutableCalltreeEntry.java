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

import javax.annotation.CheckForNull;

/**
 *
 */
public interface MutableCalltreeEntry extends CalltreeEntry {

	/**
	 * Shorthand for {@link #endRecord(long)} with the current nano-time.
	 */
	void endRecord();

	/**
	 * @param aTimestamp
	 */
	void endRecord(long aTimestamp);

	/**
	 * @return
	 */
	@Override
	@CheckForNull
	MutableCalltreeEntry getParent();

	void setDepth(int aDepth);

	/**
	 * @param aParent
	 */
	void setParent(MutableCalltreeEntry aParent);

	/**
	 * Set the start timestamp (for duration recording). Can only be called once, subsequent calls should have no
	 * effect.
	 *
	 * @param aStartTime
	 */
	void setStartTime(long aStartTime);

	/**
	 * Set the timestamp (in miliseconds)
	 *
	 * @param aTimestamp
	 */
	void setTimestamp(long aTimestamp);
}
