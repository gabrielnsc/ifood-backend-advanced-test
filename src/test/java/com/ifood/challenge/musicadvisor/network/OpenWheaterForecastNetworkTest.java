package com.ifood.challenge.musicadvisor.network;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ifood.challenge.musicadvisor.exception.CityNotFoundException;
import com.ifood.challenge.musicadvisor.exception.NetworkException;
import com.ifood.challenge.musicadvisor.network.impl.OpenWheaterForecastNetwork;
import com.ifood.challenge.musicadvisor.vo.openwheater.Main;
import com.ifood.challenge.musicadvisor.vo.openwheater.Wheater;

@RunWith(MockitoJUnitRunner.class)
public class OpenWheaterForecastNetworkTest {
	@Mock
	private RestTemplate rest;

	@Autowired
	@InjectMocks
	private OpenWheaterForecastNetwork network;

	private static final String CITY_NAME = "HUSTON";
	private static final float TEMPERATURE = 30f;
	private static final float KELVIN_BASE_TEMPERATURE = 273.15f;
	private static final int LATITUDE = 90;
	private static final int LONGITUDE = 180;

	private float methodResponse;
	private ResponseEntity<Wheater> restResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * Testing getTemperatureFromCity method
	 */
	@Test
	public void getTemperatureFromCityReturnsValidTemperature() throws NetworkException, CityNotFoundException {
		givenTheRestServiceReturnsAValidTemperatureForCity(TEMPERATURE, CITY_NAME);

		whenWeCallTheGetTemperatureFromCityMethodWithName(CITY_NAME);

		thenWeExpectTheMethodToReturnsTheTemperature(TEMPERATURE);
	}

	@Test(expected = CityNotFoundException.class)
	public void getTemperatureFromCityReturnsANotFoundCityError() throws NetworkException, CityNotFoundException {
		givenTheRestServiceReturnsACityNotFoundErrorForCity(CITY_NAME);

		whenWeCallTheGetTemperatureFromCityMethodWithName(CITY_NAME);

		thenWeExpectTheMethodToReturnsACityNotFoundException();
	}

	@Test(expected = NetworkException.class)
	public void getTemperatureFromCityReturnsANetworkError() throws NetworkException, CityNotFoundException {
		givenTheRestServiceReturnsANetworkErrorForCity(CITY_NAME);

		whenWeCallTheGetTemperatureFromCityMethodWithName(CITY_NAME);

		thenWeExpectTheMethodToReturnsANetworkException();
	}

	/*
	 * Testing getTemperatureFromLocation method
	 */
	@Test
	public void getTemperatureFromLocationReturnsAValidTemperature() throws NetworkException {
		givenTheRestServiceReturnsAValidTemperatureForCoordinates(TEMPERATURE, LATITUDE, LONGITUDE);

		whenWeCallTheGetTemperatureFromLocationMethodWithCoordinates(LATITUDE, LONGITUDE);

		thenWeExpectTheMethodToReturnsTheTemperature(TEMPERATURE);
	}

	@Test(expected = NetworkException.class)
	public void getTemperatureFromLocationReturnsANetworkError() throws NetworkException {
		givenTheRestServiceReturnsANetworkErrorForCoordinates(LATITUDE, LONGITUDE);

		whenWeCallTheGetTemperatureFromLocationMethodWithCoordinates(LATITUDE, LONGITUDE);

		thenWeExpectTheMethodToReturnsANetworkException();
	}

	@SuppressWarnings("unchecked")
	private void givenTheRestServiceReturnsAValidTemperatureForCity(float temp, String city) {
		// building the rest response body
		Main main = new Main();
		main.setTemp(KELVIN_BASE_TEMPERATURE + temp);

		Wheater wheater = new Wheater();
		wheater.setMain(main);

		restResponse = new ResponseEntity<Wheater>(wheater, HttpStatus.OK);

		// mocking the rest response
		when(rest.getForEntity(anyString(), any(Class.class))).thenReturn(restResponse);
	}

	private void whenWeCallTheGetTemperatureFromCityMethodWithName(String city)
			throws NetworkException, CityNotFoundException {
		methodResponse = network.getTemperatureFromCity(city);
	}

	private void thenWeExpectTheMethodToReturnsTheTemperature(float temperature) {
		float precision = 0;
		assertEquals(temperature, methodResponse, precision);
	}

	@SuppressWarnings("unchecked")
	private void givenTheRestServiceReturnsACityNotFoundErrorForCity(String city) {
		when(rest.getForEntity(anyString(), any(Class.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	private void thenWeExpectTheMethodToReturnsACityNotFoundException() {
		// the exception throw expectation is defined at the method who calls this one
	}

	@SuppressWarnings("unchecked")
	private void givenTheRestServiceReturnsANetworkErrorForCity(String city) {
		when(rest.getForEntity(anyString(), any(Class.class))).thenThrow(new RuntimeException());
	}

	private void thenWeExpectTheMethodToReturnsANetworkException() {
		// the exception throw expectation is defined at the method who calls this one
	}

	@SuppressWarnings("unchecked")
	private void givenTheRestServiceReturnsAValidTemperatureForCoordinates(float temp, int latitude, int logitude) {
		// building the rest response body
		Main main = new Main();
		main.setTemp(KELVIN_BASE_TEMPERATURE + temp);

		Wheater wheater = new Wheater();
		wheater.setMain(main);

		restResponse = new ResponseEntity<Wheater>(wheater, HttpStatus.OK);

		// mocking the rest response
		when(rest.getForEntity(anyString(), any(Class.class))).thenReturn(restResponse);
	}

	private void whenWeCallTheGetTemperatureFromLocationMethodWithCoordinates(int latitude, int longitude)
			throws NetworkException {
		methodResponse = network.getTemperatureFromLocation(latitude, longitude);
	}

	@SuppressWarnings("unchecked")
	private void givenTheRestServiceReturnsANetworkErrorForCoordinates(int latitude, int longitude) {
		when(rest.getForEntity(anyString(), any(Class.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}
}
