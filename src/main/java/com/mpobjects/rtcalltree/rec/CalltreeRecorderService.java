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
package com.mpobjects.rtcalltree.rec;

import javax.annotation.Nonnull;

/**
 * A calltree recorder service. Can be used in a Spring environment together with the
 * {@link NullCalltreeRecorderService} or {@link BasicRecorder}. But it you probably want to use the
 * {@link AspectJRecorder} instead because it is less invasive.
 */
public interface CalltreeRecorderService {

	/**
	 * A token returned by the start methods to be used to end the recording when passing to the stop method. You cannot
	 * use tokens created by one recorder instance to pass it to another instance.
	 */
	public static interface RecordToken {
	}

	/**
	 * Start a calltree record entry
	 *
	 * @param aClass
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull Class<?> aClass, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Start a calltree record entry
	 *
	 * @param aInstance
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull Object aInstance, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Start a calltree record entry
	 *
	 * @param aClassname
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	RecordToken start(@Nonnull String aClassname, @Nonnull String aMethodName, Object... aParameterValues);

	/**
	 * Stop a calltree record. Use the roken previously provided by one of the start methods.
	 *
	 * @param aToken
	 */
	void stop(RecordToken aToken);

}