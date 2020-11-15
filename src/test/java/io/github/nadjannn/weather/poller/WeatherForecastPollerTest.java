package io.github.nadjannn.weather.poller;

import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherForecastPollerTest {

    @SpyBean
    private WeatherForecastPoller poller;

    @Test
    public void pollerShouldBeCalled() {
        await()
                .atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> {
                    verify(poller, atLeast(1)).pollWeatherForecast();
                });
    }

}
