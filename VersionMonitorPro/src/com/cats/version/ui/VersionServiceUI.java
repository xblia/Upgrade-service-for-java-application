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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.version.httpserver.HttpStaticVersionMonitorFileServer;
import com.cats.version.msg.BroadcastMsg;

/**
 * @author xblia2 Jun 25, 2015
 */
public class VersionServiceUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -2383263318465862942L;

	private int width = 950;
	private int height = 300;
	private String title = "VersionService UI_" + HttpStaticVersionMonitorFileServer.VERSION;

	private JButton sendMsgBtn;
	private JButton aboutBtn;
	private JTable table;

	private HttpStaticVersionMonitorFileServer versionService;
	private DefaultTableCellRenderer render = null;
	
	public VersionServiceUI()
	{
		this.setSize(width, height);
		this.setLocation(200, 100);
		this.setTitle(title);
		this.setIconImage(new ImageIcon(VersionServiceUI.class.getResource("icon.png")).getImage());
		this.initView();
		this.updateTable();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		versionService = new HttpStaticVersionMonitorFileServer();
		versionService.startServer();
	}

	private void initView()
	{
		this.setLayout(new BorderLayout());
		this.getRootPane().setBorder(new LineBorder(Color.gray, 1));
		
		sendMsgBtn = makeToolButton("icon_sendmsg.png", IActionCommand.ACTION_SEND_MSG);
		aboutBtn = makeToolButton("icon_about.png", IActionCommand.ACTION_ABOUT);
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.add(sendMsgBtn);
		toolBar.add(aboutBtn);
		
		table = new JTable();
		table.setModel(UserTableModel.getInstance());
		table.setRowHeight(30);
		table.setSelectionBackground(new Color(0x3399CC));
		
		this.add(toolBar, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		initTableCellRender();
		setColumnWidth(50, 120, 120, 120, 120, 120, 150, 150);
	}

	private void setColumnWidth(int...columnWidths)
	{
		for (int i = 0; i < columnWidths.length; i++)
        {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setMinWidth(columnWidths[i]);
			column.setCellRenderer(render);
        }
	}

	public void updateTable()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						table.updateUI();
					}
				});
			}
		}, 1000, 1000);
	}
	
	private void initTableCellRender()
	{
		render = new DefaultTableCellRenderer()
		{
			private static final long serialVersionUID = 7551137567801824157L;

			@Override
			public Component getTableCellRendererComponent(JTable table,
			        Object value, boolean isSelected, boolean hasFocus,
			        int row, int column)
			{
				Component component = super.getTableCellRendererComponent(
				        table, value, isSelected, hasFocus, row, column);
				if (column == 4)
				{
					String latestVersion = table.getModel().getValueAt(row, 5)
					        .toString();
					if (!latestVersion.equals(value.toString()))
					{
						component.setForeground(Color.red);
					}
					else
					{
						component.setForeground(Color.black);
					}
				}else
				{
					component.setForeground(Color.black);
				}
				return component;
			}
		};
		render.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private JButton makeToolButton(String filename, String command) {// 创建按钮  
        ImageIcon imageicon = null;  
        if (filename != null) {  
            imageicon = new ImageIcon(VersionServiceUI.class.getResource(filename));
            Image image = imageicon.getImage();  
            image = image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);// 创建图片的缩放版本  
            imageicon.setImage(image);  
        }  
        JButton button = new JButton(imageicon);  
        button.setActionCommand(command);  
        button.addActionListener(this);  
        return (button);  
    }  
	

	public static void main(String[] args)
	{
		new VersionServiceUI();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof JButton)
		{
			JButton btn = (JButton)e.getSource();
			switch (btn.getActionCommand())
			{
				case IActionCommand.ACTION_SEND_MSG:
					actionSendMessage();
					break;
				
				case IActionCommand.ACTION_ABOUT:
					actionAbout();
				default:
					break;
			}
		}
	}

	private void actionAbout()
	{
		AlertDialog.show("Version Service developer: xiaobox.li@intel.com");
	}

	private void actionSendMessage()
	{
		int[] selectedRows = table.getSelectedRows();
		if(selectedRows.length == 0)
		{
			return;
		}
		
		BroadcastMsg msg = BroadcastMsgInput.showInputDialog();
		if(msg == null)
		{
			return;
		}
		
		List<UserInfo> allUserList = UserTableModel.getInstance().getAllusers();
		for (int rowIndex : selectedRows)
		{
			allUserList.get(rowIndex).setBroadcastMsg(msg);
		}
		
	}
}
