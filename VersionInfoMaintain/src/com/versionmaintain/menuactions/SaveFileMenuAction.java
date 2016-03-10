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
