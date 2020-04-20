/*
 * Copyright (C) 2019-2020 ConnectorIO Sp. z o.o.
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
package org.connectorio.binding.plc4x.beckhoff.internal.discovery.udp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import org.apache.plc4x.java.ads.api.generic.types.AmsNetId;

public class UdpDiscoveryRequest extends UdpStructure {

  public static final int CAPACITY = 24;
  private final String sourceAms;

  public UdpDiscoveryRequest(String sourceAms) {
    this.sourceAms = sourceAms;
  }

  public byte[] construct() {
    ByteBuffer buffer = ByteBuffer.allocate(CAPACITY).order(ByteOrder.LITTLE_ENDIAN);

    AmsNetId senderAms = AmsNetId.of(sourceAms);

    // header
    buffer.put(HEADER);
    buffer.put(STATIC);
    buffer.put(BROADCAST_REQUEST_TYPE);

    buffer.put(senderAms.getBytes()); // source ams
    buffer.put(new byte[] {0x10, 0x27});
    buffer.put(new byte[] {0x00, 0x00, 0x00, 0x00});

    byte[] packet = new byte[CAPACITY];
    buffer.position(0);
    buffer.get(packet);
    return packet;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UdpDiscoveryRequest{");
    sb.append(", sourceAms='").append(sourceAms).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UdpDiscoveryRequest)) {
      return false;
    }
    UdpDiscoveryRequest that = (UdpDiscoveryRequest) o;
    return Objects.equals(sourceAms, that.sourceAms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceAms);
  }

  public static UdpDiscoveryRequest parse(byte[] bytes) {
    out("Outgoing packet", bytes);

    ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

    slice(buffer, 12); // header

    byte[] slice = slice(buffer, 6);
    AmsNetId amsSender = AmsNetId.of(slice);
    slice(buffer, 2);  // unknown 0x1027
    slice(buffer, 4);  // unknown 0x00 00 00 00

    return new UdpDiscoveryRequest(amsSender.toString());
  }

}
