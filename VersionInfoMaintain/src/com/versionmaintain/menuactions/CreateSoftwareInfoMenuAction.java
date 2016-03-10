package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.panel.VersionMaintainMainFrame;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class CreateSoftwareInfoMenuAction implements ActionListener
{

	private VersionMaintainMainFrame versionMaintainMainFrame;
	public CreateSoftwareInfoMenuAction(
            VersionMaintainMainFrame versionMaintainMainFrame)
    {
		this.versionMaintainMainFrame = versionMaintainMainFrame;
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(null == FileDataMgr.getInstance().getData())
		{
			JOptionPane.showMessageDialog(null, "You need to open or create a file", "Version Info Maintain warning.", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String softwareName = JOptionPane.showInputDialog(null, "Please Input New Software Name ", "Software Version info Maintain", JOptionPane.QUESTION_MESSAGE);
		if(softwareName == null || softwareName.trim().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Input Error, Please Try Again Later.~~~~", "Software Version info Maintain", JOptionPane.WARNING_MESSAGE);
			return;
		}
		versionMaintainMainFrame.onNewSoftwareInfo(softwareName.trim());
	}

}
