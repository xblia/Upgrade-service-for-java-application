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
 package com.cats.version.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cats.version.VersionInfo;
import com.versionmaintain.files.FileDataMgr;

/**
 * @author xblia2
 * Jan 21, 2016
 */
public class URLVersionHistoryService extends ICommonService
{
	
	public URLVersionHistoryService()
	{
	}

	@Override
	public String getServiceName()
	{
		return "versionhistoryservice";
	}

	@Override
	public boolean execute(String []servicePara, ChannelHandlerContext ctx, FullHttpRequest request)
	{
		if(servicePara.length < 2)
		{
			return false;
		}
		String strSoftName = servicePara[1];
		if(FileDataMgr.getInstance().getData() == null)
		{
			FileDataMgr.getInstance().loadBinFileData(false);
		}
		List<VersionInfo> softwareVerHistoryInfos = FileDataMgr.getInstance().getSoftwareData(strSoftName);
		
		String strJsonInfo = "[]";
		if(softwareVerHistoryInfos != null)
		{
			strJsonInfo = JSON.toJSONString(softwareVerHistoryInfos);
		}
		
		sendMessageToClient(strJsonInfo, ctx);
		return true;
	}
}
