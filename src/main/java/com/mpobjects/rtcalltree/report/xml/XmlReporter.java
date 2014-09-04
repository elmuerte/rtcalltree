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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpobjects.rtcalltree.CalltreeEntry;
import com.mpobjects.rtcalltree.report.AbstractCalltreeReporter;

/**
 *
 */
public class XmlReporter extends AbstractCalltreeReporter {

	private static final Logger LOG = LoggerFactory.getLogger(XmlReporter.class);

	protected File destination;

	protected String filenamePattern;

	public XmlReporter() {
		filenamePattern = "calltree-{0,date,yyyyMMdd-HHmmssSSS}-{1}.xml";
	}

	/**
	 * @return the destination
	 */
	public File getDestination() {
		return destination;
	}

	/**
	 * @return the filenamePattern
	 */
	public String getFilenamePattern() {
		return filenamePattern;
	}

	@Override
	public void reportEndOfTree(String aThreadName, List<? extends CalltreeEntry> aCallTree) {
		if (!shouldReport(aCallTree)) {
			return;
		}

		Date creationDate = new Date();
		Writer writer = null;
		try {
			writer = createWriter(creationDate, aThreadName);
			XMLStreamWriter xmlwriter = createStreamWriter(writer);
			if (xmlwriter == null) {
				return;
			}
			createNewReport(xmlwriter, creationDate, aThreadName, aCallTree);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * @param aDestination
	 *            the destination to set
	 */
	public void setDestination(@Nonnull File aDestination) {
		destination = aDestination;
	}

	public void setDestination(String aDestination) {
		setDestination(new File(aDestination));
	}

	/**
	 * @param aFilenamePattern
	 *            the filenamePattern to set
	 */
	public void setFilenamePattern(String aFilenamePattern) {
		filenamePattern = aFilenamePattern;
	}

	/**
	 * @param aWriter
	 * @param aCreationDate
	 * @param aThreadName
	 * @param aCallTree
	 */
	protected void createNewReport(XMLStreamWriter aWriter, Date aCreationDate, String aThreadName, List<? extends CalltreeEntry> aCallTree) {
		XmlGenerator gen = new XmlGenerator(aWriter);
		try {
			gen.startReport(aCreationDate);
			gen.writeCallTree(aThreadName, aCallTree);
			gen.endReport();
		} catch (XMLStreamException e) {
			LOG.error("Fatal error generating report. " + e.getMessage(), e);
		}
	}

	protected XMLStreamWriter createStreamWriter(Writer aWriter) {
		XMLOutputFactory factory = XMLOutputFactory.newFactory();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(aWriter);
		} catch (XMLStreamException e) {
			LOG.error("Failed to create XMLStreamWriter. " + e.getMessage(), e);
			return null;
		}
		return writer;
	}

	/**
	 * @param aCreationDate
	 * @param aThreadName
	 * @return
	 */
	protected Writer createWriter(Date aCreationDate, String aThreadName) {
		String filename = MessageFormat.format(filenamePattern, aCreationDate, aThreadName);
		filename = filename.replaceAll("[/: _]+", "_");
		try {
			return new OutputStreamWriter(new FileOutputStream(new File(destination, filename)), Charsets.UTF_8);
		} catch (IOException e) {
			LOG.error("Unable to create file output stream for: " + filename + ". " + e.getMessage(), e);
			return null;
		}
	}

}
