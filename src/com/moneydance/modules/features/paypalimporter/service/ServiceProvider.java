/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentTransactionSearchResultType;

import com.moneydance.apps.md.controller.DateRange;
import com.moneydance.modules.features.paypalimporter.util.Helper;
import com.moneydance.modules.features.paypalimporter.util.Preferences;
import com.paypal.core.Constants;

/**
 * @author Florian J. Breunig
 */
public final class ServiceProvider {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(
            ServiceProvider.class.getName());

    /**
     * The resource in the JAR file to read the settings from.
     */
    private static final String PROPERTIES_RESOURCE =
            "com/moneydance/modules/features/paypalimporter/resources/"
                    + "sdk_config.properties";

    private final ExecutorService executorService;
    private final Preferences prefs;

    public ServiceProvider() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.prefs = Helper.INSTANCE.getPreferences();
    }

    public void callCheckCurrencyService(
            final String username,
            final char[] password,
            final String signature,
            final RequestHandler<CurrencyCodeType> requestHandler) {

        Callable<ServiceResult<CurrencyCodeType>> callable =
                new CheckCurrencyService(
                        this.createService(username, password, signature),
                        this.prefs.getLocale());
        this.createAndExecuteFutureTask(callable, requestHandler);
    }

    public void callTransactionSearchService(
            final String username,
            final char[] password,
            final String signature,
            final DateRange dateRange,
            final CurrencyCodeType currencyCode,
            final boolean isPrimaryCurrency,
            final RequestHandler<PaymentTransactionSearchResultType>
            requestHandler) {

        Callable<ServiceResult<PaymentTransactionSearchResultType>>
        callable = new TransactionSearchService(
                this.createService(username, password, signature),
                currencyCode,
                isPrimaryCurrency,
                dateRange,
                this.prefs.getLocale());

        this.createAndExecuteFutureTask(callable, requestHandler);
    }

    public List<Runnable> shutdownNow() {
        return this.executorService.shutdownNow();
    }

    private PayPalAPIInterfaceServiceService createService(
            final String username,
            final char[] password,
            final String signature) {
        final Properties config = new Properties();
        try {
            InputStream inputStream =
                    Helper.INSTANCE.getInputStreamFromResource(
                            PROPERTIES_RESOURCE);
            config.load(inputStream);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }

        final String prefix = Constants.ACCOUNT_PREFIX + 1;

        config.setProperty(
                prefix + Constants.CREDENTIAL_USERNAME_SUFFIX,
                username);
        config.setProperty(
                prefix + Constants.CREDENTIAL_PASSWORD_SUFFIX,
                String.valueOf(password));
        config.setProperty(
                prefix + Constants.CREDENTIAL_SIGNATURE_SUFFIX,
                signature);

        if (this.prefs.hasProxy()) {
            config.setProperty(
                    Constants.USE_HTTP_PROXY,
                    String.valueOf(this.prefs.hasProxyAuthentication()));
            config.setProperty(
                    Constants.HTTP_PROXY_HOST,
                    String.valueOf(this.prefs.getProxyHost()));
            config.setProperty(
                    Constants.HTTP_PROXY_PORT,
                    String.valueOf(this.prefs.getProxyPort()));
            if (this.prefs.hasProxyAuthentication()) {
                config.setProperty(
                        Constants.HTTP_PROXY_USERNAME,
                        String.valueOf(this.prefs.getProxyUsername()));
                config.setProperty(
                        Constants.HTTP_PROXY_PASSWORD,
                        String.valueOf(this.prefs.getProxyPassword()));
            }
        }
        return new PayPalAPIInterfaceServiceService(config);
    }

    private <V> void createAndExecuteFutureTask(
            final Callable<ServiceResult<V>> callable,
            final RequestHandler<V> requestHandler) {

        final FutureTask<ServiceResult<V>> task =
                new FutureTask<ServiceResult<V>>(callable) {
            @Override
            protected void done() {
                ServiceResult<V> serviceResult = null;
                try {
                    serviceResult = this.get();
                } catch (InterruptedException e) {
                    LOG.log(Level.WARNING, "Thread interrupted", e);
                    serviceResult = new ServiceResult<V>(
                            e.getLocalizedMessage());
                } catch (ExecutionException e) {
                    LOG.log(Level.WARNING, "Task aborted", e.getCause());
                    serviceResult = new ServiceResult<V>(
                            e.getLocalizedMessage());
                } finally {
                    requestHandler.serviceCallFinished(serviceResult);
                }
            }
        };
        this.executorService.submit(task);
    }
}
