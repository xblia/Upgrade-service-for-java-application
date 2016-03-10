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

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class UserTableModel implements TableModel
{
	private List<UserInfo> userInfoList = new ArrayList<UserInfo>();
	
	private UserTableModel()
	{
	}
	
	private static UserTableModel model = new UserTableModel();
	public static UserTableModel getInstance()
	{
		return model;
	}
	
	@Override
	public int getRowCount()
	{
		return userInfoList.size();
	}

	@Override
	public int getColumnCount()
	{
		return 8;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		switch(columnIndex)
		{
		case 0:
			return "Seq";
		case 1:
			return "Client Name";
		case 2:
			return "Client ip";
		case 3:
			return "Soft Name";
		case 4:
			return "Client Version";
		case 5:
			return "Latest Version";
		case 6:
			return "Latest Hearbeat";
		case 7:
			return "Notify Msg";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		UserInfo userInfo = userInfoList.get(rowIndex);
		switch (columnIndex)
        {
		case 0:
			return (rowIndex + 1) + "";
		case 1:
			return userInfo.getClientName();
		case 2:
			return userInfo.getIp();
		case 3:
			return userInfo.getSoftName();
		case 4:
			return userInfo.getClientVersion();
		case 5:
			return userInfo.getLatestVersion();
		case 6:
			return userInfo.getLastHearbeat();
		case 7:
			return userInfo.getBroadcastMsg();
		default:
		}
		return "N/A";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{

	}

	@Override
	public void addTableModelListener(TableModelListener l)
	{

	}

	@Override
	public void removeTableModelListener(TableModelListener l)
	{

	}
	
	public synchronized UserInfo getUser(String name, String ip, String softName)
	{
		for (UserInfo userInfo : userInfoList)
        {
	        if(userInfo.getClientName().equals(name) && userInfo.getIp().equals(ip) && userInfo.getSoftName().equals(softName))
	        {
	        	return userInfo;
	        }
        }
		return null;
	}
	
	public synchronized void addUser(UserInfo userinfo)
	{
		if(!userInfoList.contains(userinfo))
		{
			userInfoList.add(userinfo);
		}
	}

	public synchronized boolean haveControl(UserInfo userInfo)
    {
		if(userInfoList.contains(userInfo))
		{
			return true;
		}
	    return false;
    }

	public synchronized void removeUser(UserInfo userInfo)
    {
		if(userInfoList.contains(userInfo))
		{
			userInfoList.remove(userInfo);
		}
    }
	
	public synchronized List<UserInfo> getAllusers()
	{
		return userInfoList;
	}
}
