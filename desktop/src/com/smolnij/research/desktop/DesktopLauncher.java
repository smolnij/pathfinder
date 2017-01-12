package com.smolnij.research.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smolnij.research.PathFindingResearchApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		PathFindingTexturePacker.packTextures();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PathFindingResearchApp.VIRTUAL_WIDTH;
		config.height = PathFindingResearchApp.VIRTUAL_HEIGHT + PathFindingResearchApp.PANEL_HEIGHT;
		new LwjglApplication(new PathFindingResearchApp(), config);
	}
}
