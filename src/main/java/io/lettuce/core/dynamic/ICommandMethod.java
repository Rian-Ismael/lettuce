package io.lettuce.core.dynamic;

public interface ICommandMethod {

    /**
     * @return {@code true} if the method uses reactive execution declaring {@link org.reactivestreams.Publisher} as result
     *         type.
     */
    boolean isReactiveExecution();

}
