/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
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

	private static final ThreadLocal<Stack<RecordToken>> ENTRY_STACK;

	private static final InheritableThreadLocal<StacktraceRecorder> INSTANCE;

	static {
		ENTRY_STACK = new ThreadLocal<Stack<RecordToken>>() {
			@Override
			protected Stack<RecordToken> initialValue() {
				return new Stack<RecordToken>();
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
	 * @return the current instance of the stack trace recorder.
	 */
	public static final StacktraceRecorder getRecorderInstance() {
		return INSTANCE.get();
	}

	public static final void start(Object... aParameterValues) {
		final RecordToken entry = INSTANCE.get().startRecorder(aParameterValues);
		ENTRY_STACK.get().push(entry);
	}

	public static final void stop() {
		final long endTime = System.nanoTime();

		Stack<RecordToken> stack = ENTRY_STACK.get();
		while (!stack.isEmpty()) {
			RecordToken entry = stack.pop();
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
	protected RecordToken startRecorder(Object... aParameterValues) {
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
