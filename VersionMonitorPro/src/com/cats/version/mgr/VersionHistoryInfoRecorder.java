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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cats.version.VersionInfo;
import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.files.LastVersionInfosParser;

/**
 * @author xiaobolx
 * 2016年1月22日
 */
public class VersionHistoryInfoRecorder extends Thread
{
	private static final Object fileLock = new Object();

	public VersionHistoryInfoRecorder()
    {
	    super();
    }
	
	@Override
	public void run()
	{
		synchronized (fileLock)
		{
			FileDataMgr.getInstance().loadBinFileData(true);
			Map<String, List<VersionInfo>> nameToSoftHistories = FileDataMgr
			        .getInstance().getData();
			LastVersionInfosParser infosParser = new LastVersionInfosParser();
			List<VersionInfo> softwareVerInfos = infosParser
			        .getVersionInfo();
			if (null == softwareVerInfos || softwareVerInfos.isEmpty()
			        || null == nameToSoftHistories)
			{
				return;
			}

			for (VersionInfo softwareVerInfo : softwareVerInfos)
			{
				String softName = softwareVerInfo.getAppName();
				boolean isFind = false;
				if(nameToSoftHistories.containsKey(softName))
				{
					List<VersionInfo> softHistoryInfos = nameToSoftHistories.get(softName);
					for (VersionInfo softwareVerHistoryInfo : softHistoryInfos)
                    {
						if(softwareVerHistoryInfo.getVersionCode() == softwareVerInfo.getVersionCode())
						{
							isFind = true;
							break;
						}
                    }
					if(!isFind)
					{
						softHistoryInfos.add(softwareVerInfo);
					}
				}else
				{
					List<VersionInfo> softInfos = new ArrayList<VersionInfo>();
					softInfos.add(softwareVerInfo);
					nameToSoftHistories.put(softName, softInfos);
				}
			}
			FileDataMgr.getInstance().saveData();
			FileDataMgr.getInstance().closeFile();
		}//End synchronized.
	}
	
}
