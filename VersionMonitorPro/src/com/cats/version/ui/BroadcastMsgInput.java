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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.version.msg.BroadcastMsg;
import com.cats.version.msg.IMessageDef;

/**
 * @author xblia2
 * Jan 7, 2016
 */
public class BroadcastMsgInput extends JDialog implements ActionListener
{
	private static final long serialVersionUID = -3217618591106892806L;
	private int width = 500;
	private int height = 300;
	private JComboBox<String> msgPriorityBox;
	private JTextArea textArea;
	private JButton btnOk;
	private JButton btnCancel;
	
	private String msgPriority;
	private String msgInfo;
	private boolean needSendMsg = false;
	
	public BroadcastMsgInput()
	{
		this.setSize(width, height);
		this.setTitle("Broadcast Message To Client");
		this.setLocation(200, 100);
		this.setAlwaysOnTop(true);
		this.setModal(true);
		this.initView();
		this.addListener();
		this.setVisible(true);
	}
	
	private void initView()
	{
		this.setLayout(new BorderLayout());
		this.getRootPane().setBorder(new EmptyBorder(15, 15, 15, 15));
		
		JLabel priorityLabel = new JLabel("Priority");
		JLabel msgInputLabel = new JLabel("Message");
		this.msgPriorityBox = new JComboBox<>(getComboValList());
		this.textArea = new JTextArea(10, 10);
		this.btnOk = new JButton("BroadcastMsg");
		this.btnCancel = new JButton("CANCEL");
		JScrollPane textScrollPanel = new JScrollPane(textArea);
		
		JPanel bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.add(btnOk);
		bottomPanel.add(btnCancel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(new LineBorder(new Color(0xcccccc)));
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints();
		bagConstraints.insets = new Insets(5, 5, 5, 5);
		mainPanel.setLayout(bagLayout);

		bagConstraints.gridx = 0;
		bagConstraints.gridy = 0;
		mainPanel.add(priorityLabel, bagConstraints);
		bagConstraints.gridx = 1;
		bagConstraints.gridy = 0;
		bagConstraints.fill=GridBagConstraints.HORIZONTAL;
		mainPanel.add(msgPriorityBox, bagConstraints);
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 1;
		mainPanel.add(msgInputLabel, bagConstraints);
		bagConstraints.gridx = 1;
		bagConstraints.gridy = 1;
		bagConstraints.weighty=1;
		bagConstraints.weightx = 1;
		bagConstraints.fill=GridBagConstraints.BOTH;
		mainPanel.add(textScrollPanel, bagConstraints);
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 2;
		bagConstraints.gridwidth=2;
		bagConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(bottomPanel, bagConstraints);
		
		
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	private String[] getComboValList()
	{
		IMessageDef.BroadcastMsgPriority[] values = IMessageDef.BroadcastMsgPriority.values();
		String[] comBoxVal = new String[values.length];
		int index = 0;
		for (IMessageDef.BroadcastMsgPriority priority : values)
		{
			comBoxVal[index] = priority.toString();
			index++;
		}
		return comBoxVal;
	}

	private void addListener()
	{
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	
	public static void main(String[] args)
	{
		BroadcastMsgInput inputDlg = new BroadcastMsgInput();
		System.out.println(inputDlg.getMsgInfo());
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnOk)
		{
			Object obj = this.msgPriorityBox.getSelectedItem();
			this.msgInfo = this.textArea.getText();
			if(obj == null || msgInfo == null || msgInfo.trim().equals(""))
			{
				AlertDialog.show("Input empty.");
				return;
			}
			this.msgPriority = obj.toString();
			this.needSendMsg = true;
			this.dispose();
		}else if(e.getSource() == btnCancel)
		{
			this.needSendMsg = false;
			this.dispose();
		}
	}

	public String getMsgPriority()
	{
		return msgPriority;
	}

	public String getMsgInfo()
	{
		return msgInfo;
	}

	public boolean isNeedSendMsg()
	{
		return needSendMsg;
	}
	
	public static BroadcastMsg showInputDialog()
	{
		BroadcastMsgInput inputDlg = new BroadcastMsgInput();
		if(inputDlg.needSendMsg)
		{
			BroadcastMsg msg = new BroadcastMsg();
			msg.setMsgPriority(inputDlg.getMsgPriority());
			msg.setMsgInfo(inputDlg.getMsgInfo());
			return msg;
		}
		return null;
	}
}
