/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.rec;

import java.util.Arrays;
import java.util.Stack;

import com.mpobjects.rtcalltree.CalltreeRecordManager;
import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.impl.CalltreeEntryImpl;

/**
 *
 */
public final class StacktraceRecorder {

	/**
	 * Offset in the stack where {@link #createEntry()} should get the information from
	 */
	private static final int STACK_OFFSET = 3;

	private static final ThreadLocal<Stack<MutableCalltreeEntry>> ENTRY_STACK;

	static {
		ENTRY_STACK = new ThreadLocal<Stack<MutableCalltreeEntry>>() {
			@Override
			protected Stack<MutableCalltreeEntry> initialValue() {
				return new Stack<MutableCalltreeEntry>();
			}
		};
	}

	private StacktraceRecorder() {
	}

	public static final void start() {
		final MutableCalltreeEntry entry = createEntry();
		if (entry == null) {
			return;
		}
		CalltreeRecordManager.start(entry);
		ENTRY_STACK.get().push(entry);
	}

	public static final void stop() {
		final long endTime = System.nanoTime();

		Stack<MutableCalltreeEntry> stack = ENTRY_STACK.get();
		while (!stack.isEmpty()) {
			MutableCalltreeEntry entry = stack.pop();
			// TODO: check if current stack matches
			entry.endRecord(endTime);
			CalltreeRecordManager.stop(entry);
			break;
		}
	}

	/**
	 * @return
	 */
	static MutableCalltreeEntry createEntry() {
		final long startTime = System.nanoTime();
		StackTraceElement[] elms = Thread.currentThread().getStackTrace();
		System.err.println(Arrays.toString(elms));

		if (elms.length <= STACK_OFFSET) {
			return null;
		}
		CalltreeEntryImpl entry = new CalltreeEntryImpl(startTime, elms[STACK_OFFSET].getClassName(), elms[STACK_OFFSET].getMethodName());
		entry.setSourceFilename(elms[1].getFileName());
		entry.setSourceLine(elms[STACK_OFFSET].getLineNumber());
		return entry;
	}

}
