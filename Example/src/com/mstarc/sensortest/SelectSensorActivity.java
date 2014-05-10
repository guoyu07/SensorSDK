package com.mstarc.sensortest;

import net.ldmf.sensorsdk.SensorSDK;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectSensorActivity extends ListActivity {

	Context context = this;
	Adapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("选择传感器");

		adapter = new Adapter();

		System.out.println("Count:" + adapter.getCount());

		this.setListAdapter(adapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Sensor s = App.getInstance().sensor.allSensors.get(arg2);

				if (App.getInstance().sensor.flags.get(s.getType())) {
					App.getInstance().sensor.flags.put(s.getType(), false);
				} else {
					App.getInstance().sensor.flags.put(s.getType(), true);
				}

				adapter.notifyDataSetChanged();
			}
		});
	}

	public class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return App.getInstance().sensor.allSensors.size();
		}

		@Override
		public Sensor getItem(int arg0) {
			return App.getInstance().sensor.allSensors.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {

			Panel p = null;

			if (arg1 == null) {
				arg1 = LayoutInflater.from(context).inflate(
						R.layout.item_sensor, null);

				p = new Panel(arg1);
				arg1.setTag(p);

			} else {
				p = (Panel) arg1.getTag();
			}

			p.setPanel(getItem(arg0));

			return arg1;
		}

		public class Panel {

			TextView tv_sensor, tv_status;
			LinearLayout cell;

			public Panel(View view) {
				cell = (LinearLayout) view.findViewById(R.id.cell);

				tv_sensor = (TextView) view.findViewById(R.id.tv_sensor);
				tv_status = (TextView) view.findViewById(R.id.tv_status);
			}

			public void setPanel(Sensor s) {
				setSensor(s);
				setStatus(s);
			}

			public void setSensor(Sensor s) {
				StringBuffer sb = new StringBuffer();

				sb.append("Name:" + s.getName() + "\n");
				sb.append("Version:" + s.getVersion() + "\n");
				sb.append("Resolution:" + s.getResolution() + "\n");
				sb.append("注解：" + SensorSDK.getType(s.getType()));

				tv_sensor.setText("" + sb);
			}

			public void setStatus(Sensor s) {
				if (App.getInstance().sensor.flags.get(s.getType())) {
					tv_status.setText("已检测");
					cell.setBackgroundColor(Color.GREEN);
				} else {
					tv_status.setText("未检测");
					cell.setBackgroundColor(Color.WHITE);
				}
			}

		}

	}
}
