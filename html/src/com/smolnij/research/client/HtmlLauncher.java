package com.smolnij.research.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.smolnij.research.PathFindingResearchApp;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(PathFindingResearchApp.VIRTUAL_WIDTH, PathFindingResearchApp.VIRTUAL_HEIGHT + PathFindingResearchApp.PANEL_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PathFindingResearchApp();
        }
}