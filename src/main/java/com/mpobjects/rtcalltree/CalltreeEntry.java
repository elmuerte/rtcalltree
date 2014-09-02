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
 * A recorded entry in the call tree
 */
public interface CalltreeEntry {
	/**
	 * @return the name of the class which was called
	 */
	String getClassName();

	/**
	 * Difference in nanoseconds between start and end. This will be -1 if call has not returned
	 *
	 * @return nanoseconds
	 */
	long getDeltaTime();

	/**
	 * Depth in the tree
	 *
	 * @return
	 */
	int getDepth();

	/**
	 * @return name of the method was called
	 */
	String getMethodName();

	/**
	 * @return types of the arguments of the method or null when not recorded
	 */
	@CheckForNull
	String[] getParameterTypes();

	/**
	 * Returns the converted arguments passed to the method. Passed objects will converted to strings using the
	 * registered converter. Primitives will be stores as their boxed counterparts. So you will not get the actual
	 * arguments
	 *
	 * @return the method arguments, or null when not recorded
	 */
	@CheckForNull
	Object[] getParameterValues();

	@CheckForNull
	CalltreeEntry getParent();

	/**
	 * @return filename when the method is declared, or null if not recorded
	 */
	@CheckForNull
	String getSourceFilename();

	/**
	 * Line number of the method that was called, if recorded.
	 *
	 * @return line number of the method, or -1 if not recorded
	 */
	int getSourceLine();

	/**
	 * The time the entry was created (and the call was started).
	 *
	 * @return nanoseconds
	 */
	long getStartTime();
}
