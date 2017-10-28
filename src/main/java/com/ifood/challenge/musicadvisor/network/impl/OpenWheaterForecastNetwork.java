package com.ifood.challenge.musicadvisor.network.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ifood.challenge.musicadvisor.exception.CityNotFoundException;
import com.ifood.challenge.musicadvisor.exception.NetworkException;
import com.ifood.challenge.musicadvisor.network.WheaterForecastNetwork;
import com.ifood.challenge.musicadvisor.vo.openwheater.Wheater;

@Component
public class OpenWheaterForecastNetwork implements WheaterForecastNetwork {
	@Value("${open_wheater.api_key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	private static final float KELVIN_BASE_TEMPERATURE = 273.15f;
	private static final String CITY_TEMPERATURE_API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=%s&q=%s";
	private static final String COORDINATE_TEMPERATURE_API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=%s&lat=%d&lon=%d";

	/*
	 * @see com.ifood.challenge.musicadvisor.network.WheaterForecastNetwork#
	 * getTemperatureFromCity(java.lang.String)
	 */
	@Override
	public Float getTemperatureFromCity(String cityName) throws NetworkException, CityNotFoundException {
		Float temperature = null;
		try {
			// building the URI
			final String uri = String.format(CITY_TEMPERATURE_API_URL, apiKey, cityName);

			// performing the request
			ResponseEntity<Wheater> wheater = restTemplate.getForEntity(uri, Wheater.class);

			// getting the temperature
			temperature = convertKelvinToCelsius(wheater.getBody().getMain().getTemp());
		} catch (HttpClientErrorException ex) {
			throw new CityNotFoundException("Invalid city name");
		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
		return temperature;
	}

	/*
	 * @see com.ifood.challenge.musicadvisor.network.WheaterForecastNetwork#
	 * getTemperatureFromLocation(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Float getTemperatureFromLocation(Integer latitude, Integer longitude) throws NetworkException {
		Float temperature = null;
		try {
			// building the URI
			final String uri = String.format(COORDINATE_TEMPERATURE_API_URL, apiKey, latitude, longitude);

			// performing the request
			ResponseEntity<Wheater> wheater = restTemplate.getForEntity(uri, Wheater.class);

			// getting the temperature
			temperature = convertKelvinToCelsius(wheater.getBody().getMain().getTemp());
		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
		return temperature;
	}

	/**
	 * Convert a given temperature in Kelvin to Celsius
	 * 
	 * @param kelvinTemperature the temperature in Kelvins
	 * @return the temperature in Celcius
	 */
	private float convertKelvinToCelsius(float kelvinTemperature) {
		return kelvinTemperature - KELVIN_BASE_TEMPERATURE;
	}
}
