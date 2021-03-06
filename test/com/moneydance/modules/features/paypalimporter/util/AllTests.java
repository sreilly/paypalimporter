/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Florian J. Breunig
 */
@RunWith(Suite.class)
@SuiteClasses({
    HelperTest.class,
    LocalizableTest.class,
    LogFormatterTest.class,
    PreferencesTest.class,
    TrackerTest.class })
public final class AllTests {
    // no test cases
}
