/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import com.mpobjects.rtcalltree.MutableCalltreeEntry;

/**
 * A reported that does not do anything.
 */
public final class NullCalltreeReporter implements CalltreeReporter {

	public static final NullCalltreeReporter INSTANCE = new NullCalltreeReporter();

	public NullCalltreeReporter() {
	}

	@Override
	public void reportEndOfTree(List<MutableCalltreeEntry> aCallTree) {
	}

}
