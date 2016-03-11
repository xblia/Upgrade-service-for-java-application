/*
 * Copyright 2015 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
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
