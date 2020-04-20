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
 */
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

import static org.assertj.core.api.Assertions.assertThat;

import org.connectorio.binding.plc4x.beckhoff.internal.discovery.udp.UdpDiscoveryResponse;
import org.junit.jupiter.api.Test;

class UdpDiscoveryResponseTest {

  private static byte[] INPUT = new byte[] {
    0x03, 0x66, 0x14, 0x71, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, (byte) 0x80, (byte) 0xc0, (byte) 0xa8, (byte) 0x02, (byte) 0xdd,
    0x01, 0x01, 0x10, 0x27, 0x04, 0x00, 0x00, 0x00, 0x05, 0x00, 0x10, 0x00, 0x44, 0x45, 0x53, 0x4b,
    0x54, 0x4f, 0x50, 0x2d, 0x54, 0x30, 0x4e, 0x36, 0x55, 0x4e, 0x42, 0x00, 0x04, 0x00, 0x14, 0x01,
    0x14, 0x01, 0x00, 0x00, 0x0a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x63, 0x45, 0x00, 0x00,
    0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x04, 0x00, 0x03, 0x01, (byte) 0xb8, 0x0f, 0x12, 0x00, 0x41, 0x00,
    0x33, 0x63, 0x30, 0x39, 0x37, 0x32, 0x61, 0x34, 0x38, 0x66, 0x64, 0x62, 0x37, 0x36, 0x66, 0x31,
    0x32, 0x61, 0x38, 0x31, 0x32, 0x38, 0x39, 0x61, 0x33, 0x35, 0x64, 0x34, 0x61, 0x33, 0x37, 0x31,
    0x65, 0x31, 0x38, 0x66, 0x63, 0x33, 0x30, 0x37, 0x36, 0x36, 0x30, 0x38, 0x32, 0x38, 0x39, 0x37,
    0x30, 0x31, 0x38, 0x61, 0x38, 0x62, 0x66, 0x38, 0x38, 0x64, 0x39, 0x39, 0x32, 0x64, 0x63, 0x38,
    0x00
  };

  @Test
  void test() {
    UdpDiscoveryResponse response = UdpDiscoveryResponse.parse(INPUT);

    assertThat(response.getSourceAms())
      .isEqualTo("192.168.2.221.1.1");

    assertThat(response.getName())
      .isEqualTo("DESKTOP-T0N6UNB");
  }

}