/*
 * Copyright (C) 2018 Signal Technology Foundation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/agpl.html>.
 */

package org.signal.verificationservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DeliveryResult {

    @JsonProperty
    private boolean success;

    @JsonProperty
    private String provider;

    @JsonProperty
    private final List<String> results = new ArrayList<>();

    @JsonProperty
    private final List<String> errors = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public String getProvider() {
        return provider;
    }

    public List<String> getResults() {
        return results;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public int hashCode() { return Objects.hash(errors, provider, results, success); }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof DeliveryResult)) { return false; }
        final DeliveryResult that = (DeliveryResult) o;
        return this.success == that.success &&
                StringUtils.equals(this.provider, that.provider) &&
                this.results.equals(that.results) &&
                this.errors.equals(that.errors);
    }

    @Override
    public String toString() {
        return "DeliveryResult{" +
                "success=" + success +
                ", provider='" + provider + '\'' +
                ", results=" + results +
                ", errors=" + errors +
                '}';
    }
}
