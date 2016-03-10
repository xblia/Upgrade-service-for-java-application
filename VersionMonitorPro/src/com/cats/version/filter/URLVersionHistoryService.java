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
