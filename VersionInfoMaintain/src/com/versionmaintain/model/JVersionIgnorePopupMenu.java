package com.versionmaintain.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public class JVersionIgnorePopupMenu implements ActionListener
{
	private JTable table;
	private JMenuItem addMenuItem;
	private JMenuItem deleteMenuItem;
	private JMenuItem modifyMenuItem;
	
	private JButton addBtn;
	private JButton editBtn;
	private  JButton delBtn;
	
	public JVersionIgnorePopupMenu(JTable table)
    {
	    super();
	    this.table = table;
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
		final JPopupMenu popupMenu = new JPopupMenu("Version Ignore File List Editor");
		addMenuItem = new JMenuItem("Add Item", Resource.ICON_ADD);
		modifyMenuItem = new JMenuItem("Modify Item", Resource.ICON_EDIT);
		deleteMenuItem = new JMenuItem("Delete Item", Resource.ICON_DEL);
		addMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		modifyMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		deleteMenuItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(addMenuItem);
		popupMenu.add(modifyMenuItem);
		popupMenu.add(deleteMenuItem);
		
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				enableMenuItems();
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					popupMenu.show(table, e.getX(), e.getY());
				}
			}
		});
		
		addMenuItem.addActionListener(this);
		deleteMenuItem.addActionListener(this);
		modifyMenuItem.addActionListener(this);
	}
	
	private void enableMenuItems()
    {
		int iSelected = table.getSelectedRow();
		if(iSelected != -1)
		{
			addMenuItem.setEnabled(true);
			deleteMenuItem.setEnabled(true);
			modifyMenuItem.setEnabled(true);
			addBtn.setEnabled(true);
			delBtn.setEnabled(true);
			editBtn.setEnabled(true);
		}
		else
		{
			addMenuItem.setEnabled(true);
			deleteMenuItem.setEnabled(false);
			modifyMenuItem.setEnabled(false);
			addBtn.setEnabled(true);
			delBtn.setEnabled(false);
			editBtn.setEnabled(false);
		}
    }
	
	public void resetComponent()
	{
		this.addBtn.setEnabled(true);
		this.editBtn.setEnabled(false);
		this.delBtn.setEnabled(false);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == addMenuItem || e.getSource() == addBtn)
		{
			addItem();
			table.updateUI();
		}else if(e.getSource() == deleteMenuItem || e.getSource() == delBtn)
		{
			deleteItem();
			table.updateUI();
		}else if(e.getSource() == modifyMenuItem || e.getSource() == editBtn)
		{
			int iSelectedRow = table.getSelectedRow();
			if(-1 != iSelectedRow)
			{
				table.editCellAt(iSelectedRow, 1);
			}
		}
	}

	private void addItem()
	{
		try
        {
	        IgnoreFileTableModel model = (IgnoreFileTableModel) table.getModel();
	        model.addItem();
        } catch (Exception e)
        {
        	//ClassCastException
        }
	}

	private void deleteItem()
	{
		IgnoreFileTableModel model = (IgnoreFileTableModel) table.getModel();
		model.deleteItem(table.getSelectedRow());
	}
}
