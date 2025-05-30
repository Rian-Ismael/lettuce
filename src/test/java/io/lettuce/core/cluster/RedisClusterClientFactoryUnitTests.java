package io.lettuce.core.cluster;

import static io.lettuce.TestTags.UNIT_TEST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.lettuce.core.RedisURI;
import io.lettuce.core.internal.LettuceLists;
import io.lettuce.test.resource.FastShutdown;
import io.lettuce.test.resource.TestClientResources;
import io.lettuce.test.settings.TestSettings;

/**
 * @author Mark Paluch
 */
@Tag(UNIT_TEST)
class RedisClusterClientFactoryUnitTests {

    private static final String URI = "redis://" + TestSettings.host() + ":" + TestSettings.port();

    private static final RedisURI REDIS_URI = RedisURI.create(URI);

    private static final List<RedisURI> REDIS_URIS = LettuceLists.newList(REDIS_URI);

    @Test
    void withStringUri() {
        FastShutdown.shutdown(RedisClusterClient.create(TestClientResources.get(), URI));
    }

    @Test
    void withStringUriNull() {
        assertThatThrownBy(() -> RedisClusterClient.create((String) null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withUri() {
        FastShutdown.shutdown(RedisClusterClient.create(REDIS_URI));
    }

    @Test
    void withUriUri() {
        assertThatThrownBy(() -> RedisClusterClient.create((RedisURI) null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withUriIterable() {
        FastShutdown.shutdown(RedisClusterClient.create(LettuceLists.newList(REDIS_URI)));
    }

    @Test
    void withUriIterableNull() {
        assertThatThrownBy(() -> RedisClusterClient.create((Iterable<RedisURI>) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesWithStringUri() {
        FastShutdown.shutdown(RedisClusterClient.create(TestClientResources.get(), URI));
    }

    @Test
    void clientResourcesWithStringUriNull() {
        assertThatThrownBy(() -> RedisClusterClient.create(TestClientResources.get(), (String) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesNullWithStringUri() {
        assertThatThrownBy(() -> RedisClusterClient.create(null, URI)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesWithUri() {
        FastShutdown.shutdown(RedisClusterClient.create(TestClientResources.get(), REDIS_URI));
    }

    @Test
    void clientResourcesWithUriNull() {
        assertThatThrownBy(() -> RedisClusterClient.create(TestClientResources.get(), (RedisURI) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesWithUriUri() {
        assertThatThrownBy(() -> RedisClusterClient.create(null, REDIS_URI)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesWithUriIterable() {
        FastShutdown.shutdown(RedisClusterClient.create(TestClientResources.get(), LettuceLists.newList(REDIS_URI)));
    }

    @Test
    void clientResourcesWithUriIterableNull() {
        assertThatThrownBy(() -> RedisClusterClient.create(TestClientResources.get(), (Iterable<RedisURI>) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientResourcesNullWithUriIterable() {
        assertThatThrownBy(() -> RedisClusterClient.create(null, REDIS_URIS)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientWithDifferentSslSettings() {
        assertThatThrownBy(() -> RedisClusterClient
                .create(Arrays.asList(RedisURI.create("redis://host1"), RedisURI.create("redis+ssl://host1"))))
                        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientWithDifferentTlsSettings() {
        assertThatThrownBy(() -> RedisClusterClient
                .create(Arrays.asList(RedisURI.create("rediss://host1"), RedisURI.create("redis+tls://host1"))))
                        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clientWithDifferentVerifyPeerSettings() {
        RedisURI redisURI = RedisURI.create("rediss://host1");
        redisURI.setVerifyPeer(false);

        assertThatThrownBy(() -> RedisClusterClient.create(Arrays.asList(redisURI, RedisURI.create("rediss://host1"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
