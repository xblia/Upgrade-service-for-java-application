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
 package com.cats.version.httpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cats.version.VersionServiceError;
import com.cats.version.httpserver.msg.IHandlerDispatcher;
import com.cats.version.msg.IMessageDef;

/**
 * @author xblia2 Jun 9, 2015
 */
public class VersionProtocolMessageHandler
{
	
	private VersionServiceError error;
	
	public VersionProtocolMessageHandler()
	{
	}
	
	public void handleMessage(ChannelHandlerContext ctx, FullHttpRequest request)
	{
		error = VersionServiceError.SUCCESS;
		try
        {
			String requestMsg = "";
			int msgType = -1;
	        do
	        {
	        	requestMsg = decodeMessage(request);
		        if(null == requestMsg || requestMsg.isEmpty())
		        {
		        	error = VersionServiceError.HANDLEMSG_POST;
		        	break;
		        }
		        JSONObject jsonReq = JSON.parseObject(requestMsg);
	        	if(null == jsonReq)
	        	{
	        		error = VersionServiceError.HANDLEMSG_JSONPARSE;
	        		break;
	        	}
	        	
	        	int iDentified = jsonReq.getInteger("msgIdentified");
        		if(iDentified != IMessageDef.MSGIDENTIFIED_VERSIONSERVICE)
        		{
        			error = VersionServiceError.HANDLEMSG_PERMISSION_DENIED;
        			break;
        		}
        		msgType = jsonReq.getIntValue("msgType");
	        }while(false);
	        
			if(!IHandlerDispatcher.getInstance().dispatcheMsg(msgType, requestMsg, error, ctx))
			{
				error = VersionServiceError.HANDLEMSG_UNSUPPORTMSG;
				System.out.println("Dispatche msg fail.");
			}
	        
        } catch (IOException e)
        {
	        e.printStackTrace();
        }
	}

	private String decodeMessage(FullHttpRequest request) throws IOException
	{
		HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(request);
		InterfaceHttpData data = httpPostRequestDecoder.getBodyHttpData("msg");
		if (data.getHttpDataType() == HttpDataType.Attribute)
		{
			Attribute attribute = (Attribute) data;
			String strMsg = attribute.getValue();
			return strMsg;
		}
		return null;
	}
}
