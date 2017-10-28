package com.ifood.challenge.musicadvisor.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ifood.challenge.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilsTest {
	private static final String VALID_STRING = "valid";

	private String str;
	private boolean result;

	/*
	 * Testing the isValid method
	 */
	@Test
	public void isValidMethodWithValidString() {
		givenAValidString();

		whenWeCallTheIsValidMethod();

		thenWeExpectItToBeTrue();
	}

	@Test
	public void isValidMethodWithEmptyString() {
		givenAnEmptyString();

		whenWeCallTheIsValidMethod();

		thenWeExpectItToBeFalse();
	}

	@Test
	public void isValidMethodWithNullString() {
		givenANullString();

		whenWeCallTheIsValidMethod();

		thenWeExpectItToBeFalse();
	}

	private void givenAValidString() {
		this.str = VALID_STRING;
	}

	private void givenAnEmptyString() {
		this.str = "";
	}

	private void givenANullString() {
		this.str = null;
	}

	private void whenWeCallTheIsValidMethod() {
		result = StringUtils.isValid(this.str);
	}

	private void thenWeExpectItToBeTrue() {
		assertTrue(result);
	}

	private void thenWeExpectItToBeFalse() {
		assertFalse(result);
	}
}
