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

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import com.cats.version.VersionServiceError;
import com.cats.version.msg.IMessageDef;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class IHandlerDispatcher
{
	public static IHandlerDispatcher dispatcher = new IHandlerDispatcher();
	
	public static IHandlerDispatcher getInstance()
	{
		return dispatcher;
	}
	
	private Map<Integer, Class<?>> idToHandler = new HashMap<Integer, Class<?>>();
	
	public IHandlerDispatcher()
    {
		idToHandler.put(IMessageDef.MSGTYPE_REQUEST_ERROR, 		MessageHandlerErrorMsg.class);
		idToHandler.put(IMessageDef.MSGTYPE_VERSION_CHCECK_REQ, MessageHandlerVersionCheck.class);
		idToHandler.put(IMessageDef.MSGTYPE_VERSION_UPDATE_REQ, MessageHandlerVersionUpdate.class);
		idToHandler.put(IMessageDef.MSGTYPE_GET_ONLINELIST_REQ, MessageHandlerGetOnlineUser.class);
    }
	
	public boolean dispatcheMsg(int iMsgType, String strReq, VersionServiceError error, ChannelHandlerContext ctx)
	{
		VersionServiceError serviceError = error;
		if(serviceError == VersionServiceError.SUCCESS)
		{
			Class<?> handlerClass = idToHandler.get(iMsgType);
			if(handlerClass != null)
			{
				try
		        {
		            IMsgHandler msgHandler = (IMsgHandler)handlerClass.newInstance();
		            msgHandler.handleMessage(strReq, ctx);
		        } catch (InstantiationException | IllegalAccessException e)
		        {
		            e.printStackTrace();
		        }
				return true;
			}
			serviceError = VersionServiceError.HANDLEMSG_UNSUPPORTMSG;
		}
		
		if(serviceError != VersionServiceError.SUCCESS)
		{
			new MessageHandlerErrorMsg(serviceError).handleMessage(strReq, ctx);
		}
	
		return false;
	}
}