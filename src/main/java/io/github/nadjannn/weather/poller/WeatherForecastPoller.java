package io.github.nadjannn.weather.poller;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@CommonsLog
public class WeatherForecastPoller<T> {

    @Autowired
    private WeatherForecastPollerService<T> weatherForecastPollerService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}", initialDelay = 0)
    public void pollWeatherForecast() {
        try {
            log.info("Poller process started");
            weatherForecastPollerService.pollForecasts();
        } catch (Exception e) {
            log.error("Poller has error", e);
        } finally {
            log.info("Poller process ended");
        }
    }

}
