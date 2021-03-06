/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.presentation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.controller.StubMoneydanceGUIFactory;
import com.moneydance.modules.features.paypalimporter.presentation.WizardHandler;
/**
 * @author Florian J. Breunig
 */
public final class WizardHandlerTest {

    private WizardHandler wizardHandler;

    @Before
    public void setUp() throws Exception {
        StubMoneydanceGUIFactory factory = new StubMoneydanceGUIFactory(new StubContextFactory());
        this.wizardHandler = new WizardHandler(
                null,
                factory.getMoneydanceGUI(),
                new Observer() {

                    @Override
                    public void update(final Observable observable, final Object obj) {
                        // ignore
                    }
                });
    }

    @Test
    public void testActionPerformed() {
        this.wizardHandler.actionPerformed(
                new ActionEvent(new Object(), 0, "stub command"));
        this.wizardHandler.actionPerformed(
                new ActionEvent(this.wizardHandler.btnHelp, 0, "stub command"));
        this.wizardHandler.actionPerformed(
                new ActionEvent(this.wizardHandler.btnProceed, 0, "stub command"));
        this.wizardHandler.actionPerformed(
                new ActionEvent(this.wizardHandler.btnCancel, 0, "stub command"));
    }

    @Test
    public void testWindowClosing() {
        this.wizardHandler.windowClosing(null);
    }

    @Test
    public void testGetInputData() {
        this.wizardHandler.actionPerformed(
                new ActionEvent(this.wizardHandler.btnProceed, 0, null));
        assertThat(this.wizardHandler.getInputData(), notNullValue());
    }
}
