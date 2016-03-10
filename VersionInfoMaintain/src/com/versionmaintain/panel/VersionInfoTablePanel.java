package com.versionmaintain.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.versionmaintain.IVersionMaintainConstant;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class VersionInfoTablePanel implements ActionListener
{
	private VersionMaintainMainFrame frame;
	private Component panel;
    private JButton versionEditAdd;
	private JButton versionEditRemove;
	private JTable softwareVersionsTable;
	
	public VersionInfoTablePanel(VersionMaintainMainFrame frame)
    {
		this.frame = frame;
		this.panel = genVersionMainInfoPanel();
    }
	
	private Component genVersionMainInfoPanel()
    {
		softwareVersionsTable = new JTable();
		softwareVersionsTable.setRowHeight(IVersionMaintainConstant.TABLE_ROT_HEIGHT);
		softwareVersionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		softwareVersionsTable.setRowSelectionAllowed(true);
		softwareVersionsTable.getTableHeader().setPreferredSize(new Dimension(-1, 30));
		softwareVersionsTable.getSelectionModel().addListSelectionListener(frame);
		softwareVersionsTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				int iSelectedRow = softwareVersionsTable.getSelectedRow();
				versionEditRemove.setEnabled(-1 != iSelectedRow);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(genToolBar(), BorderLayout.NORTH);
		panel.add(new JScrollPane(softwareVersionsTable), BorderLayout.CENTER);
	    return panel;
    }
	
	private Component genToolBar()
	{
		JToolBar versionMaintainToolbar = new JToolBar("Version Info");
		versionEditAdd = new JButton(Resource.ICON_ADD);
		versionEditRemove = new JButton(Resource.ICON_DEL);
		versionMaintainToolbar.add(versionEditAdd);
		versionMaintainToolbar.add(versionEditRemove);
		
		this.versionEditAdd.addActionListener(this);
		this.versionEditRemove.addActionListener(this);
		
		return versionMaintainToolbar;
	}
	
	public void initVersionInfoTableColumnWidth()
    {
		softwareVersionsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		softwareVersionsTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		softwareVersionsTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		softwareVersionsTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		softwareVersionsTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		softwareVersionsTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		softwareVersionsTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
    }
	
	public Component getComponent()
	{
		return this.panel;
	}
	
	public JTable getTable()
	{
		return this.softwareVersionsTable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == versionEditAdd)
		{
			frame.onAddVersionAction();
		}
		else if(e.getSource() == versionEditRemove)
		{
			frame.onRemoveVersionAction();
		}
	}
}
