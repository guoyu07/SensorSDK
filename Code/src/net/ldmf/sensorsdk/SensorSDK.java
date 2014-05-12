package net.ldmf.sensorsdk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.SparseBooleanArray;

public class SensorSDK {

	Context context;

	private static SensorSDK sdk = null;

	public static SensorSDK getInstance(Context context) {
		if (sdk == null) {
			sdk = new SensorSDK(context);
		}
		return sdk;
	}

	private SensorSDK(Context context) {

		this.context = context;

		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		allSensors = sm.getSensorList(Sensor.TYPE_ALL);// 获得传感器列表

		s = sm.getDefaultSensor(Sensor.TYPE_ALL);

		initListener();

		printSensors();
	}

	private static boolean isDebug = true;

	// 监听状态
	public SparseBooleanArray flags = new SparseBooleanArray();
	// 事件回调
	public HashMap<String, MSensorListener> events = new HashMap<String, MSensorListener>();

	/**
	 * @category 所有传感器
	 * */
	public List<Sensor> allSensors = new ArrayList<Sensor>();
	public Sensor s;
	public SensorManager sm = null;
	public SensorEventListener mListener = null;

	public void registerSensorsListener() {
		registerSensorsListener(SensorManager.SENSOR_DELAY_GAME);
	}

	public void registerSensorsListener(int delay) {
		for (Sensor s : allSensors) {
			boolean sf = flags.get(s.getType());
			if (sf) {
				int type = s.getType();
				s = sm.getDefaultSensor(type);
				sm.registerListener(mListener, s, delay);
			}
		}
	}

	public void unregisterSensorsListener() {
		sm.unregisterListener(mListener);
	}

	public void printSensors() {
		for (Sensor s : allSensors) {
			out("name:" + s.getName() + ",type=" + s.getType());
		}

	}

	public void initListener() {

		mListener = new SensorEventListener() {
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}

			public void onSensorChanged(SensorEvent event) {
				int type = event.sensor.getType();

				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];

				boolean isFlag = flags.get(type);

				out("" + getType(type) + ":" + x + "," + y + "," + z);

				if (isFlag) {
					for (String key : events.keySet()) {
						MSensorListener listener = events.get(key);
						if (listener != null) {
							listener.onValuesChanged(type, x, y, z);
						}
					}
				}

			}

		};
	}

	/**
	 * @category 检测指定传感器是否存在
	 * */
	public boolean isSensorExist(int type) {
		boolean exist = false;
		for (Sensor s : allSensors) {
			if (s.getType() == type) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	public int getFlagsCount(boolean flag) {
		return getFlagsCountBySensor(flag);
	}

	public int getFlagsCountByFlags(boolean flag) {
		int count = 0;
		for (int i = 0; i < flags.size(); i++) {
			int key = flags.keyAt(0);
			boolean sf = flags.get(key);
			if (sf == flag) {
				count++;
			}
		}

		return count;
	}

	public int getFlagsCountBySensor(boolean flag) {
		int count = 0;
		for (Sensor s : allSensors) {
			boolean sf = flags.get(s.getType());
			if (sf == flag) {
				count++;
			}
		}

		return count;
	}

	public static String getNow() {
		Calendar c = Calendar.getInstance();
		return getNow(c.getTimeInMillis());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getNow(long time) {
		long ms = time % 1000;
		Date date = new Date(time);
		SimpleDateFormat dateformat2 = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss." + ms + "秒");
		return dateformat2.format(date);
	}

	@SuppressWarnings("deprecation")
	public static String getType(int type) {
		String name = "未知传感器";
		switch (type) {
		case MSensor.TYPE_ACCELEROMETER:// API3 加速度传感器
			name = "加速度传感器";
			break;
		case MSensor.TYPE_AMBIENT_TEMPERATURE:// API14
			name = "AMBIENT_TEMPERATURE";
			break;
		case MSensor.TYPE_GAME_ROTATION_VECTOR:// API15
			name = "GAME_ROTATION_VECTOR";
			break;
		case MSensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:// API19
			name = "GEOMAGNETIC_ROTATION_VECTOR";
			break;
		case MSensor.TYPE_GRAVITY:// API9
			name = "GRAVITY";
			break;
		case MSensor.TYPE_GYROSCOPE:// API3 陀螺仪传感器
			name = "陀螺仪传感器";
			break;
		case MSensor.TYPE_GYROSCOPE_UNCALIBRATED:// API18
			name = "GYROSCOPE_UNCALIBRATED";
			break;
		case MSensor.TYPE_LIGHT:// API3 亮度传感器
			name = "亮度传感器";
			break;
		case MSensor.TYPE_LINEAR_ACCELERATION:// API9
			name = "LINEAR_ACCELERATION";
			break;
		case MSensor.TYPE_MAGNETIC_FIELD:// API3 地磁传感器
			name = "地磁传感器";
			break;
		case MSensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:// API18
			name = "MAGNETIC_FIELD_UNCALIBRATED";
			break;
		case MSensor.TYPE_ORIENTATION:// 方向传感器
			name = "方向传感器";
			break;
		case MSensor.TYPE_PRESSURE:// API3 压力传感器
			name = "压力传感器";
			break;
		case MSensor.TYPE_PROXIMITY:// API3 近程传感器
			name = "近程传感器";
			break;
		case MSensor.TYPE_RELATIVE_HUMIDITY:// API14
			name = "RELATIVE_HUMIDITY";
			break;
		case MSensor.TYPE_ROTATION_VECTOR:// API9
			name = "ROTATION_VECTOR";
			break;
		case MSensor.TYPE_SIGNIFICANT_MOTION:// API18
			name = "SIGNIFICANT_MOTION";
			break;
		case MSensor.TYPE_STEP_COUNTER:// API19
			name = "STEP_COUNTER";
			break;
		case MSensor.TYPE_STEP_DETECTOR:// API19
			name = "STEP_DETECTOR";
			break;
		case MSensor.TYPE_TEMPERATURE:// API3 温度传感器
			name = "温度传感器";
			break;
		}

		return name;
	}

	public static void out(String text) {
		if (isDebug) {
			Log.d("Sensor", text);
		}
	}
}
