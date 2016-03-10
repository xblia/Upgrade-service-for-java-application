package com.versionmaintain.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.cats.version.VersionInfo;
import com.cats.version.VersionInfoDetail;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class VersionInfoTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = -8618640561626063405L;
    
    private List<VersionInfo> softwareVerInfos = new ArrayList<VersionInfo>();
    
	public VersionInfoTableModel(List<VersionInfo> softwareVerInfos)
    {
	    super();
	    if(null != softwareVerInfos)
	    {
	    	this.softwareVerInfos = softwareVerInfos;
	    }
    }

	@Override
	public int getRowCount()
	{
		if(null != softwareVerInfos)
		{
			return softwareVerInfos.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount()
	{
		return 4;
	}
	
	

	@Override
    public String getColumnName(int column)
    {
		switch (column)
        {
		case 0:
			return "Seq";
		case 1:
			return "SoftName";
		case 2:
			return "VersionName";
		case 3:
			return "VersionCode";
		default:
			return "N/A";
		}
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		switch(columnIndex)
		{
		case 0:
			return rowIndex+1;
		case 1:
			return softwareVerInfos.get(rowIndex).getAppName();
		case 2:
			return softwareVerInfos.get(rowIndex).getVersion();
		case 3:
			return softwareVerInfos.get(rowIndex).getVersionCode();
		default:
			return "N/A";
		}
	}
	
	public List<VersionInfoDetail> getSoftwareDetailInfos(int index)
	{
		return softwareVerInfos.get(index).getDetails();
	}
	
	public List<String> getSoftwareIgnoreFiles(int index)
	{
		return softwareVerInfos.get(index).getIgnoreFiles();
	}
	
	public VersionInfo getSoftwarInfo(int index)
	{
		return softwareVerInfos.get(index);
	}
	
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
		if(columnIndex == 2 || columnIndex == 3)
		{
			return true;
		}
		else
		{
			return false;
		}
    }
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		VersionInfo softwareVerInfo = softwareVerInfos.get(rowIndex);
		if(null == softwareVerInfo || null == aValue)
		{
			return;
		}
		switch (columnIndex)
        {
		case 1:
			softwareVerInfo.setAppName(aValue.toString());
			break;
		case 2:
			softwareVerInfo.setVersion(aValue.toString());
			break;
		case 3:
			softwareVerInfo.setVersionCode(Integer.parseInt(aValue.toString()));
			break;
		default:
			break;
		}
	}
	
	public void addItem(String softwarename)
	{
		VersionInfo softwareVerInfo = new VersionInfo();
		softwareVerInfo.setAppName(softwarename);
		this.softwareVerInfos.add(softwareVerInfo);
	}
	
	public void removeItem(int iIndex)
	{
		if(iIndex < softwareVerInfos.size())
		{
			softwareVerInfos.remove(iIndex);
		}
	}
}
