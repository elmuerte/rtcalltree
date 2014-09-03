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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;

import com.mpobjects.rtcalltree.CalltreeEntry;

/**
 *
 */
public class XmlGenerator {

	public static final String NAMESPACE = "http://system.mp-objects.com/schemas/RTCalltreeReport";

	public static final String VERSION = "1.0";

	protected SimpleDateFormat iso8601format;

	protected XMLStreamWriter writer;

	/**
	 * @param aWriter
	 */
	public XmlGenerator(@Nonnull XMLStreamWriter aWriter) {
		iso8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		writer = aWriter;
	}

	/**
	 * And the report element
	 *
	 * @throws XMLStreamException
	 */
	public void endReport() throws XMLStreamException {
		writer.writeEndDocument();
		writer.flush();
	}

	/**
	 * Start the report element
	 *
	 * @param aCreationDate
	 * @throws XMLStreamException
	 */
	public void startReport(Date aCreationDate) throws XMLStreamException {
		writer.writeStartDocument();
		writer.setDefaultNamespace(NAMESPACE);
		writer.writeStartElement(NAMESPACE, "report");
		writer.writeAttribute("version", VERSION);
		writer.writeAttribute("timestamp", iso8601format.format(aCreationDate));

	}

	/**
	 * @param aCallTree
	 * @throws XMLStreamException
	 */
	public void writeCallTree(String aThreadName, List<? extends CalltreeEntry> aCallTree) throws XMLStreamException {
		writer.writeStartElement(NAMESPACE, "calltree");
		writer.writeAttribute("calltree", aThreadName);
		writeCallTreeEntry(aCallTree);
		writer.writeEndElement();
	}

	protected void writeCallTreeEntry(CalltreeEntry aEntry) throws XMLStreamException {
		writer.writeAttribute("depth", Integer.toString(aEntry.getDepth()));
		writer.writeAttribute("start-time", iso8601format.format(new Date(aEntry.getStartTime() / 1000)));
		writer.writeAttribute("duration", Long.toString(aEntry.getDeltaTime()));
		writer.writeAttribute("class", aEntry.getClassName());
		writer.writeAttribute("method", aEntry.getMethodName());
		if (!StringUtils.isEmpty(aEntry.getSourceFilename())) {
			writer.writeAttribute("source-file", aEntry.getSourceFilename());
			writer.writeAttribute("source-line", Integer.toString(aEntry.getSourceLine()));
		}
		writeParameterElements(aEntry);
	}

	/**
	 * @param aCallTree
	 * @throws XMLStreamException
	 */
	protected void writeCallTreeEntry(List<? extends CalltreeEntry> aCallTree) throws XMLStreamException {
		int currentDepth = -1;
		for (CalltreeEntry entry : aCallTree) {
			// close open elements up to the sibling level
			while (currentDepth >= entry.getDepth()) {
				writer.writeEndElement();
				--currentDepth;
			}
			currentDepth = entry.getDepth();

			writer.writeStartElement(NAMESPACE, "entry");
			writeCallTreeEntry(entry);
		}
		// close remaining open elements
		while (currentDepth > -1) {
			writer.writeEndElement();
			--currentDepth;
		}
	}

	/**
	 * @param aEntry
	 * @throws XMLStreamException
	 */
	protected void writeParameterElements(CalltreeEntry aEntry) throws XMLStreamException {
		String[] types = aEntry.getParameterTypes();
		if (types == null) {
			types = new String[0];
		}
		Object[] vals = aEntry.getParameterValues();
		if (vals == null) {
			vals = new Object[0];
		}
		int paramCnt = Math.max(types.length, vals.length);
		for (int i = 0; i < paramCnt; ++i) {
			Object val = null;
			String type = null;
			if (types.length > i) {
				type = types[i];
			}
			if (vals.length > i) {
				val = vals[i];
			}
			if (val == null) {
				writer.writeEmptyElement(NAMESPACE, "parameter");
			} else {
				writer.writeEmptyElement(NAMESPACE, "parameter");
				writer.writeCharacters(val.toString());
			}
			if (!StringUtils.isBlank(type)) {
				writer.writeAttribute("type", type);
			}
		}
	}

}
