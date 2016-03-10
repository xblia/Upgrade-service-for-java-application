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