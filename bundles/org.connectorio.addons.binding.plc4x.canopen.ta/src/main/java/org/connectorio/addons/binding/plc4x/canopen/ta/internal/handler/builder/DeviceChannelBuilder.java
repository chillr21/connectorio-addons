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
package org.connectorio.addons.binding.plc4x.canopen.ta.internal.handler.builder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.connectorio.addons.binding.plc4x.canopen.ta.tapi.dev.InOutCallback;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.thing.Bridge;

public interface DeviceChannelBuilder extends InOutCallback {

  CompletableFuture<Bridge> build();

  DeviceChannelBuilder withConfiguration(Configuration configuration);

  DeviceChannelBuilder withProperties(Map<String, String> properties);

}
