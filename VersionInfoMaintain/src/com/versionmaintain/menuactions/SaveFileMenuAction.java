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

import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class SaveFileMenuAction extends IMenuAction
{

	public SaveFileMenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		if(FileDataMgr.getInstance().getData() == null)
		{
			frame.onSaveFile("File not create!");
		}else if(FileDataMgr.getInstance().saveData())
		{
			frame.onSaveFile("Save Success~!");
		}else
		{
			frame.onSaveFile("File save fail, please check.");
		}
    }

}
