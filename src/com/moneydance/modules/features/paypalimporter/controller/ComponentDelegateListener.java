package com.moneydance.modules.features.paypalimporter.controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.model.AccountListener;

/**
 * @author Florian J. Breunig
 */
final class ComponentDelegateListener extends ComponentAdapter {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG = Logger.getLogger(
            ComponentDelegateListener.class.getName());

    private final FeatureModuleContext context;
    private final AccountListener      accountListener;

    ComponentDelegateListener(
            final FeatureModuleContext argContext,
            final ViewController argViewController) {
        Validate.notNull(argContext, "context must not be null");
        Validate.notNull(argViewController,
                "view controller must not be null");
        this.context = argContext;
        this.accountListener = new AccountDelegateListener(argViewController);
    }

    @Override
    public void componentShown(final ComponentEvent event) {
        LOG.config("Show wizard");
        this.context.getRootAccount().addAccountListener(this.accountListener);
    }

    @Override
    public void componentHidden(final ComponentEvent event) {
        LOG.config("Hide wizard");
        this.context.getRootAccount().removeAccountListener(
                this.accountListener);
    }
}
