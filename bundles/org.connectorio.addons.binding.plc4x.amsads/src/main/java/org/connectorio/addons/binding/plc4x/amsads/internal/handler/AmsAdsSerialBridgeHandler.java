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
package org.connectorio.addons.binding.plc4x.amsads.internal.handler;

import java.util.concurrent.CompletableFuture;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.connectorio.addons.binding.plc4x.amsads.internal.config.AmsAdsConfiguration;
import org.connectorio.addons.binding.plc4x.amsads.internal.config.SerialConfiguration;
import org.connectorio.plc4x.extras.osgi.PlcDriverManager;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link AmsAdsSerialBridgeHandler} is responsible for handling connections to AMS/ADS PLC
 * over serial port.
 *
 * @author Lukasz Dywicki - Initial contribution
 */
public class AmsAdsSerialBridgeHandler extends AbstractAmsAdsBridgeHandler<PlcConnection, SerialConfiguration> {

  private final Logger logger = LoggerFactory.getLogger(AmsAdsSerialBridgeHandler.class);
  private final PlcDriverManager driverManager;

  public AmsAdsSerialBridgeHandler(Bridge thing, PlcDriverManager driverManager) {
    super(thing);
    this.driverManager = driverManager;
  }

  @Override
  public void handleCommand(ChannelUID channelUID, Command command) {
  }

  @Override
  protected Runnable createInitializer(AmsAdsConfiguration amsAds, CompletableFuture<PlcConnection> initializer) {
    return new Runnable() {
      @Override
      public void run() {
        try {
          SerialConfiguration config = getBridgeConfig().get();
          String target = "targetAmsNetId=" + config.targetAmsId + "&targetAmsPort=" + config.targetAmsPort;
          String source = "&sourceAmsNetId=" + amsAds.sourceAmsId + "&sourceAmsPort=" + amsAds.sourceAmsPort;
          PlcConnection connection = driverManager.getConnection("ads:serial://" + config.port + "/" + target + source);

          if (!connection.isConnected()) {
            connection.connect();
          }

          if (connection.isConnected()) {
            updateStatus(ThingStatus.ONLINE);
            initializer.complete(connection);
          } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Connection failed");
            initializer.complete(null);
          }
        } catch (PlcConnectionException e) {
          logger.warn("Could not obtain connection", e);
          updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
          initializer.completeExceptionally(e);
        }
      }
    };
  }

}
