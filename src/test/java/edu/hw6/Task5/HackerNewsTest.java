package edu.hw6.Task5;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class HackerNewsTest {
    @Test
    public void hackerNewsTopStoriesTest() {
        //expect
        assertThat(HackerNews.hackerNewsTopStories().length).isNotEqualTo(0);
    }

    @Test
    public void newsTest() {
        //expect
        assertThat(HackerNews.news(1)).isNotNull().isNotEqualTo("");
    }
}
