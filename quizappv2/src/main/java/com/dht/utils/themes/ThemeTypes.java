/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.utils.themes;

import javafx.scene.Scene;

/**
 *
 * @author admin
 */
public enum ThemeTypes {
    DEFAULT {
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setTheme(new DefaultFactory());
            ThemeManager.applyTheme(scene);
        }
    }, DARK {
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setTheme(new DarkFactory());
            ThemeManager.applyTheme(scene);
        }
    }, LIGHT {
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setTheme(new LightFactory());
            ThemeManager.applyTheme(scene);
        }
    };
    
    public abstract void updateTheme(Scene scene);
}
