/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.springone.gcp.pubsub;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.BytesJsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
public class Springone2018GcpPubsubApplicationTests {

	private static final String KAFKA_TOPIC = "filtered-locations";

	@ClassRule
	public static final KafkaEmbedded KAFKA_EMBEDDED = new KafkaEmbedded(1, true, KAFKA_TOPIC);

	@BeforeClass
	public static void setup() {
		System.setProperty("spring.kafka.bootstrapServers", KAFKA_EMBEDDED.getBrokersAsString());
	}

	private final CountDownLatch processedLatch = new CountDownLatch(5);

	@Test
	public void testFromPubSubToKafka() throws InterruptedException {
		assertThat(this.processedLatch.await(60, TimeUnit.SECONDS)).isTrue();
	}

	@KafkaListener(topics = KAFKA_TOPIC, id = "gcpPubSubKafkaListener")
	public void processFromKafka(String locationUpdate) {
		System.out.println("--------------------------------");
		System.out.println("FILTERED LOCATION: " + locationUpdate);
		System.out.println("--------------------------------");
		this.processedLatch.countDown();
	}

}
