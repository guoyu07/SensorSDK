package net.ldmf.sensorsdk;

public class MSensor {

	// Field descriptor #8 I API3 加速度传感器
	public static final int TYPE_ACCELEROMETER = 1;

	// Field descriptor #8 I API3 地磁传感器
	public static final int TYPE_MAGNETIC_FIELD = 2;

	// Field descriptor #8 I (deprecated) API3 方向传感器
	@java.lang.Deprecated
	public static final int TYPE_ORIENTATION = 3;

	// Field descriptor #8 I API3 陀螺仪传感器
	public static final int TYPE_GYROSCOPE = 4;

	// Field descriptor #8 I API3 亮度传感器
	public static final int TYPE_LIGHT = 5;

	// Field descriptor #8 I API3 压力传感器
	public static final int TYPE_PRESSURE = 6;

	// Field descriptor #8 I (deprecated) API3 温度传感器
	@java.lang.Deprecated
	public static final int TYPE_TEMPERATURE = 7;

	// Field descriptor #8 I API3 近程传感器
	public static final int TYPE_PROXIMITY = 8;

	// Field descriptor #8 I API9
	public static final int TYPE_GRAVITY = 9;

	// Field descriptor #8 I API9
	public static final int TYPE_LINEAR_ACCELERATION = 10;

	// Field descriptor #8 I API9
	public static final int TYPE_ROTATION_VECTOR = 11;

	// Field descriptor #8 I API14
	public static final int TYPE_RELATIVE_HUMIDITY = 12;

	// Field descriptor #8 I API14
	public static final int TYPE_AMBIENT_TEMPERATURE = 13;

	// Field descriptor #8 I API18
	public static final int TYPE_MAGNETIC_FIELD_UNCALIBRATED = 14;

	// Field descriptor #8 I API15
	public static final int TYPE_GAME_ROTATION_VECTOR = 15;

	// Field descriptor #8 I API18
	public static final int TYPE_GYROSCOPE_UNCALIBRATED = 16;

	// Field descriptor #8 I API18
	public static final int TYPE_SIGNIFICANT_MOTION = 17;

	// Field descriptor #8 I API19
	public static final int TYPE_STEP_DETECTOR = 18;

	// Field descriptor #8 I API19
	public static final int TYPE_STEP_COUNTER = 19;

	// Field descriptor #8 I API19
	public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 20;

	// Field descriptor #8 I
	public static final int TYPE_ALL = -1;

}
