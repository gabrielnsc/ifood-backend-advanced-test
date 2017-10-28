package com.ifood.challenge.musicadvisor.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.ifood.challenge.musicadvisor.enums.TrackGenre;
import com.ifood.challenge.musicadvisor.exception.BusinessException;
import com.ifood.challenge.musicadvisor.exception.CityNotFoundException;
import com.ifood.challenge.musicadvisor.exception.CoordinatesNotFoundException;
import com.ifood.challenge.musicadvisor.exception.NetworkException;
import com.ifood.challenge.musicadvisor.network.TrackListNetwork;
import com.ifood.challenge.musicadvisor.network.WheaterForecastNetwork;
import com.ifood.challenge.musicadvisor.service.impl.WheaterTrackAdvisorServiceImpl;
import com.ifood.challenge.musicadvisor.vo.TrackList;

@RunWith(MockitoJUnitRunner.class)
public class WheaterTrackAdvisorServiceTest {
	@Mock
	private WheaterForecastNetwork weatherNetwork;

	@Mock
	private TrackListNetwork trackNetwork;

	@Autowired
	@InjectMocks
	private WheaterTrackAdvisorServiceImpl service;

	private static final float HIGH_TEMPERATURE = 40.0f;
	private static final float COOL_TEMPERATURE = 20.0f;
	private static final float CHILL_TEMPERATURE = 12.0f;
	private static final float FREEZING_TEMPERATURE = 5.0f;
	private static final int VALID_LATITUDE = 45;
	private static final int INVALID_LATITUDE = 100;
	private static final int VALID_LONGITUDE = 90;
	private static final int INVALID_LONGITUDE = 200;
	private static final String CITY = "Huston";

	private TrackList methodResult;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * Tests for adviseTracksByCityName method
	 */
	@Test
	public void adviseTracksByCityNameReturnsAPartyListForHotTemperatures()
			throws BusinessException, CityNotFoundException, NetworkException {
		givenTheWheaterAPIReturnsAHotTemperatureForCity(CITY);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.PARTY);

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.PARTY);
	}

	@Test
	public void adviseTracksByCityNameReturnsAPopListForCoolTemperatures()
			throws BusinessException, CityNotFoundException, NetworkException {
		givenTheWheaterAPIReturnsACoolTemperatureForCity(CITY);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.POP);

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.POP);
	}

	@Test
	public void adviseTracksByCityNameReturnsARockListForChillTemperatures()
			throws BusinessException, CityNotFoundException, NetworkException {
		givenTheWheaterAPIReturnsAChillTemperatureForCity(CITY);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.ROCK);

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.ROCK);
	}

	@Test
	public void adviseTracksByCityNameReturnsAClassicalListForFreezingTemperatures()
			throws BusinessException, CityNotFoundException, NetworkException {
		givenTheWheaterAPIReturnsAFreezingTemperatureForCity(CITY);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.CLASSICAL);

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.CLASSICAL);
	}

	@Test(expected = BusinessException.class)
	public void adviseTracksByCityNameWhenWheaterAPIThrowsANetworkException()
			throws BusinessException, NetworkException, CityNotFoundException {
		givenTheWheaterAPIForCityNameThrowsANetworkException();

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheABusinessException();
	}

	@Test(expected = BusinessException.class)
	public void adviseTracksByCityNameWhenTrackAPIThrowsANetworkException()
			throws BusinessException, CityNotFoundException, NetworkException {
		givenTheWheaterAPIReturnsAValidValue();
		givenTheTrackAPIForCityNameThrowsANetworkException();

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheABusinessException();
	}

	@Test(expected = CityNotFoundException.class)
	public void adviseTracksByCityNameWhenWheaterAPIThrowsACityNotFoundException()
			throws BusinessException, NetworkException, CityNotFoundException {
		givenTheWheaterAPIThrowsACityNotFoundException();

		whenWeCallTheAdviseTracksByCityNameMethod(CITY);

		thenWeExpectTheACityNotFoundException();
	}

	/*
	 * Tests for adviseTracksByLocation method
	 */
	@Test
	public void adviseTracksByLocationReturnsAPartyListForHotTemperatures()
			throws NetworkException, BusinessException, CoordinatesNotFoundException {
		givenTheWheaterAPIReturnsAHotTemperatureForLocation(VALID_LATITUDE, VALID_LONGITUDE);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.PARTY);

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.PARTY);
	}

	@Test
	public void adviseTracksByLocationReturnsAPopListForCoolTemperatures()
			throws NetworkException, BusinessException, CoordinatesNotFoundException {
		givenTheWheaterAPIReturnsACoolTemperatureForLocation(VALID_LATITUDE, VALID_LONGITUDE);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.POP);

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.POP);
	}

	@Test
	public void adviseTracksByLocationReturnsARockListForChillTemperatures()
			throws NetworkException, BusinessException, CoordinatesNotFoundException {
		givenTheWheaterAPIReturnsAChillTemperatureForLocation(VALID_LATITUDE, VALID_LONGITUDE);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.ROCK);

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.ROCK);
	}

	@Test
	public void adviseTracksByLocationReturnsAClassicalListForFreezingTemperatures()
			throws NetworkException, BusinessException, CoordinatesNotFoundException {
		givenTheWheaterAPIReturnsAFreezingTemperatureForLocation(VALID_LATITUDE, VALID_LONGITUDE);
		givenTheTrackAPIReturnsATrackListForGenre(TrackGenre.CLASSICAL);

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheReturnedListToBeTheGenre(TrackGenre.CLASSICAL);
	}

	@Test(expected = BusinessException.class)
	public void adviseTracksByLocationWhenWheaterAPIThrowsANetworkException() throws BusinessException, CoordinatesNotFoundException, NetworkException {
		givenTheWheaterAPIForLocationThrowsANetworkException();

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheABusinessException();
	}

	@Test(expected = BusinessException.class)
	public void adviseTracksByLocationWhenTrackAPIThrowsANetworkException() throws NetworkException, BusinessException, CoordinatesNotFoundException {
		givenTheWheaterAPIForLocationThrowsANetworkException();
		givenTheTrackAPIForCityNameThrowsANetworkException();

		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheABusinessException();
	}

	@Test(expected = CoordinatesNotFoundException.class)
	public void adviseTracksByLocationWithInvalidLatitude() throws BusinessException, CoordinatesNotFoundException {
		whenWeCallTheAdviseTracksByLocationMethod(INVALID_LATITUDE, VALID_LONGITUDE);

		thenWeExpectTheMethodToThrowACoordinatesNotFoundException();
	}

	@Test(expected = CoordinatesNotFoundException.class)
	public void adviseTracksByLocationWithInvalidLongitude() throws BusinessException, CoordinatesNotFoundException {
		whenWeCallTheAdviseTracksByLocationMethod(VALID_LATITUDE, INVALID_LONGITUDE);

		thenWeExpectTheMethodToThrowACoordinatesNotFoundException();
	}

	@Test(expected = CoordinatesNotFoundException.class)
	public void adviseTracksByLocationWithInvalidLatitudeAndLongitude()
			throws BusinessException, CoordinatesNotFoundException {
		whenWeCallTheAdviseTracksByLocationMethod(INVALID_LATITUDE, INVALID_LONGITUDE);

		thenWeExpectTheMethodToThrowACoordinatesNotFoundException();
	}

	private void givenTheWheaterAPIReturnsAHotTemperatureForCity(String city)
			throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(city)).thenReturn(HIGH_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsACoolTemperatureForCity(String city)
			throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(city)).thenReturn(COOL_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsAChillTemperatureForCity(String city)
			throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(city)).thenReturn(CHILL_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsAFreezingTemperatureForCity(String city)
			throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(city)).thenReturn(FREEZING_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsAHotTemperatureForLocation(int lat, int lon)
			throws NetworkException {
		when(weatherNetwork.getTemperatureFromLocation(lat, lon)).thenReturn(HIGH_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsACoolTemperatureForLocation(int lat, int lon)
			throws NetworkException {
		when(weatherNetwork.getTemperatureFromLocation(lat, lon)).thenReturn(COOL_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsAChillTemperatureForLocation(int lat, int lon)
			throws NetworkException {
		when(weatherNetwork.getTemperatureFromLocation(lat, lon)).thenReturn(CHILL_TEMPERATURE);
	}

	private void givenTheWheaterAPIReturnsAFreezingTemperatureForLocation(int lat, int lon)
			throws NetworkException {
		when(weatherNetwork.getTemperatureFromLocation(lat, lon)).thenReturn(FREEZING_TEMPERATURE);
	}

	private void givenTheTrackAPIReturnsATrackListForGenre(TrackGenre genre) throws NetworkException {
		TrackList tracks = new TrackList(genre, new ArrayList<>());
		when(trackNetwork.suggestTracksForGenre(genre)).thenReturn(tracks);
	}

	private void whenWeCallTheAdviseTracksByCityNameMethod(String city) throws BusinessException, CityNotFoundException {
		methodResult = service.adviseTracksByCityName(city);
	}

	private void whenWeCallTheAdviseTracksByLocationMethod(int lat, int lon)
			throws BusinessException, CoordinatesNotFoundException {
		methodResult = service.adviseTracksByLocation(lat, lon);
	}

	private void thenWeExpectTheReturnedListToBeTheGenre(TrackGenre genre) {
		assertEquals(methodResult.getGenre(), genre);
	}

	private void givenTheWheaterAPIForCityNameThrowsANetworkException() throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(any(String.class))).thenThrow(new NetworkException("Network error"));
	}
	
	private void givenTheWheaterAPIForLocationThrowsANetworkException() throws NetworkException {
		when(weatherNetwork.getTemperatureFromLocation(any(Integer.class), any(Integer.class))).thenThrow(new NetworkException("Network error"));
	}

	private void givenTheWheaterAPIThrowsACityNotFoundException() throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(any(String.class)))
				.thenThrow(new CityNotFoundException("City not found error"));
	}

	private void givenTheWheaterAPIReturnsAValidValue() throws NetworkException, CityNotFoundException {
		when(weatherNetwork.getTemperatureFromCity(any(String.class))).thenReturn(CHILL_TEMPERATURE);
	}

	private void givenTheTrackAPIForCityNameThrowsANetworkException() throws NetworkException {
		when(trackNetwork.suggestTracksForGenre(any(TrackGenre.class))).thenThrow(new NetworkException("Network error"));
	}

	private void thenWeExpectTheABusinessException() {
		// the exception throw expectation is defined at the method who calls this one
	}

	private void thenWeExpectTheACityNotFoundException() {
		// the exception throw expectation is defined at the method who calls this one
	}

	private void thenWeExpectTheMethodToThrowACoordinatesNotFoundException() {
		// the exception throw expectation is defined at the method who calls this one
	}
}
