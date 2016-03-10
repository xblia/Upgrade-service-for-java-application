package com.versionmaintain.panel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class StatusPanel
{
	private JLabel statusLabel;
	private Timer saveHideTimer = null;
	private JComponent component;
	public StatusPanel()
    {
		component = getBottomPanel();
    }
	
	private JComponent getBottomPanel()
    {
		statusLabel = new JLabel("Ready~!");
		statusLabel.setBorder(new EmptyBorder(5, 15, 5, 5));
	    return statusLabel;
    }

	public JComponent getComponent()
	{
		return this.component;
	}
	
	public void onSaveFile(String info)
    {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm:ss");
		String strDate = sdf.format(new Date());
		statusLabel.setText(info + strDate);
		if(null != saveHideTimer)
		{
			saveHideTimer.cancel();
		}
		saveHideTimer = new Timer();
		saveHideTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				statusLabel.setText("Ready.");
				saveHideTimer = null;
			}
		}, 3000);
    }

}
