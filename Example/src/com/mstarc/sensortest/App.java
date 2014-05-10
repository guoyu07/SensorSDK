package com.mstarc.sensortest;

import net.ldmf.sensorsdk.SensorSDK;
import android.app.Application;

public class App extends Application {

	public static App self = null;

	public SensorSDK sensor = null;

	public static App getInstance() {
		if (self == null) {
			self = new App();
		}
		return self;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
		sensor = SensorSDK.getInstance(this);
	}
}
