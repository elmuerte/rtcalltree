/*
 * Runtime Call Tree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2014, Michiel Hendriks <michiel.hendriks@mp-objects.com>
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
