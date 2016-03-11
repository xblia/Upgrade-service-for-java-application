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
 package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class CMDMLauncherenuAction extends IMenuAction
{

	public CMDMLauncherenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		startupCmd();
    }
	
	public static void startupCmd(String... args)
	{
		try
		{
			Runtime.getRuntime().exec("cmd /c start cmd");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}