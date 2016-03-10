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
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageGetOnlineUserReq;
import com.cats.version.msg.IMessageGetOnlineUserRsp;
import com.cats.version.ui.UserInfo;
import com.cats.version.ui.UserTableModel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author xblia2
 * Jul 22, 2015
 */
public class MessageHandlerGetOnlineUser extends IMsgHandler
{

	@Override
	public boolean handleMessage(String strReq, ChannelHandlerContext ctx)
	{
		IMessageGetOnlineUserReq getOnlineReq = JSON.parseObject(strReq, IMessageGetOnlineUserReq.class);
		if(null != getOnlineReq)
		{
			String checkRsp = fireRequestGenRsp(getOnlineReq);
			sendMessageToClient(checkRsp, ctx);
			return true;
		}
		return false;
	}

	private String fireRequestGenRsp(IMessageGetOnlineUserReq request)
    {
		List<UserInfo> userTable = UserTableModel.getInstance().getAllusers();
		IMessageGetOnlineUserRsp getUserRsp = new IMessageGetOnlineUserRsp();
		getUserRsp.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
		getUserRsp.setMsgid(IMessageDef.genMsgId());
		getUserRsp.setMsgType(IMessageDef.MSGTYPE_GET_ONLINELIST_RSP);
		getUserRsp.setSoftName(request.getSoftName());
		getUserRsp.setMsgResult(IMessageDef.FAIL);
		if(!userTable.isEmpty())
		{
			getUserRsp.setMsgResult(IMessageDef.SUCC);
			Map<String, String> onLineIpToUser = new HashMap<String, String>();
			for (UserInfo userInfo : userTable)
			{
				if(userInfo.getSoftName().equals(request.getSoftName()))
				{
					onLineIpToUser.put(userInfo.getIp(), userInfo.getClientName());
				}
			}
			getUserRsp.setOnlineIPToUser(onLineIpToUser);
		}
		return JSON.toJSONString(getUserRsp);
    }

	@Override
	public int getType()
	{
		return IMessageDef.MSGTYPE_GET_ONLINELIST_REQ;
	}

}
