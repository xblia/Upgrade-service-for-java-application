package com.versionmaintain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class SoftwareNameComboBoxModel implements ComboBoxModel<String>
{
	private List<String> selectStrings = new ArrayList<String>();
	private Object selectObj;
	
	public SoftwareNameComboBoxModel(Set<String> selections)
    {
	    super();
	    if(null != selections)
	    {
	    	this.selectStrings = new ArrayList<String>(selections);
	    }
    }

	@Override
    public int getSize()
    {
		if(null != selectStrings)
		{
			return selectStrings.size();
		}
		return 0;
    }

	@Override
    public String getElementAt(int index)
    {
	    return selectStrings.get(index);
    }

	@Override
    public void addListDataListener(ListDataListener l)
    {
	    
    }

	@Override
    public void removeListDataListener(ListDataListener l)
    {
	    
    }

	@Override
    public void setSelectedItem(Object anItem)
    {
	    this.selectObj = anItem;
    }

	@Override
    public Object getSelectedItem()
    {
	    return selectObj;
    }
	
	public boolean addSoftInfoItem(String softwareName)
	{
		if(null != selectStrings && !selectStrings.contains(softwareName))
		{
			selectStrings.add(softwareName);
			return true;
		}
		return false;
	}

	public void removeSoftwareItem(Object selectItem)
	{
		if (selectStrings.contains(selectItem))
		{
			selectStrings.remove(selectItem);
		}
	}
}
