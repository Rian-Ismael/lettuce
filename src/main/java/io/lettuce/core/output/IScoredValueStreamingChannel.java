package io.lettuce.core.output;

import io.lettuce.core.ScoredValue;

public interface IScoredValueStreamingChannel<V> {

    /**
     * Called on every incoming ScoredValue.
     *
     * @param value the scored value
     */
    void onValue(ScoredValue<V> value);

}
