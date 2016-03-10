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
 package com.cats.version.httpserver.msg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cats.version.VersionInfo;
import com.cats.version.mgr.VersionMgr;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageVersionUpdateReq;
import com.cats.version.msg.IMessageVersionUpdateRsp;
import com.cats.version.utils.Utils;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class MessageHandlerVersionUpdate extends IMsgHandler
{
	@Override
	public  boolean handleMessage(String strReq, ChannelHandlerContext ctx)
    {
		IMessageVersionUpdateReq updateReq = JSON.parseObject(strReq,  IMessageVersionUpdateReq.class);
		String softName = updateReq.getSoftName();
		VersionInfo latestVersionInfo = VersionMgr.getInstance().getLatestVersionByName(softName);
		if(null != latestVersionInfo)
		{
			List<File> latestFiles = getLatestSoftFiles(latestVersionInfo);
			IMessageVersionUpdateRsp updateRsp = new IMessageVersionUpdateRsp();
			updateRsp.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
			updateRsp.setMsgType(IMessageDef.MSGTYPE_VERSION_UPDATE_RSP);
			updateRsp.setMsgid(IMessageDef.genMsgId());
			updateRsp.setResult(IMessageDef.SUCC);
			updateRsp.setMsgResult(IMessageDef.SUCC);
			updateRsp.setSoftName(updateReq.getSoftName());
			updateRsp.setStartupName(latestVersionInfo.getStartupName());
			
			List<String> pathList = new ArrayList<String>();
			String basePath = Utils.getWorkSpace();
			if(null != latestFiles)
			{
				for (File file : latestFiles)
				{
					pathList.add(file.getAbsolutePath().replace(basePath, ""));
				}
				updateRsp.setUpdateFilePath(pathList);
			}
			String strJsonRsp = JSON.toJSONString(updateRsp);
			sendMessageToClient(strJsonRsp, ctx);
		}
	    return true;
    }

	private List<File> getLatestSoftFiles(final VersionInfo latestVersionInfo)
    {
		String softPath = latestVersionInfo.getPath();
		File filePath = new File(softPath);
		
		List<File> fileList = new ArrayList<>();
		List<File> targetFileList = new ArrayList<>();
		scannFiles(filePath, fileList);
		for (File file : fileList)
		{
			if(ignoreFile(latestVersionInfo, file))
			{
				targetFileList.add(file);
			}
		}
		return targetFileList;
    }
	
	
	private void scannFiles(File filePath, List<File> fileList)
	{
		File[] files = filePath.listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				if(file.isFile())
				{
					fileList.add(file);
				}else
				{
					scannFiles(file, fileList);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		File filePath = new File("/home/xblia2/ReleaseVer/cats_release/ApkInfoToExcel");
		List<File> fileList = new ArrayList<File>();
		new MessageHandlerVersionUpdate().scannFiles(filePath, fileList);
		for (File file : fileList)
		{
			System.out.println(file.getAbsolutePath());
		}
	}

	private boolean ignoreFile(VersionInfo latestVersionInfo, File targetFile)
	{
		String targetFilePath = targetFile.getAbsolutePath();
		List<String> ignoreFiles = latestVersionInfo.getIgnoreFiles();
		String basePath = latestVersionInfo.getPath();
		
		if(null != ignoreFiles && !ignoreFiles.isEmpty())
		{
			for (String ignoreFile : ignoreFiles)
			{
				if(targetFilePath.equals(new File(basePath + File.separator + ignoreFile).getAbsolutePath()))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public  int getType()
    {
	    return IMessageDef.MSGTYPE_VERSION_UPDATE_REQ;
    }

}
