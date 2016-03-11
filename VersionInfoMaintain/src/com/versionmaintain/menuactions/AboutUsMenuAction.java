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

import javax.swing.JOptionPane;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class AboutUsMenuAction extends IMenuAction
{

	public AboutUsMenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		StringBuffer message = new StringBuffer();
		message.append("\r\n");
		message.append("Version Info Maintain tool.\r\n");
		message.append("Author: xiaobox.li@intel.com.\r\n");
		message.append("Power By Intel SSG DRD CATS Team!. 2016-01 All rights reserved~!\r\n");
		message.append("\r\n");
		JOptionPane.showMessageDialog(null, message, "Version Info Maintain", JOptionPane.INFORMATION_MESSAGE);
    }
}