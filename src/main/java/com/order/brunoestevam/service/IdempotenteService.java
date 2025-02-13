package com.order.brunoestevam.service;

import com.order.brunoestevam.exception.InvalidDataException;

public interface IdempotenteService {

	void validate(String idempotenteKey) throws InvalidDataException;
	void put(String idempotenteKey);
}
