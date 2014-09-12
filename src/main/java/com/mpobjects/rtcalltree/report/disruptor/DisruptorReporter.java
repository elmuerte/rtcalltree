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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.mpobjects.rtcalltree.CalltreeEntry;
import com.mpobjects.rtcalltree.report.CalltreeReporter;

/**
 * This reporter will make use of <a href="https://github.com/LMAX-Exchange/disruptor">LMAX Disruptor</a> to
 * asynchronously call other reporters. The thread where the calltree was recorded will continue while the reporters are
 * busy processing the calltree.
 */
public final class DisruptorReporter implements CalltreeReporter {

	public static final int DEFAULT_RINGBUFFER_SIZE = 256;

	private RingBuffer<DisruptorReportEvent> ringBuffer;

	public DisruptorReporter(Collection<? extends CalltreeReporter> aReceivers) {
		this(aReceivers, DEFAULT_RINGBUFFER_SIZE);
	}

	public DisruptorReporter(Collection<? extends CalltreeReporter> aReceivers, int aRingBufferSize) {
		super();
		init(aReceivers, aRingBufferSize);
	}

	@Override
	public void reportEndOfTree(String aThreadName, List<? extends CalltreeEntry> aCallTree) {
		long seq = ringBuffer.next();
		try {
			DisruptorReportEvent event = ringBuffer.get(seq);
			event.set(aThreadName, aCallTree);
		} finally {
			ringBuffer.publish(seq);
		}
	}

	protected void init(Collection<? extends CalltreeReporter> aReceivers, int aRingBufferSize) {
		Executor executor = Executors.newCachedThreadPool();
		Disruptor<DisruptorReportEvent> disruptor = new Disruptor<DisruptorReportEvent>(new DisruptorReportEventFactory(), aRingBufferSize, executor,
				ProducerType.SINGLE, new BlockingWaitStrategy());

		registerEventHandlers(disruptor, aReceivers);

		ringBuffer = disruptor.start();

	}

	/**
	 * @param aDisruptor
	 * @param aReceiver
	 */
	private void registerEventHandlers(Disruptor<DisruptorReportEvent> aDisruptor, Collection<? extends CalltreeReporter> aReceivers) {
		List<DisruptorReportEventHandler> handlers = new ArrayList<DisruptorReportEventHandler>();
		for (CalltreeReporter reporter : aReceivers) {
			if (reporter instanceof DisruptorReporter || reporter == null) {
				continue;
			}
			handlers.add(new DisruptorReportEventHandler(reporter));
		}

		aDisruptor.handleEventsWith(handlers.toArray(new DisruptorReportEventHandler[0])).then(new UnsetDisruptorReportEvent());
	}
}
