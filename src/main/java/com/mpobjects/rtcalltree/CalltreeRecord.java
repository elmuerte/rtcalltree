/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import javax.annotation.Nonnull;

/**
 * A record of {@link CalltreeEntry}s
 */
public interface CalltreeRecord {
	/**
	 * Register the start of an entry
	 *
	 * @param aEntry
	 */
	void start(@Nonnull MutableCalltreeEntry aEntry);

	/**
	 * Register the end of an entry
	 *
	 * @param aEntry
	 */
	void stop(@Nonnull MutableCalltreeEntry aEntry);
}
