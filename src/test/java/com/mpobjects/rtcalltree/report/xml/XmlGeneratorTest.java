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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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

		// generate the XML
		StringWriter writer = new StringWriter();
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlwriter = factory.createXMLStreamWriter(writer);
		XmlGenerator gen = new XmlGenerator(xmlwriter);
		gen.startReport(new Date());
		gen.writeCallTree("dummy", calltree);
		gen.endReport();

		// parse the XML validating with the schema
		DocumentBuilderFactory domfact = DocumentBuilderFactory.newInstance();
		SchemaFactory schfact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schfact.newSchema(new StreamSource(XmlGenerator.class.getResourceAsStream("RTCalltreeReport.xsd")));
		domfact.setSchema(schema);
		domfact.setNamespaceAware(true);
		DocumentBuilder builder = domfact.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {

			@Override
			public void error(SAXParseException aException) throws SAXException {
				throw aException;
			}

			@Override
			public void fatalError(SAXParseException aException) throws SAXException {
				throw aException;
			}

			@Override
			public void warning(SAXParseException aException) throws SAXException {
				throw aException;
			}
		});
		Document dom = builder.parse(new InputSource(new StringReader(writer.toString())));
		// TODO: check content?
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
		entry.setTimestamp(System.currentTimeMillis());
		entry.endRecord(entry.getStartTime() + 123456);
		entry.setParameterTypes(new String[] { "param1", "param2", "param3" });
		entry.setParameterValues(new Object[] { true, 1, null, "foo" });
		return entry;
	}

}
