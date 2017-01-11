package com.smolnij.research.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.smolnij.research.PathFindingResearch;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(PathFindingResearch.VIRTUAL_WIDTH, PathFindingResearch.VIRTUAL_HEIGHT + PathFindingResearch.PANEL_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PathFindingResearch();
        }
}