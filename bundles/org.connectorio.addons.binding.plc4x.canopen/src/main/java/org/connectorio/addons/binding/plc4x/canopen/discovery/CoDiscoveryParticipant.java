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
package org.connectorio.addons.binding.plc4x.canopen.discovery;

import org.connectorio.addons.binding.plc4x.canopen.api.CoNode;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.thing.ThingUID;

/**
 * CANopen discovery is a dynamic process meaning that once node is found it can be additionally scanned do determine device kind.
 *
 * This process is shifted to discovery participants who can utilize various methods in order to identify device.
 * Implementation of delegation logic from CANopen binding to this interface might vary, however this interface itself is
 * intended to be called once per every scan attempt. Please note that it might happen that first participant which returns
 * successfully result might win preventing others from doing their job.
 *
 * It is advised to abstain from giving discovery result implementer processing returned unclear results.
 * This means that in case of failed reads or writes there should be no assumptions to let subsequent participants do their job.
 */
public interface CoDiscoveryParticipant {

  /**
   * Inform discovery participant that a new CANopen node is found.
   *
   * @param bridgeUID The bridge (thing representing an connection) to which thing must be linked.
   * @param node CANopen node which is just found.
   * @return Discovery result if node was identified or null if not.
   */
  DiscoveryResult nodeDiscovered(ThingUID bridgeUID, CoNode node);

}
