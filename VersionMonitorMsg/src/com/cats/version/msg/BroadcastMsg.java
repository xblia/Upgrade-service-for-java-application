package com.cats.version.msg;

/**
 * @author xblia2
 * Jan 7, 2016
 */
public class BroadcastMsg
{
	private String msgPriority="";
	private String msgInfo="";
	public String getMsgPriority()
	{
		return msgPriority;
	}
	public void setMsgPriority(String msgPriority)
	{
		this.msgPriority = msgPriority;
	}
	public String getMsgInfo()
	{
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo)
	{
		this.msgInfo = msgInfo;
	}
	public BroadcastMsg(String msgPriority, String msgInfo)
	{
		super();
		this.msgPriority = msgPriority;
		this.msgInfo = msgInfo;
	}
	public BroadcastMsg()
	{
	}
	@Override
	public String toString()
	{
		return this.msgInfo;
	}
}
