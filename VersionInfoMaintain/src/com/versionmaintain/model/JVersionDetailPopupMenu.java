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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;

import com.cats.version.VersionInfoDetail;
import com.versionmaintain.res.Resource;
import com.versionmaintain.utils.JTreeUtils;
import com.versionmaintain.utils.VersionServiceUtils;

/**
 * @author xiaobolx
 * 2016年1月20日
 */
public class JVersionDetailPopupMenu implements ActionListener
{
	private JTree tree;
	private JMenuItem addMenuItem;
	private JMenuItem deleteMenuItem;
	private JMenuItem modifyMenuItem;
	
	private JButton addBtn;
	private JButton editBtn;
	private  JButton delBtn;
	
	public JVersionDetailPopupMenu(JTree tree)
    {
	    super();
	    this.tree = tree;
    }

	public void injectPopupMenu(JButton addBtn, JButton editBtn, JButton delBtn)
	{
		this.addBtn = addBtn;
		this.editBtn = editBtn;
		this.delBtn = delBtn;
		this.addBtn.addActionListener(this);
		this.editBtn.addActionListener(this);
		this.delBtn.addActionListener(this);
		this.resetComponent();
		final JPopupMenu popupMenu = new JPopupMenu("Version Detail Info Editor");
		addMenuItem = new JMenuItem("Add Item", Resource.ICON_ADD);
		modifyMenuItem = new JMenuItem("Modify Item", Resource.ICON_EDIT);
		deleteMenuItem = new JMenuItem("Delete Item", Resource.ICON_DEL);
		addMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		modifyMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		deleteMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(addMenuItem);
		popupMenu.add(modifyMenuItem);
		popupMenu.add(deleteMenuItem);
		
		tree.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				TreePath treePath = tree.getSelectionPath();
				if (null == treePath)
				{
					return;
				}
				
				enableMenuItems();
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					popupMenu.show(tree, e.getX(), e.getY());
				}
			}
		});
		
		addMenuItem.addActionListener(this);
		deleteMenuItem.addActionListener(this);
		modifyMenuItem.addActionListener(this);
	}
	
	private void enableMenuItems()
    {
		TreePath treePath = tree.getSelectionPath();
		if(treePath.getParentPath()== null)
		{
			addMenuItem.setEnabled(true);
			deleteMenuItem.setEnabled(false);
			modifyMenuItem.setEnabled(false);
			addBtn.setEnabled(true);
			delBtn.setEnabled(false);
			editBtn.setEnabled(false);
		}
		else if(treePath.getLastPathComponent() instanceof VersionInfoDetail)
		{
			addMenuItem.setEnabled(true);
			deleteMenuItem.setEnabled(true);
			modifyMenuItem.setEnabled(true);
			addBtn.setEnabled(true);
			delBtn.setEnabled(true);
			editBtn.setEnabled(true);
		}
		else if(treePath.getParentPath().getLastPathComponent() instanceof VersionInfoDetail)
		{
			addMenuItem.setEnabled(false);
			deleteMenuItem.setEnabled(true);
			modifyMenuItem.setEnabled(true);
			addBtn.setEnabled(false);
			delBtn.setEnabled(true);
			editBtn.setEnabled(true);
		}
    }
	
	public void resetComponent()
	{
		this.addBtn.setEnabled(false);
		this.editBtn.setEnabled(false);
		this.delBtn.setEnabled(false);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == addMenuItem || e.getSource() == addBtn)
		{
			addItem();
			tree.updateUI();
			JTreeUtils.expandAllNodes(tree, 0, tree.getRowCount());
		}else if(e.getSource() == deleteMenuItem || e.getSource() == delBtn)
		{
			deleteItem();
			tree.updateUI();
			JTreeUtils.expandAllNodes(tree, 0, tree.getRowCount());
		}else if(e.getSource() == modifyMenuItem || e.getSource() == editBtn)
		{
			tree.startEditingAtPath(tree.getSelectionPath()); 
		}
	}

	private void addItem()
    {
	    TreePath treePath = tree.getSelectionPath();
	    if(null == treePath)
	    {
	    	return;
	    }
	    Object selectObj = treePath.getLastPathComponent();
	    if(treePath.getParentPath()== null)//root
		{
	    	@SuppressWarnings({ "unchecked", "rawtypes" })
            List<VersionInfoDetail> detailList = ((List)selectObj);
	    	if(null != detailList)
	    	{
	    		VersionInfoDetail detailInfo = new VersionInfoDetail();
	    		detailInfo.setTitle("New Item");
	    		detailList.add(detailInfo);
	    	}
		}
		else if(treePath.getLastPathComponent() instanceof VersionInfoDetail)//Feature
		{
			((VersionInfoDetail)selectObj).getDetail().add(VersionServiceUtils.getDiffInfo(((VersionInfoDetail)selectObj).getDetail()));
		}
    }
	
	private void deleteItem()
	{
		TreePath treePath = tree.getSelectionPath();
		if (null == treePath)
		{
			return;
		}
		if (treePath.getLastPathComponent() instanceof VersionInfoDetail)// Feature
		{
			((List<?>)treePath.getParentPath().getLastPathComponent()).remove(treePath.getLastPathComponent());
		} else if (treePath.getParentPath().getLastPathComponent() instanceof VersionInfoDetail)// Info.
		{
			((VersionInfoDetail)treePath.getParentPath().getLastPathComponent()).getDetail().remove(treePath.getLastPathComponent());
		}
	}
}
