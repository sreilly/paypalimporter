/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.controller;

import org.junit.Before;
import org.junit.Test;

import com.moneydance.apps.md.model.AccountListener;
import com.moneydance.modules.features.paypalimporter.controller.AccountDelegateListener;
import com.moneydance.modules.features.paypalimporter.controller.ViewController;

/**
 * @author Florian J. Breunig
 */
public final class AccountDelegateListenerTest {

    private AccountListener accountListener;

    @Before
    public void setUp() throws Exception {
        ViewController viewController = new ViewControllerMock();
        this.accountListener = new AccountDelegateListener(viewController);
    }

    @Test
    public void testAccountModified() {
        this.accountListener.accountModified(null);
    }

    @Test
    public void testAccountBalanceChanged() {
        this.accountListener.accountBalanceChanged(null);
    }

    @Test
    public void testAccountDeleted() {
        this.accountListener.accountDeleted(null, null);
    }

    @Test
    public void testAccountAdded() {
        this.accountListener.accountAdded(null, null);
    }
}
