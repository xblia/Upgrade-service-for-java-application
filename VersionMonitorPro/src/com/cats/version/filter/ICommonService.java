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