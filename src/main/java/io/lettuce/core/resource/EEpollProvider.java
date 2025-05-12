package io.lettuce.core.resource;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.epoll.EpollChannelOption;

import java.time.Duration;

public class EEpollProvider {

    /**
     * Apply TcpUserTimeout options.
     */
    public static void applyTcpUserTimeout(Bootstrap bootstrap, Duration timeout) {
        bootstrap.option(EpollChannelOption.TCP_USER_TIMEOUT, Math.toIntExact(timeout.toMillis()));
    }

}
