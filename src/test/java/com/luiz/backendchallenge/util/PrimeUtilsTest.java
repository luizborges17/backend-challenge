package com.luiz.backendchallenge.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeUtilsTest {

    @Test
    void testIsPrimeWithNegativeNumbers() {
        assertFalse(PrimeUtils.isPrime(-100));
        assertFalse(PrimeUtils.isPrime(-1));
        assertFalse(PrimeUtils.isPrime(0));
        assertFalse(PrimeUtils.isPrime(1));
    }

    @Test
    void testIsPrimeWithSmallPrimes() {
        assertTrue(PrimeUtils.isPrime(2));
        assertTrue(PrimeUtils.isPrime(3));
        assertTrue(PrimeUtils.isPrime(5));
        assertTrue(PrimeUtils.isPrime(7));
        assertTrue(PrimeUtils.isPrime(11));
    }

    @Test
    void testIsPrimeWithSmallNonPrimes() {
        assertFalse(PrimeUtils.isPrime(4));
        assertFalse(PrimeUtils.isPrime(6));
        assertFalse(PrimeUtils.isPrime(8));
        assertFalse(PrimeUtils.isPrime(9));
        assertFalse(PrimeUtils.isPrime(10));
    }

    @Test
    void testIsPrimeWithLargePrime() {
        assertTrue(PrimeUtils.isPrime(7919)); // 1000th prime
    }

    @Test
    void testIsPrimeWithLargeNonPrime() {
        assertFalse(PrimeUtils.isPrime(10000));
    }
}
