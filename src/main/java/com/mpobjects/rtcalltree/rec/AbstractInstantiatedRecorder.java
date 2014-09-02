/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.rec;

import javax.annotation.Nonnull;

import com.mpobjects.rtcalltree.CalltreeRecordManager;
import com.mpobjects.rtcalltree.CalltreeRecordProvider;
import com.mpobjects.rtcalltree.conv.ArgumentConverter;

/**
 *
 */
public abstract class AbstractInstantiatedRecorder {

	protected CalltreeRecordProvider calltreeRecordProvider;

	protected ArgumentConverter argumentConverter;

	public AbstractInstantiatedRecorder() {
		super();
		calltreeRecordProvider = CalltreeRecordManager.instance();
	}

	public ArgumentConverter getArgumentConverter() {
		return argumentConverter;
	}

	public CalltreeRecordProvider getCalltreeRecordProvider() {
		return calltreeRecordProvider;
	}

	/**
	 * Set the parameter converter. If set to null (the default) parameter values will not be recorded.
	 *
	 * @param aArgumentConverter
	 */
	public void setArgumentConverter(ArgumentConverter aArgumentConverter) {
		argumentConverter = aArgumentConverter;
	}

	public void setCalltreeRecordProvider(@Nonnull CalltreeRecordProvider aCalltreeRecordProvider) {
		calltreeRecordProvider = aCalltreeRecordProvider;
	}

}