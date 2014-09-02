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

import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.impl.CalltreeEntryImpl;

/**
 * A basic instantiated calltree recorder.
 */
public class BasicRecorder extends AbstractInstantiatedRecorder implements CalltreeRecorderService {

	/**
	 * Calltree record token created by a call to a start method. Should be provided in the stop call to end a record.
	 */
	public static final class RecordTokenImpl implements RecordToken {
		protected MutableCalltreeEntry entry;

		protected RecordTokenImpl(@Nonnull MutableCalltreeEntry aEntry) {
			entry = aEntry;
		}

		public MutableCalltreeEntry getEntry() {
			return entry;
		}
	}

	public BasicRecorder() {
		super();
	}

	/**
	 * Start a calltree record entry
	 *
	 * @param aClass
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	@Override
	public final RecordToken start(@Nonnull Class<?> aClass, @Nonnull String aMethodName, Object... aParameterValues) {
		if (aClass == null) {
			return null;
		}
		return start(aClass.getName(), aMethodName, aParameterValues);
	}

	/**
	 * Start a calltree record entry
	 *
	 * @param aInstance
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	@Override
	public final RecordToken start(@Nonnull Object aInstance, @Nonnull String aMethodName, Object... aParameterValues) {
		if (aInstance == null) {
			return null;
		}
		return start(aInstance.getClass().getName(), aMethodName, aParameterValues);
	}

	/**
	 * Start a calltree record entry
	 *
	 * @param aClassname
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	@Override
	public final RecordToken start(@Nonnull String aClassname, @Nonnull String aMethodName, Object... aParameterValues) {
		return startInternal(createEntry(aClassname, aMethodName, aParameterValues));
	}

	/**
	 * Stop a calltree record. Use the roken previously provided by one of the start methods.
	 *
	 * @param aToken
	 */
	@Override
	public final void stop(@Nonnull RecordToken aToken) {
		if (aToken == null) {
			return;
		}
		if (!(aToken instanceof RecordTokenImpl)) {
			// TODO: report illegal state
			return;
		}
		calltreeRecordProvider.getRecord().stop(((RecordTokenImpl) aToken).getEntry());
	}

	/**
	 * Create the mutable entry
	 *
	 * @param aClassname
	 * @param aMethodName
	 * @param aParameterValues
	 * @return
	 */
	protected CalltreeEntryImpl createEntry(String aClassname, String aMethodName, Object... aParameterValues) {
		CalltreeEntryImpl entry = new CalltreeEntryImpl(aClassname, aMethodName);
		if (argumentConverter != null && aParameterValues != null && aParameterValues.length > 0) {
			entry.setParameterValues(argumentConverter.convertArguments(aParameterValues));
		}
		return entry;
	}

	/**
	 * Called by the start methods to actually log the entry.
	 *
	 * @param aEntry
	 * @return
	 */
	protected final RecordTokenImpl startInternal(@Nonnull final MutableCalltreeEntry aEntry) {
		final RecordTokenImpl token = new RecordTokenImpl(aEntry);
		calltreeRecordProvider.getRecord().start(aEntry);
		return token;
	}
}
