/*
 *  This file is part of nzyme.
 *
 *  nzyme is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  nzyme is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with nzyme.  If not, see <http://www.gnu.org/licenses/>.
 */

package horse.wtf.nzyme.dot11.networks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import horse.wtf.nzyme.dot11.Dot11SecurityConfiguration;
import horse.wtf.nzyme.dot11.networks.beaconrate.AverageBeaconRate;
import horse.wtf.nzyme.dot11.networks.beaconrate.BeaconRate;
import horse.wtf.nzyme.dot11.networks.beaconrate.BeaconRateManager;
import horse.wtf.nzyme.util.Tools;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@AutoValue
public abstract class SSID {

    @JsonIgnore
    public abstract BeaconRateManager beaconRateManager();

    @JsonIgnore
    public abstract String name();

    @JsonProperty
    public abstract String bssid();

    @JsonProperty
    public abstract Map<Integer, Channel> channels();

    @JsonProperty("human_readable")
    public boolean isHumanReadable() {
        return Tools.isHumanlyReadable(name());
    }

    private List<Dot11SecurityConfiguration> security = Lists.newArrayList();

    @JsonProperty
    public List<Dot11SecurityConfiguration> getSecurity() {
        return security;
    }

    @JsonProperty("beacon_rate_history")
    public List<AverageBeaconRate> beaconRateHistory = Collections.emptyList();

    @JsonIgnore
    public final AtomicInteger beaconCount = new AtomicInteger(1);

    @JsonProperty("name")
    public String nameSafe() {
        if (isHumanReadable()) {
            return name();
        } else {
            return "[not human readable]";
        }
    }

    @JsonProperty("beacon_rate")
    public BeaconRate beaconRate() {
        return beaconRateManager().getAverageBeaconRate(bssid(), name());
    }

    @JsonIgnore
    public void setBeaconRateHistory(List<AverageBeaconRate> history) {
        this.beaconRateHistory = history;
    }

    public static SSID create(String name, String bssid, BeaconRateManager beaconRateManager) {
        return builder()
                .name(name)
                .bssid(bssid)
                .channels(Maps.newHashMap())
                .beaconRateManager(beaconRateManager)
                .build();
    }

    @JsonIgnore
    public void updateSecurity(List<Dot11SecurityConfiguration> security) {
        this.security = security;
    }

    public static Builder builder() {
        return new AutoValue_SSID.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder bssid(String bssid);

        public abstract Builder channels(Map<Integer, Channel> channels);

        public abstract Builder beaconRateManager(BeaconRateManager beaconRateManager);

        public abstract SSID build();
    }

}
