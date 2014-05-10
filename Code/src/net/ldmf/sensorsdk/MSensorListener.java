package net.ldmf.sensorsdk;

public interface MSensorListener {

	/**
	 * @category 传感器的值产生了变动
	 * */
	public void onValuesChanged(int type, float x, float y, float z);
}
