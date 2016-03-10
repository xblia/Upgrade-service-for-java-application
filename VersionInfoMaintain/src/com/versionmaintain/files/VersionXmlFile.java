package com.versionmaintain.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cats.version.VersionCfgParseAndSave;
import com.cats.version.VersionInfo;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class VersionXmlFile extends IVersionInfoFile
{
	public VersionXmlFile(File file)
    {
	    super(file);
	    this.file = file;
    }

	@Override
    public synchronized boolean loadFile()
	{
		List<VersionInfo> versionInfos = new VersionCfgParseAndSave().getVersionInfo(file.getAbsolutePath());
		if(versionInfos == null)
		{
			return false;
		}
		Map<String, List<VersionInfo>> nameToVerdata = new HashMap<String, List<VersionInfo>>();
		for (VersionInfo versionInfo : versionInfos)
        {
			List<VersionInfo> versionInfoList = new ArrayList<VersionInfo>();
			versionInfoList.add(versionInfo);
			nameToVerdata.put(versionInfo.getAppName(), versionInfoList);
        }
		this.data = nameToVerdata;
		return true;
	}
	
	@Override
	public synchronized boolean saveData()
	{
		VersionCfgParseAndSave xmlParseAndSave = new VersionCfgParseAndSave();
		
		List<VersionInfo> versionInfos = new ArrayList<VersionInfo>();
		Set<Entry<String, List<VersionInfo>>> entries = this.data.entrySet();
		for (Entry<String, List<VersionInfo>> entry : entries)
        {
			List<VersionInfo> versionInfoList = entry.getValue();
			if(!versionInfoList.isEmpty())
			{
				versionInfos.add(versionInfoList.get(0));
			}
        }
		return xmlParseAndSave.saveVersionInfo(versionInfos, file.getAbsolutePath());
	}
}
