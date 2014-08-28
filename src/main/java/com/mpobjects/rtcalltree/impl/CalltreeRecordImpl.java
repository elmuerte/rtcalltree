/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.CalltreeRecord;
import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.report.CalltreeReporter;

/**
 *
 */
public class CalltreeRecordImpl implements CalltreeRecord {

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
		aEntry.setDepth(currentDepth++);
		entries.add(aEntry);
		lastEntry = aEntry;
	}

	@Override
	public void stop(@Nonnull MutableCalltreeEntry aEntry) {
		if (lastEntry != aEntry) {
			// TODO: something wrong
			return;
		}
		currentDepth = aEntry.getDepth();
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
