/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.util;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.modules.features.paypalimporter.util.Tracker;

/**
 * @author Florian J. Breunig
 */
public final class TrackerTest {

    private Tracker tracker;

    @Before
    public void setUp() {
        this.tracker = new Tracker(0);
    }

    @Test
    public void testTrack() {
        for (Tracker.EventName eventName : Tracker.EventName.values()) {
            this.tracker.track(eventName);
        }
    }

}
