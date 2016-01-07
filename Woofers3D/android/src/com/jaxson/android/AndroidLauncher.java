package com.jaxson.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jaxson.woofers3d.Woofers3D;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Woofers3D game = new Woofers3D();
		initialize(game, game.toAndroidConfig());
	}
}
