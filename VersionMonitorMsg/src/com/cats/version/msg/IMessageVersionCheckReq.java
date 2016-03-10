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
 package com.cats.version.msg;


/**
 * @author xblia2 Jun 10, 2015
 */
public class IMessageVersionCheckReq extends IMessage
{
	private static final long serialVersionUID = -3498794055424994910L;
	private String softName;
	private int currVersionCode;
	private String currVersionName;
	private String clientName;
	private String clientIp;
	
	public String getSoftName()
	{
		return softName;
	}

	public void setSoftName(String softName)
	{
		this.softName = softName;
	}

	public int getCurrVersionCode()
	{
		return currVersionCode;
	}

	public void setCurrVersionCode(int currVersionCode)
	{
		this.currVersionCode = currVersionCode;
	}

	public String getCurrVersionName()
	{
		return currVersionName;
	}

	public void setCurrVersionName(String currVersionName)
	{
		this.currVersionName = currVersionName;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getClientIp()
	{
		return clientIp;
	}

	public void setClientIp(String clientIp)
	{
		this.clientIp = clientIp;
	}
	
	public void fillHostInfo()
	{
		this.clientIp = IMessage.getLocalIp();
		this.clientName = IMessage.getUserName();
	}

	@Override
    public String toString()
    {
	    return "IMessageVersionCheckReq [softName=" + softName
	            + ", currVersionCode=" + currVersionCode + ", currVersionName="
	            + currVersionName + ", clientName=" + clientName
	            + ", clientIp=" + clientIp + "]";
    }
}
