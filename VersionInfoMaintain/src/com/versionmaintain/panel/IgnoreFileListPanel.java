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
 package com.versionmaintain.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.versionmaintain.IVersionMaintainConstant;
import com.versionmaintain.JTitleLabel;
import com.versionmaintain.model.IgnoreFileTableModel;
import com.versionmaintain.model.JVersionIgnorePopupMenu;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class IgnoreFileListPanel
{
	private JButton versionIgnoreFileEditAdd;
	private JButton versionIgnoreFileEditRemove;
	private JButton versionIgnoreFileEditEdit;
	
	private Component mainPanel;
	private JTable ignoreFileListTable;
	private JVersionIgnorePopupMenu versionIgnoreTabMenu;
	
	
	public IgnoreFileListPanel()
    {
		mainPanel = genVersinIgnoreFileListPanel();
    }
	
	private Component genVersinIgnoreFileListPanel()
	{
		ignoreFileListTable = new JTable();
		JLabel nameLabel = new JTitleLabel("IGnore File List");
		
		JPanel ignoreTablePanel = new JPanel();
		JPanel ignoreTableTopPanel = new JPanel(new BorderLayout());
		ignoreTableTopPanel.add(nameLabel, BorderLayout.NORTH);
		ignoreTableTopPanel.add(genIgnoreToolbar(), BorderLayout.SOUTH);
		
		ignoreTablePanel.setLayout(new BorderLayout());
		ignoreTablePanel.add(ignoreTableTopPanel, BorderLayout.NORTH);
		ignoreTablePanel.add(new JScrollPane(ignoreFileListTable), BorderLayout.CENTER);
		
		//Menu
		versionIgnoreTabMenu = new JVersionIgnorePopupMenu(ignoreFileListTable);
		versionIgnoreTabMenu.injectPopupMenu(versionIgnoreFileEditAdd, versionIgnoreFileEditEdit, versionIgnoreFileEditRemove);
		
		return ignoreTablePanel;
	}
	
	private Component genIgnoreToolbar()
    {
		JToolBar versionIgnorefileToolbar = new JToolBar("Ignore file");
		versionIgnoreFileEditAdd = new JButton(Resource.ICON_ADD);
		versionIgnoreFileEditEdit = new JButton(Resource.ICON_EDIT);
		versionIgnoreFileEditRemove = new JButton(Resource.ICON_DEL);
		versionIgnorefileToolbar.add(versionIgnoreFileEditAdd);
		versionIgnorefileToolbar.add(versionIgnoreFileEditEdit);
		versionIgnorefileToolbar.add(versionIgnoreFileEditRemove);
		
		versionIgnoreFileEditEdit.setEnabled(false);
		versionIgnoreFileEditRemove.setEnabled(false);
	    return versionIgnorefileToolbar;
    }
	
	public void initIgnoreFileTable(List<String> softwareIgnoreFiles)
    {
		ignoreFileListTable.setModel(new IgnoreFileTableModel(softwareIgnoreFiles));
		ignoreFileListTable.setRowHeight(IVersionMaintainConstant.TABLE_ROT_HEIGHT);
		ignoreFileListTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		ignoreFileListTable.getColumnModel().getColumn(1).setPreferredWidth(500);
		ignoreFileListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ignoreFileListTable.getTableHeader().setPreferredSize(new Dimension(-1, 30));
		ignoreFileListTable.getColumnModel().getColumn(0).setWidth(20);
		ignoreFileListTable.getColumnModel().getColumn(1).setWidth(100);
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ignoreFileListTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		
    }
	
	public Component getComponent()
	{
		return this.mainPanel;
	}

	public void emptyTable()
    {
		this.ignoreFileListTable.setModel(new IgnoreFileTableModel(null));
		this.ignoreFileListTable.updateUI();
    }
}
