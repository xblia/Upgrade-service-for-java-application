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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cats.version.VersionInfo;
import com.versionmaintain.JTitleLabel;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class BaseInfoPanel implements KeyListener
{
	private Component component;
	private JTextField appAbsDirField;
	private JTextField startupNameField;
	private VersionInfo currEditVersionInfo;
	
	public BaseInfoPanel()
    {
		component = genVersinBaseInfoPanel();
    }
	
	private Component genVersinBaseInfoPanel()
	{
		JLabel appDirLabel = new JTitleLabel("App Dir");
		JLabel startupAppLabel = new JTitleLabel("StartupName");
		appAbsDirField = new JTextField(20);
		startupNameField = new JTextField(20);
		appAbsDirField.addKeyListener(this);
		startupNameField.addKeyListener(this);
		appAbsDirField.setPreferredSize(new Dimension(-1, 30));
		startupNameField.setPreferredSize(new Dimension(-1, 30));
		
		JPanel versionBaseInfoPanel = new JPanel();
		versionBaseInfoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints();
		versionBaseInfoPanel.setLayout(bagLayout);
		
		bagConstraints.gridx=0;
		bagConstraints.gridy=0;
		versionBaseInfoPanel.add(appDirLabel, bagConstraints);
		bagConstraints.gridx=1;
		bagConstraints.gridy=0;
		bagConstraints.weightx = 1;
		bagConstraints.fill = GridBagConstraints.HORIZONTAL;
		versionBaseInfoPanel.add(appAbsDirField, bagConstraints);
		bagConstraints.gridx=0;
		bagConstraints.gridy=1;
		bagConstraints.weightx = 0;
		versionBaseInfoPanel.add(startupAppLabel, bagConstraints);
		bagConstraints.gridx=1;
		bagConstraints.gridy=1;
		bagConstraints.weightx = 1;
		bagConstraints.fill = GridBagConstraints.HORIZONTAL;
		versionBaseInfoPanel.add(startupNameField, bagConstraints);
		
		return versionBaseInfoPanel;
	}
	
	public void initVersionBaseInfo(VersionInfo softwarInfo)
    {
		this.currEditVersionInfo = softwarInfo;
		if(this.currEditVersionInfo != null)
		{
			this.appAbsDirField.setText(this.currEditVersionInfo.getPath());
			this.startupNameField.setText(this.currEditVersionInfo.getStartupName());
		}
    }
	
	public Component getBaseInfoComponent()
	{
		return this.component;
	}

	@Override
    public void keyTyped(KeyEvent e)
    {
	    
    }

	@Override
    public void keyPressed(KeyEvent e)
    {
	    
    }

	@Override
    public void keyReleased(KeyEvent e)
    {
		if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		
		if(null == this.currEditVersionInfo)
		{
			return;
		}
		if(e.getSource() == appAbsDirField)
		{
			String path = this.appAbsDirField.getText();
			if(null != path && !path.trim().isEmpty())
			{
				this.currEditVersionInfo.setPath(path);
			}
		}
		else if(e.getSource() == startupNameField)
		{
			String startupName = startupNameField.getText();
			if(null != startupName && !startupName.trim().isEmpty())
			{
				this.currEditVersionInfo.setStartupName(startupName);
			}
		}
    }

	public void emptyContent()
    {
		this.currEditVersionInfo = null;
		this.appAbsDirField.setText("");
		this.startupNameField.setText("");
    }
}
