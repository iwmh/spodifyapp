package com.iwmh.spodifyapp

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(InstrumentedUnitTest::class, InstrumentedMediumTest::class)
class UnitTestSuite