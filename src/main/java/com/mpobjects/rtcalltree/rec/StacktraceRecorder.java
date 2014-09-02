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

import java.util.Stack;

import com.mpobjects.rtcalltree.impl.CalltreeEntryImpl;

/**
 * Produces calltree entries based on the current stacktree. This is very easy recorder, but it does come with with a
 * lot of overhead due to the fact that a stacktrace is created.
 * <p>
 * This recorder is only able to record the following attributes:
 * <ul>
 * <li>Classname
 * <li>Method name
 * <li>Source file (if available)
 * <li>Source line (if available)
 * </ul>
 * <p>
 * Example usage
 *
 * <pre>
 * void myMethod() {
 * 	StacktraceRecorder.start();
 * 	try {
 * 		// Do your work
 * 	} finally {
 * 		StacktraceRecorder.stop();
 * 	}
 * }
 * </pre>
 */
public final class StacktraceRecorder extends BasicRecorder {

	/**
	 * Offset in the stack where {@link #startRecorder(Object...)} should get the information from
	 */
	private static final int STACK_OFFSET = 3;

	private static final ThreadLocal<Stack<RecordTokenImpl>> ENTRY_STACK;

	private static final InheritableThreadLocal<StacktraceRecorder> INSTANCE;

	static {
		ENTRY_STACK = new ThreadLocal<Stack<RecordTokenImpl>>() {
			@Override
			protected Stack<RecordTokenImpl> initialValue() {
				return new Stack<RecordTokenImpl>();
			}
		};

		INSTANCE = new InheritableThreadLocal<StacktraceRecorder>() {
			@Override
			protected StacktraceRecorder initialValue() {
				return new StacktraceRecorder();
			}
		};
	}

	public StacktraceRecorder() {
		super();
	}

	/**
	 * Get the current instance of this recorder. This will allow you to change the configuration of the provider, etc.
	 *
	 * @return the current instance of the {@link StacktraceRecorder}.
	 */
	public static final StacktraceRecorder getRecorderInstance() {
		return INSTANCE.get();
	}

	public static final void start(Object... aParameterValues) {
		final RecordTokenImpl entry = INSTANCE.get().startRecorder(aParameterValues);
		ENTRY_STACK.get().push(entry);
	}

	public static final void stop() {
		final long endTime = System.nanoTime();

		Stack<RecordTokenImpl> stack = ENTRY_STACK.get();
		while (!stack.isEmpty()) {
			RecordTokenImpl entry = stack.pop();
			// TODO: check if current stack matches
			entry.getEntry().endRecord(endTime);
			INSTANCE.get().stop(entry);
			break;
		}
	}

	/**
	 * @param aParameterValues
	 * @return
	 */
	protected RecordTokenImpl startRecorder(Object... aParameterValues) {
		StackTraceElement[] elms = Thread.currentThread().getStackTrace();
		if (elms.length <= STACK_OFFSET) {
			return null;
		}
		CalltreeEntryImpl entry = createEntry(elms[STACK_OFFSET].getClassName(), elms[STACK_OFFSET].getMethodName(), aParameterValues);

		entry.setSourceFilename(elms[STACK_OFFSET].getFileName());
		entry.setSourceLine(elms[STACK_OFFSET].getLineNumber());

		return startInternal(entry);
	}
}
