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

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.cats.version.VersionInfoDetail;

/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class VersionInfoDetailTreeModel implements TreeModel
{
    private List<VersionInfoDetail> softwareVerDetailInfos = new ArrayList<VersionInfoDetail>();
    private JTree tree;
    
	public VersionInfoDetailTreeModel(JTree tree, List<VersionInfoDetail> softwareVerDetailInfos)
    {
	    super();
	    this.tree = tree;
	    if(null != softwareVerDetailInfos)
	    {
	    	this.softwareVerDetailInfos = softwareVerDetailInfos;
	    }
    }

	@Override
    public Object getRoot()
    {
	    return softwareVerDetailInfos;
    }

	@Override
    public Object getChild(Object parent, int index)
    {
		if(parent instanceof List)
		{
			return ((List<?>)parent).get(index);
		}else if(parent instanceof VersionInfoDetail)
		{
			return ((VersionInfoDetail)parent).getDetail().get(index);
		}
	    return null;
    }

	@Override
    public int getChildCount(Object parent)
    {
		if(parent instanceof List)
		{
			return ((List<?>)parent).size();
		}else if(parent instanceof VersionInfoDetail)
		{
			return ((VersionInfoDetail)parent).getDetail().size();
		}
	    return 0;
    }

	@Override
    public boolean isLeaf(Object node)
    {
	    return node instanceof String;
    }

	@Override
    public void valueForPathChanged(TreePath path, Object newValue)
    {
		if(null == newValue || newValue.toString().isEmpty() || null == path.getParentPath())
		{
			return;
		}
		Object obj = path.getLastPathComponent();
		if(obj instanceof VersionInfoDetail)
		{
			((VersionInfoDetail)obj).setTitle(newValue.toString());
		}
		else if(path.getParentPath().getLastPathComponent() instanceof VersionInfoDetail)
		{
			VersionInfoDetail softDetailInfo = ((VersionInfoDetail)path.getParentPath().getLastPathComponent());
			String oldValue = path.getLastPathComponent().toString();
			List<String> details = softDetailInfo.getDetail();
			int iIndex = details.indexOf(oldValue);
			if(iIndex != -1)
			{
				details.remove(iIndex);
				details.add(iIndex, newValue.toString());
				tree.updateUI();
			}
		}
    }

	@Override
    public int getIndexOfChild(Object parent, Object child)
    {
	    return 0;
    }

	@Override
    public void addTreeModelListener(TreeModelListener l)
    {
	    
    }

	@Override
    public void removeTreeModelListener(TreeModelListener l)
    {
    }
}
