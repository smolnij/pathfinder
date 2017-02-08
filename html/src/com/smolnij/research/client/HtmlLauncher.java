package com.smolnij.research.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.smolnij.research.PathFindingResearchApp;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                final GwtApplicationConfiguration config = new GwtApplicationConfiguration(PathFindingResearchApp.VIRTUAL_WIDTH,
                        PathFindingResearchApp.VIRTUAL_HEIGHT + PathFindingResearchApp.PANEL_HEIGHT);

                config.preferFlash = false;
                return config;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PathFindingResearchApp();
        }
}