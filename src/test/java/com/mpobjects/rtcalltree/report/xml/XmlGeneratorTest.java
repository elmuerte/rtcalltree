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

package com.mpobjects.rtcalltree.report.xml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.junit.Before;
import org.junit.Test;

import com.mpobjects.rtcalltree.impl.CalltreeEntryImpl;

/**
 *
 */
public class XmlGeneratorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGeneration() throws Exception {

		List<CalltreeEntryImpl> calltree = new ArrayList<CalltreeEntryImpl>();
		calltree.add(newEntry(0, "foo.bar", "baz"));
		calltree.add(newEntry(1, "foo.bar.quux", "bla"));
		calltree.add(newEntry(2, "baz.fruml", "quux"));
		calltree.add(newEntry(3, "fruml.frop.foo", "friep"));
		calltree.add(newEntry(3, "fizz.buzz", "frop"));
		calltree.add(newEntry(1, "foo.bar.quux", "baz"));

		StringWriter writer = new StringWriter();
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlwriter = factory.createXMLStreamWriter(writer);
		XmlGenerator gen = new XmlGenerator(xmlwriter);
		gen.startReport(new Date());
		gen.writeCallTree("dummy", calltree);
		gen.endReport();

		System.out.println(writer.toString());
	}

	/**
	 * @param aI
	 * @param aString
	 * @param aString2
	 * @return
	 */
	protected CalltreeEntryImpl newEntry(int aDepth, String aClass, String aMethod) {
		CalltreeEntryImpl entry = new CalltreeEntryImpl(aClass, aMethod);
		entry.setDepth(aDepth);
		entry.setStartTime(System.nanoTime());
		entry.endRecord(entry.getStartTime() + 123456);
		return entry;
	}

}
