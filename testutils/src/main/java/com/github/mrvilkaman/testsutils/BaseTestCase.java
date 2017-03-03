package com.github.mrvilkaman.testsutils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by Zahar on 04.04.16.
 */
@SuppressWarnings("ALL")
@RunWith(Runner.class)
public abstract class BaseTestCase {
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void init(){

	}
}
