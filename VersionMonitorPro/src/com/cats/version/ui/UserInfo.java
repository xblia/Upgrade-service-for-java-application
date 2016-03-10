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
 package com.cats.version.ui;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import com.cats.version.msg.BroadcastMsg;
/**
 * @author xblia2 Jun 25, 2015
 */
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = -8919649252149325075L;
	private static final long CHECK_INTERVAL = 1000*60*10;

	private String clientName = "";
	private String ip = "";
	private String softName = "";
	private String clientVersion = "";
	private String latestVersion = "";
	private String lastHearbeat = "";
	private BroadcastMsg broadcastMsg;
	
	private Long updateTime = System.currentTimeMillis();

	public UserInfo()
    {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if(System.currentTimeMillis() - updateTime > CHECK_INTERVAL)
				{
					if(UserTableModel.getInstance().haveControl(UserInfo.this))
					{
						UserTableModel.getInstance().removeUser(UserInfo.this);
					}
					timer.cancel();
				}
			}
		}, 10000, CHECK_INTERVAL);
    }
	
	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getSoftName()
	{
		return softName;
	}

	public void setSoftName(String softName)
	{
		this.softName = softName;
	}

	public String getClientVersion()
	{
		return clientVersion;
	}

	public void setClientVersion(String clientVersion)
	{
		this.clientVersion = clientVersion;
	}

	public String getLatestVersion()
	{
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion)
	{
		this.latestVersion = latestVersion;
	}

	public String getLastHearbeat()
	{
		return lastHearbeat;
	}

	public void setLastHearbeat(String lastHearbeat)
	{
		this.lastHearbeat = lastHearbeat;
		this.updateTime = System.currentTimeMillis();
	}
	
	public synchronized BroadcastMsg getBroadcastMsg()
	{
		return broadcastMsg;
	}
	
	public synchronized void emptyBroadcastmsg()
	{
		this.broadcastMsg = null;
	}

	public synchronized void setBroadcastMsg(BroadcastMsg broadcastMsg)
	{
		this.broadcastMsg = broadcastMsg;
	}

	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	            + ((clientName == null) ? 0 : clientName.hashCode());
	    result = prime * result + ((ip == null) ? 0 : ip.hashCode());
	    result = prime * result
	            + ((softName == null) ? 0 : softName.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    UserInfo other = (UserInfo) obj;
	    if (clientName == null)
	    {
		    if (other.clientName != null)
			    return false;
	    } else if (!clientName.equals(other.clientName))
		    return false;
	    if (ip == null)
	    {
		    if (other.ip != null)
			    return false;
	    } else if (!ip.equals(other.ip))
		    return false;
	    if (softName == null)
	    {
		    if (other.softName != null)
			    return false;
	    } else if (!softName.equals(other.softName))
		    return false;
	    return true;
    }
}
