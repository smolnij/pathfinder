package com.smolnij.research.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smolnij.research.PathFindingResearch;

public class DesktopLauncher {
	public static void main (String[] arg) {
		PathFindingTexturePacker.packTextures();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PathFindingResearch.VIRTUAL_WIDTH;
		config.height = PathFindingResearch.VIRTUAL_HEIGHT + PathFindingResearch.PANEL_HEIGHT;
		new LwjglApplication(new PathFindingResearch(), config);
	}
}
