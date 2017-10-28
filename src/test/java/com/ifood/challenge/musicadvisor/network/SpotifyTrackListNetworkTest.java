package com.ifood.challenge.musicadvisor.network;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ifood.challenge.musicadvisor.enums.TrackGenre;
import com.ifood.challenge.musicadvisor.exception.NetworkException;
import com.ifood.challenge.musicadvisor.network.impl.SpotifyTrackListNetwork;
import com.ifood.challenge.musicadvisor.vo.TrackList;
import com.ifood.challenge.musicadvisor.vo.spotify.AuthenticationToken;
import com.ifood.challenge.musicadvisor.vo.spotify.Tracks;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyTrackListNetworkTest {
	@Mock
	private RestTemplate rest;

	@Autowired
	@InjectMocks
	private SpotifyTrackListNetwork network;

	private static final TrackGenre GENRE = TrackGenre.ROCK;
	private static final String ACCESS_TOKEN = "AUTH_TOKEN";
	private static final String AUTHENTICATION_API_URL = "https://accounts.spotify.com/api/token";
	private static final String RECOMMENDATION_API_URL = "https://api.spotify.com/v1/recommendations?seed_genres=%s";

	private ResponseEntity<AuthenticationToken> authenticationResponse;
	private ResponseEntity<Tracks> restResponse;
	private TrackList methodResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * Testing the suggestTracksForGenre method
	 */
	@Test
	public void suggestTracksForGenreReturnsAValidList() throws NetworkException {
		givenTheAuthenticationsWasOk();
		givenTheRestApiReturnsAValidListWithTheGenre(GENRE);

		whenWeCallTheSuggestTracksForGenreWithGenre(GENRE);

		thenTheMethodShouldReturnsAListOfTheGenre(GENRE);
	}
	
	@Test(expected=NetworkException.class)
	public void suggestTracksForGenreCouldNotBeAuthenticated() throws NetworkException {
		givenTheAuthenticationsWasNotOK();

		whenWeCallTheSuggestTracksForGenreWithGenre(GENRE);

		thenTheMethodShouldThrowsANetworkException();
	}
	
	@Test(expected=NetworkException.class)
	public void suggestTracksForGenreReturnsANetworkError() throws NetworkException {
		givenTheAuthenticationsWasOk();
		givenTheRestApiReturnsANetowkrError(GENRE);

		whenWeCallTheSuggestTracksForGenreWithGenre(GENRE);

		thenTheMethodShouldThrowsANetworkException();
	}

	private void givenTheAuthenticationsWasOk() {
		// building the rest response body
		AuthenticationToken token = new AuthenticationToken();
		token.setAccessToken(ACCESS_TOKEN);

		authenticationResponse = new ResponseEntity<AuthenticationToken>(token, HttpStatus.OK);

		// mocking the rest response
		when(rest.exchange(
				eq(AUTHENTICATION_API_URL), 
				eq(HttpMethod.POST), 
				isA(HttpEntity.class), 
				eq(AuthenticationToken.class)
			)).thenReturn(authenticationResponse);
	}

	private void givenTheRestApiReturnsAValidListWithTheGenre(TrackGenre genre) {
		// building the rest response body
		Tracks tracks = new Tracks();
		tracks.setTracks(new ArrayList<>());
		restResponse = new ResponseEntity<Tracks>(tracks, HttpStatus.OK);

		// mocking the rest response
		String uri = String.format(RECOMMENDATION_API_URL, genre.description());
		when(rest.exchange(
				eq(uri), 
				eq(HttpMethod.GET), 
				isA(HttpEntity.class), 
				eq(Tracks.class)
			)).thenReturn(restResponse);
	}

	private void whenWeCallTheSuggestTracksForGenreWithGenre(TrackGenre genre) throws NetworkException {
		methodResponse = network.suggestTracksForGenre(genre);
	}

	private void thenTheMethodShouldReturnsAListOfTheGenre(TrackGenre genre) {
		assertEquals(genre, methodResponse.getGenre());
	}
	
	private void givenTheAuthenticationsWasNotOK() {
		when(rest.exchange(
				eq(AUTHENTICATION_API_URL), 
				eq(HttpMethod.POST), 
				isA(HttpEntity.class), 
				eq(AuthenticationToken.class)
			)).thenThrow(new RuntimeException("Could not be authenticated"));
	}
	
	private void thenTheMethodShouldThrowsANetworkException() {
		// the exception throw expectation is defined at the method who calls this one
	}
	
	private void givenTheRestApiReturnsANetowkrError(TrackGenre genre) {
		String uri = String.format(RECOMMENDATION_API_URL, genre.description());
		when(rest.exchange(
				eq(uri), 
				eq(HttpMethod.GET), 
				isA(HttpEntity.class), 
				eq(Tracks.class)
			)).thenThrow(new RuntimeException("Network problem"));
	}
}
