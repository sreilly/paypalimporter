/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.util;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.moneydance.modules.features.paypalimporter.util.LogFormatter;

/**
 * @author Florian J. Breunig
 */
public final class LogFormatterTest {

    @Test
    public void testFormatLogRecord() {
        LogFormatter logFormatter = new LogFormatter();

        LogRecord logRecord = new LogRecord(Level.CONFIG, "stub message");
        assertThat(logFormatter.format(logRecord), notNullValue());

        logRecord.setThrown(new Exception());
        assertThat(logFormatter.format(logRecord), notNullValue());
    }

}
