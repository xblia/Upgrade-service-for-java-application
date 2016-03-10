package com.versionmaintain.files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cats.version.VersionInfo;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class FileDataMgr
{
	private static FileDataMgr dataMgr = new FileDataMgr();
	private IVersionInfoFile versionInfoFile;
	
	private FileDataMgr()
	{
	}
	
	public static FileDataMgr getInstance()
	{
		return dataMgr;
	}
	
	public synchronized Map<String, List<VersionInfo>> loadData(File file, EnFileType fileType)
	{
		versionInfoFile = genVersionInfoFile(file, fileType);
		if(versionInfoFile.loadFile())
		{
			return versionInfoFile.getData();
		}else
		{
			this.closeFile();
			return null;
		}
	}
	
	public synchronized Map<String, List<VersionInfo>> loadBinFileData(boolean createFileIfNotExists)
	{
		File file = new File(System.getProperty("user.dir") + File.separator + Resource.BIN_FILE_NAME);
		if (!file.exists())
		{
			if(createFileIfNotExists)
			{
				newVersionInfoFile(file, EnFileType.enBinFile);
			}
			return null;
		} else
		{
			return FileDataMgr.getInstance().loadData(file, EnFileType.enBinFile);
		}
	}

	public synchronized Map<String, List<VersionInfo>> getData()
	{
		if(null != versionInfoFile)
		{
			return versionInfoFile.getData();
		}
		return null;
	}
	
	public synchronized List<VersionInfo> getSoftwareData(String softwareName)
	{
		if(null != versionInfoFile)
		{
			return versionInfoFile.getSoftwareData(softwareName);
		}
		return null;
	}

	public synchronized void closeFile()
    {
		this.versionInfoFile = null;
    }
	
	public synchronized boolean saveData()
	{
		if(this.versionInfoFile != null)
		{
			return versionInfoFile.saveData();
		}
		return false;
	}

	public synchronized void newVersionInfoFile(File file, EnFileType fileType)
    {
		if(!file.exists())
		{
			try
            {
	            file.createNewFile();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}
		versionInfoFile = genVersionInfoFile(file, fileType);
    }
	
	private IVersionInfoFile genVersionInfoFile(File file, EnFileType fileType)
    {
		IVersionInfoFile versionInfoFile = null;
		if(fileType == EnFileType.enBinFile)
		{
			versionInfoFile = new VersionBinFile(file);
		}else if(fileType == EnFileType.enXmlFile)
		{
			versionInfoFile = new VersionXmlFile(file);
		}
	    return versionInfoFile;
    }
}
