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
package com.mpobjects.rtcalltree.conv;

/**
 * The basic argument converter shows the value of basic types like String, Number, Boolean and Character. For all other
 * classes it will show the classname and the identity hash.
 */
public class BasicArgumentConverter implements ArgumentConverter {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	public BasicArgumentConverter() {
	}

	@Override
	public Object[] convertArguments(Object[] aArguments) {
		if (aArguments == null) {
			return null;
		}
		if (aArguments.length == 0) {
			return EMPTY_ARRAY;
		}
		Object[] res = new Object[aArguments.length];
		for (int i = 0; i < res.length; ++i) {
			res[i] = convert(aArguments[i]);
		}
		return res;
	}

	private Object convert(Object aObject) {
		if (aObject == null) {
			return "<<null>>";
		}
		if (aObject instanceof String || aObject instanceof Number || aObject instanceof Boolean || aObject instanceof Character) {
			return "(" + aObject.getClass().getSimpleName() + ") " + aObject.toString();
		}
		return aObject.getClass().getName() + "#" + System.identityHashCode(aObject);
	}
}
