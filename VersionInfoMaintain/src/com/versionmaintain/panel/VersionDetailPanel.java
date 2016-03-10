package com.versionmaintain.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeNode;

import com.versionmaintain.JTitleLabel;
import com.versionmaintain.VerDetailTreeCellRenderer;
import com.versionmaintain.model.JVersionDetailPopupMenu;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class VersionDetailPanel
{
	private Component component;
	private JButton versionDetailEditAdd;
	private JButton versionDetailEditRemove;
	private JButton versionDetailEditEdit;
	private JTree softwareVersionInfoTree;
	private JVersionDetailPopupMenu versionDetailTreeMenu;
	
	public VersionDetailPanel()
    {
		JPanel topPanel = new JPanel(new BorderLayout());
		
		JLabel softInfoDetailLabel = new JTitleLabel("VersionDetail");
		softInfoDetailLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JToolBar versionMaintainToolbar = new JToolBar("Detail info");
		versionDetailEditAdd = new JButton(Resource.ICON_ADD);
		versionDetailEditEdit = new JButton(Resource.ICON_EDIT);
		versionDetailEditRemove = new JButton(Resource.ICON_DEL);
		versionMaintainToolbar.add(versionDetailEditAdd);
		versionMaintainToolbar.add(versionDetailEditEdit);
		versionMaintainToolbar.add(versionDetailEditRemove);
		
		topPanel.add(softInfoDetailLabel, BorderLayout.NORTH);
		topPanel.add(versionMaintainToolbar, BorderLayout.SOUTH);
		
		TreeNode root = null;
		softwareVersionInfoTree = new JTree(root);
		softwareVersionInfoTree.setCellRenderer(new VerDetailTreeCellRenderer());
		versionDetailTreeMenu = new JVersionDetailPopupMenu(softwareVersionInfoTree);
		versionDetailTreeMenu.injectPopupMenu(versionDetailEditAdd, versionDetailEditEdit, versionDetailEditRemove);
		softwareVersionInfoTree.setEditable(true);
		softwareVersionInfoTree.setRowHeight(30);
		
		JPanel detailPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints();
		bagConstraints.insets=new Insets(1,5,1,5);  
		detailPanel.setLayout(bagLayout);
		bagConstraints.gridx=0;
		bagConstraints.gridy=0;
		bagConstraints.weightx = 1;
		bagConstraints.fill = GridBagConstraints.BOTH;
		detailPanel.add(topPanel, bagConstraints);
		bagConstraints.gridx=0;
		bagConstraints.gridy=1;
		bagConstraints.weightx = 1;
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.weighty = 1;
		bagConstraints.weighty=1;
		detailPanel.add(new JScrollPane(softwareVersionInfoTree), bagConstraints);
		this.component = detailPanel;
    }
	
	public Component getVersionDetailInfoPanel()
	{
		return component;
	}
	
	public JTree getTree()
	{
		return softwareVersionInfoTree;
	}

	public void resetTreeMenu()
    {
		versionDetailTreeMenu.resetComponent();
    }
}
