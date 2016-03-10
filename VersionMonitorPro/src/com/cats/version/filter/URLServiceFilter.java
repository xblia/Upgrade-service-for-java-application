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
