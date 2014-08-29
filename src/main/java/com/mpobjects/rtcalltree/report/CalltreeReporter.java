/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public interface CalltreeReporter {
	void reportEndOfTree(List<? extends CalltreeEntry> aCallTree);
}
