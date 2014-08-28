/*
 * Copyright 2014, MP Objects, http://www.mp-objects.com
 */
package com.mpobjects.rtcalltree;

import java.lang.reflect.Method;

/**
 * 
 */
public interface CalltreeRecorder {

	void start(Class<?> aClass, Method aMethod, Object[] aArguments);

	void stop(Class<?> aClass, Method aMethod);

}