/*
 * Copyright 2011-Present, Redis Ltd. and Contributors
 * All rights reserved.
 *
 * Licensed under the MIT License.
 *
 * This file contains contributions from third-party contributors
 * licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lettuce.core.resource;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Delay that increases using equal jitter strategy.
 *
 * <p>
 * Considering retry attempts start at 1, attempt 0 would be the initial call and will always yield 0 (or the lower). Then, each
 * retry step will by default yield {@code randomBetween(0, base * 2 ^ (attempt - 1))}.
 *
 * This strategy is based on <a href="https://www.awsarchitectureblog.com/2015/03/backoff.html">Exponential Backoff and
 * Jitter</a>.
 * </p>
 *
 * @author Jongyeol Choi
 * @author Mark Paluch
 * @since 4.2
 */
class EqualJitterDelay extends ExponentialDelay {

    private final long base;

    private final TimeUnit targetTimeUnit;

    EqualJitterDelay(Duration lower, Duration upper, long base, TimeUnit targetTimeUnit) {
        super(lower, upper, 2, targetTimeUnit);
        this.base = base;
        this.targetTimeUnit = targetTimeUnit;
    }

    @Override
    public Duration createDelay(long attempt) {
        long result;

        if (attempt <= 0) { // safeguard against underflow
            result = 0L;
        } else if (attempt >= 63) { // safeguard against overflow in the bitshift operation
            result = Long.MAX_VALUE - 1;
        } else {
            result = 1L << (attempt - 1);
        }
        long value = randomBetween(0, base * result);
        return applyBounds(Duration.ofNanos(targetTimeUnit.toNanos(value)));
    }

}
