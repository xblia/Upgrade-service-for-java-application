/*
 * Copyright 2015 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
 package com.cats.version.mgr;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.List;

import com.cats.version.IProfileChangeListener;
import com.cats.version.MonitorProfileChange;
import com.cats.version.VersionCfgParseAndSave;
import com.cats.version.VersionInfo;
import com.cats.version.utils.Utils;

/**
 * @author xblia2
 * Jun 9, 2015
 */
public class VersionMgr implements IProfileChangeListener
{
	private static VersionMgr mgr = new VersionMgr();
	
	private List<VersionInfo> versionInfo = new ArrayList<VersionInfo>();
	
	public static VersionMgr getInstance()
	{
		return mgr;
	}
	
	
	public VersionMgr()
	{
		versionInfo = new VersionCfgParseAndSave().getVersionInfo();
		new VersionHistoryInfoRecorder().start();
		new MonitorProfileChange(Utils.getWorkSpace(), this).startMonitor();
	}
	
	public VersionInfo getLatestVersionByName(String softName)
	{
		for (VersionInfo version : versionInfo)
        {
	        if(version.getAppName().equals(softName))
	        {
	        	return version;
	        }
        }
		return null;
	}


	@Override
    public void notifyChange(Object object, Kind<?> kind)
    {
		int iDeleCount = deleteFolderZipFile();
		System.out.println("have change " + object.toString() + " delete zip file count: " + iDeleCount );
		versionInfo = new VersionCfgParseAndSave().getVersionInfo();
		new VersionHistoryInfoRecorder().start();
    }


	private synchronized int deleteFolderZipFile()
    {
		int iCount = 0;
		File workSpaceDir = new File(Utils.getWorkSpace());
		File[] softDirs = workSpaceDir.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.isDirectory();
			}
		});
		
		if(softDirs != null)
		{
			System.out.println("Delete file: " );
			for (File file : softDirs)
            {
	            File zipFile = new File(file.getAbsolutePath() + ".zip");
	            if(zipFile.exists())
	            {
	            	System.out.println(zipFile.getAbsolutePath());
	            	zipFile.delete();
	            	iCount++;
	            }
            }
		}
		return iCount;
    }
}
