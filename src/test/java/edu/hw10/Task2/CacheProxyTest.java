package edu.hw10.Task2;

import edu.hw10.annotations.Cache;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CacheProxyTest {
    private int count;

    private interface CacheTestMem {
        @Cache(persist = false)
        Integer testing(Integer num);
    }

    private interface CacheTestFile {
        @Cache(persist = true)
        Integer testing(Integer num);
    }

    private interface WithoutCache {
        Integer testing(Integer num);
    }

    @Test
    void memoryCaching() {
        //given
        count = 0;

        CacheTestMem cacheTest = new CacheTestMem() {
            @Override
            public Integer testing(Integer num) {
                count++;
                return num + 1;
            }
        };
        //when
        CacheTestMem test = CacheProxy.create(cacheTest, CacheTestMem.class);
        //when
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void fileCaching() {
        //given
        count = 0;

        CacheTestFile cacheTest = new CacheTestFile() {
            @Override
            public Integer testing(Integer num) {
                count++;
                return num + 1;
            }
        };
        //when
        CacheTestFile test = CacheProxy.create(cacheTest, CacheTestFile.class);
        //then
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void withoutCaching() {
        //given
        count = 0;
        WithoutCache cacheTest = new WithoutCache() {
            @Override
            public Integer testing(Integer num) {
                count++;
                return num + 1;
            }
        };
        //when
        WithoutCache test = CacheProxy.create(cacheTest, WithoutCache.class);
        //then
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(test.testing(1)).isEqualTo(2);
        assertThat(count).isEqualTo(2);
    }
}
