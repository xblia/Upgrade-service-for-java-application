package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class DeleteSoftwareInfoMenuAction implements ActionListener
{

	private VersionMaintainMainFrame versionMaintainMainFrame;
	public DeleteSoftwareInfoMenuAction(
            VersionMaintainMainFrame versionMaintainMainFrame)
    {
		this.versionMaintainMainFrame = versionMaintainMainFrame;
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		versionMaintainMainFrame.onDelSoftwareInfo();
	}

}
