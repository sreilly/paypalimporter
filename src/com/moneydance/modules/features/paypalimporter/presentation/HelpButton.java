/*
 * PayPal Importer for Moneydance - http://my-flow.github.io/paypalimporter/
 * Copyright (C) 2013 Florian J. Breunig. All rights reserved.
 */

package com.moneydance.modules.features.paypalimporter.presentation;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.moneydance.modules.features.paypalimporter.util.Helper;

/**
 * @author Florian J. Breunig
 */
final class HelpButton extends JButton {

    private static final long serialVersionUID = 1L;

    HelpButton() {
        final Image image = Helper.INSTANCE.getHelpImage();
        this.setIcon(new ImageIcon(image));
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }
}
