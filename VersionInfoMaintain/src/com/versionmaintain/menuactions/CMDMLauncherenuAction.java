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