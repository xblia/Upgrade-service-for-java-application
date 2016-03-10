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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cats.version.VersionInfo;
import com.cats.version.VersionInfoDetail;
import com.cats.version.mgr.IUserOpt;
import com.cats.version.mgr.VersionMgr;
import com.cats.version.msg.BroadcastMsg;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageVersionCheckReq;
import com.cats.version.msg.IMessageVersionCheckRsp;
import com.cats.version.ui.UserInfo;
import com.cats.version.ui.UserTableModel;
import com.cats.version.utils.Utils;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class MessageHandlerVersionCheck extends IMsgHandler
{

	@Override
	public boolean handleMessage(String strReq, ChannelHandlerContext ctx)
	{
		
		IMessageVersionCheckReq checkReq = JSON.parseObject(strReq, IMessageVersionCheckReq.class);
		if(null != checkReq)
		{
			String checkRsp = fireCheckRequestGenRsp(checkReq);
			sendMessageToClient(checkRsp, ctx);
			return true;
		}
		return false;
	}

	private String fireCheckRequestGenRsp(IMessageVersionCheckReq checkReq)
    {
		VersionInfo latestVersionInfo = VersionMgr.getInstance().getLatestVersionByName(checkReq.getSoftName());
		if(latestVersionInfo != null)
		{
			IMessageVersionCheckRsp checkRsp = new IMessageVersionCheckRsp();
			checkRsp.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
			checkRsp.setMsgid(IMessageDef.genMsgId());
			checkRsp.setMsgType(IMessageDef.MSGTYPE_VERSION_CHCECK_RSP);
			checkRsp.setMsgResult(IMessageDef.SUCC);
			checkRsp.setSoftName(checkReq.getSoftName());
			checkRsp.setCurrVersionCode(checkReq.getCurrVersionCode());
			checkRsp.setCurrVersionName(checkReq.getCurrVersionName());
			checkRsp.setLatestVersionCode(latestVersionInfo.getVersionCode());
			checkRsp.setLatestVersionName(latestVersionInfo.getVersion());
			checkRsp.setNeedUpdate(IMessageDef.NO);
			
			if(checkReq.getCurrVersionCode() < latestVersionInfo.getVersionCode())
			{
				checkRsp.setNeedUpdate(IMessageDef.YES);
				List<VersionInfoDetail> detail = latestVersionInfo.getDetails();
				checkRsp.setVersionInfoDetail(detail);
			}
			
			Map<Integer, Object> interactiveOpt = updateUI(checkReq, latestVersionInfo);
			if(interactiveOpt.get(IUserOpt.OPT_BROADCAST) != null)
			{
				checkRsp.setBroadcastMsg((BroadcastMsg)interactiveOpt.get(IUserOpt.OPT_BROADCAST));
			}
			return JSON.toJSONString(checkRsp);
		}
		return null;
    }

	private Map<Integer, Object> updateUI(IMessageVersionCheckReq checkReq, VersionInfo latestVersionInfo)
    {
		UserInfo userInfo = UserTableModel.getInstance().getUser(checkReq.getClientName(), checkReq.getClientIp(), checkReq.getSoftName());
		if(null == userInfo)
		{
			userInfo = new UserInfo();
		}
		userInfo.setClientName(checkReq.getClientName());
		userInfo.setIp(checkReq.getClientIp());
		userInfo.setSoftName(checkReq.getSoftName());
		userInfo.setLatestVersion(latestVersionInfo.getVersion());
		userInfo.setClientVersion(checkReq.getCurrVersionName());
		userInfo.setLastHearbeat(Utils.getCurrenTime());
		UserTableModel.getInstance().addUser(userInfo);
		
		Map<Integer, Object> interactiveOpt = new HashMap<>();
		BroadcastMsg broadcastMsg = userInfo.getBroadcastMsg();
		if(null != broadcastMsg)
		{
			userInfo.emptyBroadcastmsg();
			interactiveOpt.put(IUserOpt.OPT_BROADCAST, broadcastMsg);
		}
		return interactiveOpt;
    }

	@Override
	public int getType()
	{
		return IMessageDef.MSGTYPE_VERSION_CHCECK_REQ;
	}

}
