package com.ifood.challenge.musicadvisor.controller;

import static org.junit.Assert.assertTrue;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ifood.challenge.musicadvisor.enums.TrackGenre;
import com.ifood.challenge.musicadvisor.exception.BusinessException;
import com.ifood.challenge.musicadvisor.exception.CityNotFoundException;
import com.ifood.challenge.musicadvisor.exception.CoordinatesNotFoundException;
import com.ifood.challenge.musicadvisor.service.TrackAdvisorService;
import com.ifood.challenge.musicadvisor.vo.TrackList;

@RunWith(MockitoJUnitRunner.class)
public class TrackAdvisorControllerTest {
	@Mock
	private TrackAdvisorService service;

	@Autowired
	@InjectMocks
	private TrackAdvisorController controller;

	private ResponseEntity<?> response;
	private TrackList tracks;

	private static final String CITY_NAME = "Huston";
	private static final int LATITUDE = 90;
	private static final int LONGITUDE = 180;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * Testing the getTracksBasedByCityName method
	 */
	@Test
	public void getTracksBasedByCityNameWithValidCityName() throws BusinessException, CityNotFoundException {
		givenTheAdviseTracksByCityNameReturnsResults(CITY_NAME);

		whenWeCallTheGetTracksBasedByCityNameAPI(CITY_NAME);

		thenWeExpectAnOKResponse();
	}

	@Test
	public void getTracksBasedByCityNameWithEmptyName() throws BusinessException, CityNotFoundException {
		whenWeCallTheGetTracksBasedByCityNameAPIWithEmptyCityName();

		thenWeExpectABadRequestResponse();
	}

	@Test
	public void getTracksBasedByCityNameWithNullName() throws BusinessException, CityNotFoundException {
		whenWeCallTheGetTracksBasedByCityNameAPIWithNullCityName();

		thenWeExpectABadRequestResponse();
	}

	@Test
	public void getTracksBasedByCityNameWithInvalidCityName() throws BusinessException, CityNotFoundException {
		givenTheAdviseTracksByCityNameThrowsAnInvalidCityException(CITY_NAME);

		whenWeCallTheGetTracksBasedByCityNameAPI(CITY_NAME);

		thenWeExpectANotFOundResponse();
	}

	@Test
	public void getTracksBasedByCityNameWithInternalServerError() throws BusinessException, CityNotFoundException {
		givenTheAdviseTracksByCityNameThrowsABusinessException(CITY_NAME);

		whenWeCallTheGetTracksBasedByCityNameAPI(CITY_NAME);

		thenWeExpectAInternalServerErrorResponse();
	}

	/*
	 * Testing the getTracksBasedByCoordinates method
	 */
	@Test
	public void getTracksBasedByCoordinatesWithValidCoordinates()
			throws BusinessException, CoordinatesNotFoundException {
		givenTheAdviseTracksByLocationReturnsResults(LATITUDE, LONGITUDE);

		whenWeCallTheGetTracksBasedByCoordinatesAPI(LATITUDE, LONGITUDE);

		thenWeExpectAnOKResponse();
	}

	@Test
	public void getTracksBasedByCoordinatesWithNullLatitude() throws BusinessException, CityNotFoundException {
		whenWeCallTheGetTracksBasedByCoordinatesAPIWithEmptyLatitude(LONGITUDE);

		thenWeExpectABadRequestResponse();
	}

	@Test
	public void getTracksBasedByCoordinatesWithNullLongitude() throws BusinessException, CityNotFoundException {
		whenWeCallTheGetTracksBasedByCoordinatesAPIWithEmptyLongitude(LATITUDE);

		thenWeExpectABadRequestResponse();
	}

	@Test
	public void getTracksBasedByCoordinatesWithInvalidCoordinates()
			throws BusinessException, CoordinatesNotFoundException {
		givenTheAdviseTracksByCoordinatesThrowsAnCoordinatesNotFoundException(LATITUDE, LONGITUDE);

		whenWeCallTheGetTracksBasedByCoordinatesAPI(LATITUDE, LONGITUDE);

		thenWeExpectANotFOundResponse();
	}

	@Test
	public void getTracksBasedByCoordinatesWithInternalServerError()
			throws BusinessException, CoordinatesNotFoundException {
		givenTheAdviseTracksByCoordinatesThrowsABusinessException(LATITUDE, LONGITUDE);

		whenWeCallTheGetTracksBasedByCoordinatesAPI(LATITUDE, LONGITUDE);

		thenWeExpectAInternalServerErrorResponse();
	}

	private void whenWeCallTheGetTracksBasedByCityNameAPI(String city) {
		this.response = controller.getTracksBasedByCityName(city);
	}

	private void whenWeCallTheGetTracksBasedByCoordinatesAPI(int lat, int lon) {
		this.response = controller.getTracksBasedByCoordinates(lat, lon);
	}

	private void whenWeCallTheGetTracksBasedByCityNameAPIWithEmptyCityName() {
		this.response = controller.getTracksBasedByCityName("");
	}

	private void whenWeCallTheGetTracksBasedByCoordinatesAPIWithEmptyLatitude(int longitude) {
		this.response = controller.getTracksBasedByCoordinates(null, longitude);
	}

	private void whenWeCallTheGetTracksBasedByCoordinatesAPIWithEmptyLongitude(int latitude) {
		this.response = controller.getTracksBasedByCoordinates(latitude, null);
	}

	private void whenWeCallTheGetTracksBasedByCityNameAPIWithNullCityName() {
		this.response = controller.getTracksBasedByCityName(null);
	}

	private void givenTheAdviseTracksByCityNameReturnsResults(String city)
			throws BusinessException, CityNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByCityName(city)).thenReturn(tracks);
	}

	private void givenTheAdviseTracksByLocationReturnsResults(int lat, int lon)
			throws BusinessException, CoordinatesNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByLocation(lat, lon)).thenReturn(tracks);
	}

	private void givenTheAdviseTracksByCityNameThrowsAnInvalidCityException(String city)
			throws BusinessException, CityNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByCityName(city)).thenThrow(new CityNotFoundException("Invalid city name"));
	}

	private void givenTheAdviseTracksByCoordinatesThrowsAnCoordinatesNotFoundException(int lat, int lon)
			throws BusinessException, CoordinatesNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByLocation(lat, lon)).thenThrow(new CoordinatesNotFoundException("Invalid cooridnates"));
	}

	private void givenTheAdviseTracksByCityNameThrowsABusinessException(String city)
			throws BusinessException, CityNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByCityName(city)).thenThrow(new BusinessException("Internal server error"));
	}

	private void givenTheAdviseTracksByCoordinatesThrowsABusinessException(int lat, int lon)
			throws BusinessException, CoordinatesNotFoundException {
		this.tracks = new TrackList(TrackGenre.PARTY, new ArrayList<>());
		when(service.adviseTracksByLocation(lat, lon)).thenThrow(new BusinessException("Internal server error"));
	}

	private void thenWeExpectANotFOundResponse() {
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}

	private void thenWeExpectAInternalServerErrorResponse() {
		assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	private void thenWeExpectAnOKResponse() {
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
	}

	private void thenWeExpectABadRequestResponse() {
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}
}
