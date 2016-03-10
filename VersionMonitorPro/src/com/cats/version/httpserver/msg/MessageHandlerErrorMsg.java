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

import com.alibaba.fastjson.JSON;
import com.cats.version.VersionServiceError;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageError;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author xblia2
 * Jun 12, 2015
 */
public class MessageHandlerErrorMsg extends IMsgHandler
{

	private VersionServiceError error;
	
	public MessageHandlerErrorMsg(VersionServiceError error)
    {
	    super();
	    this.error = error;
    }

	@Override
	public boolean handleMessage(String strReq, ChannelHandlerContext ctx)
	{
		String errorDesc = error.getErrorDesc();
		IMessageError errorMsg = new IMessageError();
		errorMsg.setMsgid(0);
		errorMsg.setMsgType(IMessageDef.MSGTYPE_REQUEST_ERROR);
		errorMsg.setMsgIdentified(0);
		errorMsg.setOriginRequest(strReq);
		errorMsg.setMsgResult(IMessageDef.FAIL);
		errorMsg.setMsgCause(errorDesc);
		String errorStrJson = JSON.toJSONString(errorMsg);
		sendMessageToClient(errorStrJson, ctx);
		return true;
	}

	@Override
	public int getType()
	{
		return IMessageDef.MSGTYPE_REQUEST_ERROR;
	}

}
