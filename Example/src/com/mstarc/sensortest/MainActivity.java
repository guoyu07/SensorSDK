package com.mstarc.sensortest;

import java.util.ArrayList;
import java.util.List;

import net.ldmf.sensorsdk.MSensorListener;
import net.ldmf.sensorsdk.SensorSDK;
import net.ldmf.sensorsdk.SensorValue;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements MSensorListener {
	MainActivity main = this;
	Context context = this;
	Panel p = null;

	List<SensorValue> data = new ArrayList<SensorValue>();

	VAdapter adapter = new VAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("传感器数据监控");

		setContentView(R.layout.activity_main);
		p = new Panel(this);

		p.setSelect();
		p.setTouch();

	}

	@Override
	protected void onResume() {
		super.onResume();
		p.setCount("已检测" + App.getInstance().sensor.getFlagsCount(true)
				+ "个传感器数据，共" + App.getInstance().sensor.allSensors.size() + "个");
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onValuesChanged(int type, float x, float y, float z) {
		String name = SensorSDK.getType(type);

		long now = System.currentTimeMillis();

		SensorValue value = new SensorValue();
		value.setType(type);
		value.setTime(now);
		value.setTimeString(SensorSDK.getNow(now));
		value.setX(x);
		value.setY(y);
		value.setZ(z);

		String result = "时间：" + value.getTimeString() + "\n\t" + name + "{x:"
				+ x + ",y:" + y + ",z:" + "}";

		Log.i("Values", result);

		data.add(value);
		adapter.notifyDataSetChanged();

		p.lst_result.setSelection(adapter.getCount() - 1);
	}

	public class Panel {

		TextView tv_count, tv_touch;
		Button btn_select, btn_copy, btn_clear;

		ListView lst_result;

		public Panel(Activity act) {

			tv_count = (TextView) act.findViewById(R.id.tv_count);
			tv_touch = (TextView) act.findViewById(R.id.tv_touch);

			btn_select = (Button) act.findViewById(R.id.btn_select);
			btn_copy = (Button) act.findViewById(R.id.btn_copy);
			btn_clear = (Button) act.findViewById(R.id.btn_clear);

			lst_result = (ListView) act.findViewById(R.id.lst_result);

			lst_result.setAdapter(adapter);
		}

		public void setTouch() {

			tv_touch.setText("长按此区域，开始录制数据");

			tv_touch.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {

					switch (arg1.getAction()) {
					case MotionEvent.ACTION_DOWN: // 按下
						App.getInstance().sensor.registerSensorsListener();
						App.getInstance().sensor.events.put("Main", main);
						tv_touch.setText("录制数据中...");

						break;
					case MotionEvent.ACTION_UP:// 抬起
						App.getInstance().sensor.unregisterSensorsListener();
						App.getInstance().sensor.events.remove("Main");
						tv_touch.setText("长按此区域，开始录制数据");
						break;
					default:
						break;
					}
					return true;
				}
			});

		}

		public void setCount(String text) {
			tv_count.setText(text);
		}

		public void setSelect() {
			btn_select.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(context,
							SelectSensorActivity.class));
				}
			});

			btn_copy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

					String text = "";
					for (SensorValue value : data) {
						text += "时间：" + value.getTimeString() + "\n"
								+ SensorSDK.getType(value.getType()) + ":{ x:"
								+ value.getX() + " , y:" + value.getY()
								+ " , z:" + value.getZ() + "}\n";
					}

					clipboard.setText(text);

					if (text.length() > 0) {
						if (text.equals(clipboard.getText())) {
							Toast.makeText(main, "复制成功", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(main, "复制失败", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						Toast.makeText(main, "无数据", Toast.LENGTH_SHORT).show();
					}
				}
			});

			btn_clear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					data.clear();
					adapter.notifyDataSetChanged();
				}
			});
		}

	}

	public class VAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public SensorValue getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Panel p = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_result, null);

				p = new Panel(convertView);
				convertView.setTag(p);

			} else {
				p = (Panel) convertView.getTag();
			}

			SensorValue value = getItem(position);
			p.setTime(value.getTimeString());
			p.setValue(SensorSDK.getType(value.getType()) + ":{x:"
					+ value.getX() + ",y:" + value.getY() + ",z:"
					+ value.getZ() + "}");

			return convertView;
		}

		public class Panel {
			TextView tv_time, tv_value;

			public Panel(View view) {
				tv_time = (TextView) view.findViewById(R.id.tv_time);
				tv_value = (TextView) view.findViewById(R.id.tv_value);
			}

			public void setTime(String text) {
				tv_time.setText(text);
			}

			public void setValue(String text) {
				tv_value.setText(text);
			}
		}

	}
}
