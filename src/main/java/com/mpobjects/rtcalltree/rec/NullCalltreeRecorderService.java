/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree.rec;

/**
 * Recorder service which does nothing. When using the {@link CalltreeRecorderService} in a Spring set up you probably
 * want to default to the this {@link NullCalltreeRecorderService}.
 */
public class NullCalltreeRecorderService implements CalltreeRecorderService {

	private static final RecordToken EMPTY_TOKEN;

	static {
		EMPTY_TOKEN = new RecordToken() {
		};
	}

	public NullCalltreeRecorderService() {
	}

	@Override
	public RecordToken start(Class<?> aClass, String aMethodName, Object... aParameterValues) {
		return EMPTY_TOKEN;
	}

	@Override
	public RecordToken start(Object aInstance, String aMethodName, Object... aParameterValues) {
		return EMPTY_TOKEN;
	}

	@Override
	public RecordToken start(String aClassname, String aMethodName, Object... aParameterValues) {
		return EMPTY_TOKEN;
	}

	@Override
	public void stop(RecordToken aToken) {
	}

}
