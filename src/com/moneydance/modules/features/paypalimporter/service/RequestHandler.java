/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.service;

/**
 * @author Florian J. Breunig
 * @param <V>
 */
public interface RequestHandler<V> {
    void serviceCallFinished(final ServiceResult<V> v);
}
