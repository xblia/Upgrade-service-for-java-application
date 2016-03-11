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
 package com.versionmaintain.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cats.version.VersionInfo;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public abstract class IVersionInfoFile
{
	protected File file;
	protected Map<String, List<VersionInfo>> data = new HashMap<String, List<VersionInfo>>();
	public IVersionInfoFile(File file)
    {
	    super();
	    this.file = file;
    }

    public abstract boolean loadFile();
	
	public abstract boolean saveData();
	
	public Map<String, List<VersionInfo>> getData()
	{
		return data;
	}
	
	public List<VersionInfo> getSoftwareData(String softwareName)
	{
		if(null == softwareName)
		{
			return null;
		}
		if(this.data.containsKey(softwareName))
		{
			return data.get(softwareName);
		}else
		{
			List<VersionInfo> softwareInfos = new ArrayList<VersionInfo>();
			this.data.put(softwareName, softwareInfos);
			return softwareInfos;
		}
	}
}
