/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.controller;

import java.util.Observer;

import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import com.moneydance.apps.md.model.Account;
import com.moneydance.apps.md.model.CurrencyType;

/**
 * @author Florian J. Breunig, Florian.Breunig@my-flow.com
 */
public interface ViewController extends Observer {

    void startWizard();

    void cancel();

    void proceed();

    /**
     * @param text error message to be displayed (can be null)
     * @param key identifier of the related input field (can be null)
     */
    void unlock(
            final String text,
            final Object key);

    void currencyChecked(
            final CurrencyType currencyType,
            final CurrencyCodeType currencyCode,
            final boolean isPrimaryCurrency);

    void transactionsImported(
            final Account account);

    void showHelp();

    void refreshAccounts(
            final int accountId);
}
