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
package org.connectorio.addons.binding.plc4x.canopen.ta.internal.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.plc4x.java.spi.configuration.annotations.ConfigurationParameter;
import org.connectorio.addons.binding.plc4x.canopen.ta.internal.TACANopenBindingConstants;
import org.connectorio.addons.binding.plc4x.canopen.ta.internal.type.TAUnit;
import org.openhab.core.config.core.ConfigDescriptionParameter;
import org.openhab.core.config.core.ConfigDescriptionParameter.Type;
import org.openhab.core.config.core.ConfigDescriptionParameterBuilder;
import org.openhab.core.thing.type.ChannelTypeUID;
import org.openhab.core.types.StateDescriptionFragment;
import org.openhab.core.types.StateDescriptionFragmentBuilder;

public class ChannelTypeDef {

  private final ChannelTypeUID channelType;
  private final String itemType;
  private final String label;
  private final List<TAUnit> units;

  ChannelTypeDef(ChannelTypeUID channelType, String itemType, TAUnit ... units) {
    this(channelType, itemType, label(channelType.getId()), units);
  }

  ChannelTypeDef(ChannelTypeUID channelType, String itemType, String label, TAUnit ... units) {
    this.channelType = channelType;
    this.itemType = itemType;
    this.label = label;
    this.units = Arrays.asList(units);
  }

  public ChannelTypeUID getChannelType() {
    return channelType;
  }

  public String getItemType() {
    return itemType;
  }

  public String toString() {
    return "TypeEntry [channelType=" + channelType + ", itemType=" + itemType + "]";
  }

  public String getLabel() {
    return label;
  }

  public List<TAUnit> getUnits() {
    return units;
  }

  public StateDescriptionFragment getStateDescriptionFragment() {
    return StateDescriptionFragmentBuilder.create().withPattern("%.1f %unit%").build();
  }

  public Optional<ConfigDescriptionParameter> getFallback() {
    return Optional.of(createFallback(Type.DECIMAL).build());
  }

  protected ConfigDescriptionParameterBuilder createFallback(Type type) {
    return ConfigDescriptionParameterBuilder.create("fallback", type)
      .withLabel("Fallback value")
      .withDescription("Value used to set initially on channel when communication is gone or system is restarted.");
  }

  private static String label(String dimension) {
    String label = dimension.replace(TACANopenBindingConstants.TA_ANALOG_PREFIX + "-", "")
      .replace(TACANopenBindingConstants.TA_DIGITAL_PREFIX + "-", "")
      .replace("-", " ").toLowerCase();
    return label.substring(0, 1).toUpperCase() + label.substring(1);
  }

}
