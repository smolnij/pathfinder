package com.smolnij.research.desktop;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PathFindingTexturePacker {

    private static final String NON_PACKED_TEXTURES_PATH = "non-packed-textures";
    private static final String ATLAS_PATH = "atlas";
    private static final String ATLAS_NAME = "texturepack";

    public static void packTextures() {
        final TexturePacker.Settings nonTransparentSettings = initTransparentSettings();
        TexturePacker.process(nonTransparentSettings, NON_PACKED_TEXTURES_PATH,
                ATLAS_PATH, ATLAS_NAME);
    }

    private static TexturePacker.Settings initTransparentSettings() {
        final TexturePacker.Settings transparentSettings = new TexturePacker.Settings();
        transparentSettings.filterMin = Texture.TextureFilter.Linear;
        transparentSettings.filterMag = Texture.TextureFilter.Linear;
        transparentSettings.format = Pixmap.Format.RGBA8888;
        return transparentSettings;
    }

    private static TexturePacker.Settings initNonTransparentSettings() {
        final TexturePacker.Settings nonTransparentSettings = new TexturePacker.Settings();

        nonTransparentSettings.filterMin = Texture.TextureFilter.Linear;
        nonTransparentSettings.filterMag = Texture.TextureFilter.Linear;
        nonTransparentSettings.format = Pixmap.Format.RGB565;
        return nonTransparentSettings;
    }

    public static void main(String[] args) {
        final TexturePacker.Settings nonTransparentSettings = initTransparentSettings();
        TexturePacker.process(nonTransparentSettings, NON_PACKED_TEXTURES_PATH,
                ATLAS_PATH, ATLAS_NAME);
    }
}
