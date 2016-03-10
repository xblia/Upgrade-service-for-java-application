package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class ExitMenuAction extends IMenuAction
{

	public ExitMenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		System.exit(0);
    }

}
