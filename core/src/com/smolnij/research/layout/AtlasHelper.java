package com.smolnij.research.layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public enum AtlasHelper {
    INSTANCE;

    private TextureAtlas textureAtlas;

    public TextureAtlas getTextureAtlas() {
        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/texturepack.atlas"));
        }
        return textureAtlas;
    }

    public SpriteDrawable createSpriteDrawable(final String regionName) {
        return new SpriteDrawable(getTextureAtlas().createSprite(regionName));
    }

    public Texture getTexture(final String textureName) {
        return textureAtlas.createSprite(textureName).getTexture();
    }

    public void disposeAtlas() {
        if (textureAtlas != null) {
            textureAtlas.dispose();
        }
    }
}
