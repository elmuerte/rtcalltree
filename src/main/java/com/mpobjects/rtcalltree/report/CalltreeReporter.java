/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.report;

import java.util.List;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public interface CalltreeReporter {
	void reportEndOfTree(@Nonnull List<? extends CalltreeEntry> aCallTree);
}
