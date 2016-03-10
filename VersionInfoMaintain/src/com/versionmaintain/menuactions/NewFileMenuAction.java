package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;

import com.versionmaintain.panel.VersionMaintainMainFrame;

public class NewFileMenuAction extends IMenuAction
{

	public NewFileMenuAction(VersionMaintainMainFrame versionMaintainMainFrame)
    {
		super(versionMaintainMainFrame);
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		frame.onNewFile();
	}

}
