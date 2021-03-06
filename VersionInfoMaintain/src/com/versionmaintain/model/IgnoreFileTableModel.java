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
 package com.versionmaintain.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.versionmaintain.utils.VersionServiceUtils;

/**
 * @author xiaobolx 2016年1月25日
 */
public class IgnoreFileTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 6878684868766020372L;
	private List<String> ignoreFileList = new ArrayList<String>();

	public IgnoreFileTableModel(List<String> ignoreFileList)
	{
		if (null != ignoreFileList)
		{
			this.ignoreFileList = ignoreFileList;
		}
	}

	@Override
	public int getRowCount()
	{
		return ignoreFileList.size();
	}

	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:
			return rowIndex + 1;
		case 1:
			return ignoreFileList.get(rowIndex);
		}
		return null;
	}

	@Override
	public String getColumnName(int column)
	{
		switch (column)
		{
		case 0:
			return "Seq";
		case 1:
			return "IgnoreFile";
		default:
			return "N/A";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return columnIndex == 1;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if(null == aValue || aValue.toString().isEmpty())
		{
			return;
		}
		ignoreFileList.remove(rowIndex);
		ignoreFileList.add(rowIndex, aValue.toString());
	}

	public void addItem()
    {
		this.ignoreFileList.add(VersionServiceUtils.getDiffInfo(ignoreFileList));
    }
	

	public void deleteItem(int selectedRow)
    {
		if(-1 == selectedRow || selectedRow >= ignoreFileList.size())
		{
			return;
		}
		ignoreFileList.remove(selectedRow);
    }
}