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

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author xblia2
 * Jan 21, 2016
 */
public class URLServiceFilter
{

	private String uri;
	private ChannelHandlerContext ctx;
	private FullHttpRequest request;
	private static Map<String, ICommonService> nameToService = new HashMap<String, ICommonService>();
	static
	{
		ICommonService service = null;
		service = new URLVersionHistoryService();
		
		nameToService.put(service.getServiceName(), service);
	}
	
	public URLServiceFilter(String uri, ChannelHandlerContext ctx, FullHttpRequest request)
	{
		this.uri = uri;
		this.ctx = ctx;
		this.request = request;
	}

	public boolean doFilte()
	{
		String []servicePara = null;
		if(uri != null && uri.trim().length() > 1)
		{
			servicePara = uri.substring(1).split("/");
		}
		
		if(null != servicePara && servicePara.length > 0)
		{
			if(nameToService.containsKey(servicePara[0]))
			{
				return nameToService.get(servicePara[0]).execute(servicePara, ctx, request);
			}
		}
		return false;
	}

}
