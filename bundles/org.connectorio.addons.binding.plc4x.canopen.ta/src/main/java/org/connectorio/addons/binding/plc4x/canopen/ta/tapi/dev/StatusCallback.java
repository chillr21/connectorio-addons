/*
 * Copyright (C) 2019-2021 ConnectorIO Sp. z o.o.
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
 * SPDX-License-Identifier: Apache-2.0
 */
package org.connectorio.addons.binding.plc4x.canopen.ta.tapi.dev;

import java.util.function.Consumer;
import org.apache.plc4x.java.spi.generation.ParseException;
import org.apache.plc4x.java.spi.generation.ReadBuffer;

class StatusCallback extends AbstractCallback {

  private final int clientId;
  private final int nodeId;
  private final Consumer<Boolean> connected;

  StatusCallback(int clientId, int nodeId, Consumer<Boolean> connected) {
    this.clientId = clientId;
    this.nodeId = nodeId;
    this.connected = connected;
  }

  @Override
  protected void accept(ReadBuffer buffer) throws ParseException {
    int mpdoId = 0x80 | buffer.readUnsignedInt(8);              // 0
    int index = buffer.readUnsignedInt(16);                     // 1+2
    int subIndex = buffer.readUnsignedInt(8);                   // 3
    int answerClientId = 0x640 ^ buffer.readUnsignedInt(16);    // 4+5
    int flag = buffer.readUnsignedInt(8);                       // 6
    short status = buffer.readUnsignedShort(8);                     // 7

    logger.debug("Received status payload {}", buffer);
    // 89 80 12 01 49 06 00 00
    if (answerClientId == clientId) {
      if (status == 0x00) {
        logger.debug("Client {} successfully logged into node {}.", clientId, nodeId);
        connected.accept(true);
      } else if (status == 0x80) {
        logger.debug("Client {} logged out from {}.", clientId, nodeId);
        connected.accept(false);
      } else {
        logger.warn("Failed to handle login answer from {}, unknown status {}", answerClientId, Integer.toHexString(status));
        connected.accept(false);
      }
    } else {
      logger.debug("Received MPDO for other node {}", answerClientId);
    }
  }
}