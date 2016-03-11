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

import com.cats.version.httpserver.msg.IMsgHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author xblia2
 * Jan 21, 2016
 */
public abstract class ICommonService extends IMsgHandler
{
	public abstract String getServiceName();
	public abstract boolean execute(String []servicePara, ChannelHandlerContext ctx, FullHttpRequest request);
	
	@Override
	public boolean handleMessage(String strReq, ChannelHandlerContext ctx)
	{
		return false;
	}

	@Override
	public int getType()
	{
		return 0;
	}
}