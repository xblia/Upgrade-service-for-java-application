package com.versionmaintain.files;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public class VersionFileFilter
{

	public static FileFilter getFileter()
    {
		FileFilter filter = new FileFilter()
		{
			
			@Override
			public String getDescription()
			{
				return "Version Info File(*.xml  *.bin)";
			}
			
			@Override
			public boolean accept(File f)
			{
				if(f.getName().endsWith("xml") || f.getName().endsWith("bin"))
				{
					return true;
				}
				return false;
			}
		};
	    return filter;
    }
	
}
