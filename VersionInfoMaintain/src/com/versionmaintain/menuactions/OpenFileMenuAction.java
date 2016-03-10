package com.versionmaintain.menuactions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.cats.version.VersionInfo;
import com.versionmaintain.files.EnFileType;
import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.files.VersionFileFilter;
import com.versionmaintain.panel.VersionMaintainMainFrame;
import com.versionmaintain.utils.VersionServiceUtils;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class OpenFileMenuAction extends IMenuAction
{

	public OpenFileMenuAction(VersionMaintainMainFrame frame)
    {
	    super(frame);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		StringBuilder errorInfo = new StringBuilder();
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileFilter(VersionFileFilter.getFileter());
		chooser.showOpenDialog(frame);
		File file = chooser.getSelectedFile();
		EnFileType fileType = VersionServiceUtils.getFileType(file);
		if(null != file)
		{
			boolean isLoadSucc = true;
			if(null != fileType)
			{
				Map<String, List<VersionInfo>> data = FileDataMgr.getInstance().loadData(file, fileType);
				if(null == data)
				{
					isLoadSucc = false;
				}else
				{
					frame.onLoadFile(file, fileType, data);
				}
			}
			else
			{
				isLoadSucc = false;
				errorInfo.append("Unknow File Type: " + file.getAbsolutePath());
				errorInfo.append("\n");
			}
			
			if(!isLoadSucc)
			{
				errorInfo.append("Class abs path: " + VersionInfo.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				errorInfo.append("\n");
				JOptionPane.showMessageDialog(null, "Load fail, Please check the file data formatter~!\n"+errorInfo.toString(), "Load binaray file.", JOptionPane.WARNING_MESSAGE);
			}
		}
    }
}
