package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class CloseFileMenuAction extends IMenuAction
{

	public CloseFileMenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		frame.onCloseFileAction();
    }

}
