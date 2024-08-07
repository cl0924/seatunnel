/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.connectors.seatunnel.activemq.sink;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.serialization.SerializationSchema;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.api.table.type.SeaTunnelRowType;
import org.apache.seatunnel.connectors.seatunnel.activemq.client.ActivemqClient;
import org.apache.seatunnel.connectors.seatunnel.common.sink.AbstractSinkWriter;
import org.apache.seatunnel.format.json.JsonSerializationSchema;

import java.util.Optional;

public class ActivemqSinkWriter extends AbstractSinkWriter<SeaTunnelRow, Void> {
    private ActivemqClient activeMQClient;

    private final SerializationSchema serializationSchema;

    public ActivemqSinkWriter(ReadonlyConfig config, SeaTunnelRowType seaTunnelRowType) {
        this.activeMQClient = new ActivemqClient(config);
        this.serializationSchema = new JsonSerializationSchema(seaTunnelRowType);
    }

    @Override
    public void write(SeaTunnelRow element) {
        activeMQClient.write(serializationSchema.serialize(element));
    }

    @Override
    public Optional prepareCommit() {
        return Optional.empty();
    }

    @Override
    public void close() {
        if (activeMQClient != null) {
            activeMQClient.close();
        }
    }
}