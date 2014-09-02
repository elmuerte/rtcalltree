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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

import com.mpobjects.rtcalltree.CalltreeRecord;
import com.mpobjects.rtcalltree.MutableCalltreeEntry;
import com.mpobjects.rtcalltree.impl.CalltreeEntryImpl;

/**
 * A calltree recorder which can be used with an AspectJ around advice. From an advice simply call the
 * {@link #record(ProceedingJoinPoint)} method.
 */
public class AspectJRecorder extends AbstractInstantiatedRecorder {

	public AspectJRecorder() {
		super();
	}

	public Object record(ProceedingJoinPoint aJoinPoint) throws Throwable {
		final MutableCalltreeEntry entry = createEntry(aJoinPoint);
		final CalltreeRecord record = calltreeRecordProvider.getRecord();
		record.start(entry);
		try {
			return aJoinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			record.stop(entry);
		}
	}

	/**
	 * @param aJoinPoint
	 * @return
	 */
	protected MutableCalltreeEntry createEntry(ProceedingJoinPoint aJoinPoint) {
		final Signature signature = aJoinPoint.getSignature();
		final String className = signature.getDeclaringTypeName();
		final String methodName = signature.getName();
		CalltreeEntryImpl entry = new CalltreeEntryImpl(className, methodName);
		// Spring AOP throws exceptions on getFileName() etc. instead of just returning null for SourceLocation
		// if (aJoinPoint.getSourceLocation() != null) {
		// entry.setSourceFilename(aJoinPoint.getSourceLocation().getFileName());
		// entry.setSourceLine(aJoinPoint.getSourceLocation().getLine());
		// }
		if (argumentConverter != null && aJoinPoint.getArgs() != null) {
			entry.setParameterValues(argumentConverter.convertArguments(aJoinPoint.getArgs()));
		}
		if (signature instanceof CodeSignature) {
			entry.setParameterTypes(getTypeNames(((CodeSignature) signature).getParameterTypes()));
		}
		return entry;
	}

	/**
	 * Convert the class array to type names
	 *
	 * @param aParameterTypes
	 * @return
	 */
	protected final String[] getTypeNames(Class<?>[] aParameterTypes) {
		if (aParameterTypes == null) {
			return null;
		}
		String[] result = new String[aParameterTypes.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = aParameterTypes[i].getName();
		}
		return result;
	}
}
