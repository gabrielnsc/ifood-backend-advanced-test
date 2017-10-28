package com.ifood.challenge.musicadvisor.network;

import com.ifood.challenge.musicadvisor.exception.CityNotFoundException;
import com.ifood.challenge.musicadvisor.exception.NetworkException;

public interface WheaterForecastNetwork {
	/**
	 * Getting the temperature in Celsius from a given city by its name
	 * 
	 * @param cityName the city name
	 * @return the current city's temperature in Celsius
	 * @throws NetworkException whenever a communication error occurs
	 * @throws CityNotFoundException whenever the city could not be found
	 */
	Float getTemperatureFromCity(String cityName) throws NetworkException, CityNotFoundException;

	/**
	 * Getting the temperature in Celsius from a given city by its name
	 * 
	 * @param latitude the cooridante's latitude
	 * @param longitude the cooridante's longitude
	 * @return the current coodinate's temperature in Celsius
	 * @throws NetworkException whenever a communication error occurs
	 */
	Float getTemperatureFromLocation(Integer latitude, Integer longitude) throws NetworkException;
}
