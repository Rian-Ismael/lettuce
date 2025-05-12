package io.lettuce.core.resource;

import io.netty.util.concurrent.EventExecutorGroup;

public interface IEventLoopResourcesWrapper {

    boolean matches(Class<? extends EventExecutorGroup> type);

}
