package com.ifood.challenge.musicadvisor.network;

import com.ifood.challenge.musicadvisor.enums.TrackGenre;
import com.ifood.challenge.musicadvisor.exception.NetworkException;
import com.ifood.challenge.musicadvisor.vo.TrackList;

public interface TrackListNetwork {
	/**
	 * Getting a list of tracks based on a music genre
	 * 
	 * @param genre the music genre
	 * @return a {@link TrackList} object with the tracks
	 * @throws NetworkException whenever something went wrong while calling the
	 *            Spotify API
	 */
	TrackList suggestTracksForGenre(TrackGenre genre) throws NetworkException;
}
