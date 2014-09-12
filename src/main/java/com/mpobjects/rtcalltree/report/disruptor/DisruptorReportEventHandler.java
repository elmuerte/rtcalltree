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

package com.mpobjects.rtcalltree.report.disruptor;

import javax.annotation.Nonnull;

import com.lmax.disruptor.EventHandler;
import com.mpobjects.rtcalltree.report.CalltreeReporter;

/**
 * Disruptor {@link EventHandler} which forwards the event to the reporter.
 */
public final class DisruptorReportEventHandler implements EventHandler<DisruptorReportEvent> {

	private final CalltreeReporter reporter;

	public DisruptorReportEventHandler(@Nonnull CalltreeReporter aReporter) {
		reporter = aReporter;
	}

	@Override
	public void onEvent(DisruptorReportEvent aEvent, long aSequence, boolean aEndOfBatch) throws Exception {
		reporter.reportEndOfTree(aEvent.getThreadName(), aEvent.getCalltree());
	}

}
