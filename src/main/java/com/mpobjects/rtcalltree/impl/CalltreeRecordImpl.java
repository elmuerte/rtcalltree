/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpobjects.rtcalltree.CalltreeRecord;
import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.report.CalltreeReporter;

/**
 *
 */
public class CalltreeRecordImpl implements CalltreeRecord {

	private static final Logger LOG = LoggerFactory.getLogger(CalltreeRecordImpl.class);

	protected List<MutableCalltreeEntry> entries;

	protected MutableCalltreeEntry lastEntry;

	protected int currentDepth;

	protected CalltreeReporter calltreeReporter;

	public CalltreeRecordImpl() {
		initRecord();
	}

	/**
	 * @param aCalltreeReporter
	 */
	public void setCalltreeReporter(@Nonnull CalltreeReporter aCalltreeReporter) {
		calltreeReporter = aCalltreeReporter;
	}

	@Override
	public void start(@Nonnull MutableCalltreeEntry aEntry) {
		aEntry.setParent(lastEntry);
		aEntry.setDepth(currentDepth++);
		aEntry.setStartTime(System.nanoTime());
		entries.add(aEntry);
		lastEntry = aEntry;
	}

	@Override
	public void stop(@Nonnull MutableCalltreeEntry aEntry) {
		final long endTime = System.nanoTime();
		if (lastEntry != aEntry) {
			IllegalStateException e = new IllegalStateException();
			LOG.error("Stopped entry \"" + aEntry.toString() + "\" is not the last started entry \"" + lastEntry.toString() + "\"", e);
			// TODO: something wrong
			return;
		}
		// might already have been called.
		aEntry.endRecord(endTime);
		currentDepth = aEntry.getDepth();
		lastEntry = aEntry.getParent();
		if (currentDepth == 0) {
			endOfCallTree();
		}
	}

	/**
	 *
	 */
	protected void endOfCallTree() {
		final List<MutableCalltreeEntry> currentTree = entries;
		initRecord();
		calltreeReporter.reportEndOfTree(currentTree);
	}

	/**
	 *
	 */
	protected void initRecord() {
		entries = new ArrayList<MutableCalltreeEntry>();
		currentDepth = 0;
		lastEntry = null;
	}

}
