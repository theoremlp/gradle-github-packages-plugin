/*
 * (c) Copyright 2023 Theorem Lp. All rights reserved.
 */

package com.theoremlp.github.packages;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

final class Mappers {
    public static final ObjectMapper MAPPER =
            new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private Mappers() {}
}
