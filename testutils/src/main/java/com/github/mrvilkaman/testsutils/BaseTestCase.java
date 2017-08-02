package com.github.mrvilkaman.testsutils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Zahar on 04.04.16.
 */
@SuppressWarnings("ALL")
@RunWith(MockitoJUnitRunner.Silent.class)
public abstract class BaseTestCase {
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void init(){

	}
}
