/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.mpobjects.rtcalltree.CalltreeRecorder;

/**
 *
 */
public class CalltreeRecorderImpl implements CalltreeRecorder {

	public List<CTRecordImpl> stack;

	public CTRecordImpl lastEntry;

	public CalltreeRecorderImpl() {
		stack = new ArrayList<CTRecordImpl>();
	}

	@Override
	public void start(Class<?> aClass, Method aMethod, Object[] aArguments) {

	}

	@Override
	public void stop(Class<?> aClass, Method aMethod) {
		// TODO
		lastEntry = null;
	}
}
