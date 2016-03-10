package com.versionmaintain.menuactions;

import java.awt.event.ActionListener;

import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public abstract class IMenuAction implements ActionListener
{
	protected VersionMaintainMainFrame frame;

	public IMenuAction(VersionMaintainMainFrame frame)
    {
	    this.frame = frame;
    }
}
