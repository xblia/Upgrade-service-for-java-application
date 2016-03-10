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
 package com.cats.version.deamon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.alibaba.fastjson.JSON;
import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;
import com.cats.ui.custome.JButtonEx;
import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.ui.graceframe.IGraceFullyFrameBase;
import com.cats.version.msg.IMessageVersionUpdateRsp;
import com.cats.version.utils.Utils;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class VersionUpgradeDeamonFrame extends IGraceFullyFrameBase implements ActionListener, IUpdateStatusListener
{
    private static final long serialVersionUID = 8782007736657116765L;
	private int width;
	private int height;
	private static final String TITLE = "Software upgrade(v1.2)...";
	
	private JLabel infoLabel;
	private JProgressBar progressBar;
	private JButton cancelBtn;
	
	private Color userColor;
	private String startupName;
	private boolean ignoreError = false;
	private final int MAX_WAITTING_PARNENTRSP_TIMEOUT = 6;//Minutes
	private int waittingTimeIndex = 0;
	
	
	public VersionUpgradeDeamonFrame(final String upgradeProfile, int iParentPort)
    {
		super(TITLE, null, BarIconType.TITLE.getType() | BarIconType.CLOSE.getType());
		width = 500;
		height = 140;
		userColor = new Color(0x6CAAD9);
		userColor = new Color(userColor.getRed(), userColor.getGreen(), userColor.getBlue());
		this.setTitle(title);
		this.setIconImage(new ImageIcon(VersionUpgradeDeamonFrame.class.getResource("sync.png")).getImage());
		this.setSize(width, height);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.initView();
		this.setVisible(true);
		final NotifyParentProcess notifyParentProcess = new NotifyParentProcess(iParentPort).notifyProc();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				waittingTimeIndex++;
				if(waittingTimeIndex >= MAX_WAITTING_PARNENTRSP_TIMEOUT || notifyParentProcess.parentProcessDied())
				{
					timer.cancel();
					VersionUpgradeDeamonFrame.this.beginUpgrade(upgradeProfile);
				}
			}
		}, 1000, 1000);
    }
	

	private void beginUpgrade(String upgradeProfile)
    {
		String jsonProfile = getUpgradProfile(upgradeProfile);
		if(!jsonProfile.isEmpty())
		{
			IMessageVersionUpdateRsp updateRsp = JSON.parseObject(jsonProfile, IMessageVersionUpdateRsp.class);
			if(null != updateRsp)
			{
				this.infoLabel.setText(updateRsp.getSoftName() + " Begin upgrade...");
				this.startupName = updateRsp.getStartupName();
				beginDownloadFiles(updateRsp);
			}else
			{
				 alertError("Get upgrad file exception");	
			}
		}
    }

	private void beginDownloadFiles(IMessageVersionUpdateRsp updateRsp)
    {
		new HttpDownloadFiles(updateRsp, this).start();
    }

	private void initView()
    {
		infoLabel = new JLabel("Collecting information....");
		infoLabel.setPreferredSize(new Dimension(-1, 30));
		
		progressBar = new JProgressBar(0);
		progressBar.setPreferredSize(new Dimension(width, 20));
		progressBar.setBorder(null);
		progressBar.setBorderPainted(false);
		progressBar.setStringPainted(true);
		progressBar.setForeground(userColor);
		//progressBar.setValue(50);
		cancelBtn = new JButtonEx("Cancel");
		cancelBtn.setPreferredSize(new Dimension(80, 30));
		
		this.cancelBtn.addActionListener(this);
		this.cancelBtn.setEnabled(false);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(infoLabel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(progressBar, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setOpaque(false);
		bottomPanel.add(cancelBtn);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel, BorderLayout.CENTER);
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
	    if(e.getSource() == cancelBtn)
	    {
	    	System.exit(0);
	    }
	}
	
	public String getUpgradProfile(String upgradeProfile)
	{
		File file = new File(Utils.getWorkSpace() + File.separator + upgradeProfile);
		FileInputStream fis = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try
        {
			fis = new FileInputStream(file);
			int iReadLen = -1;
	        do
	        {
	        	byte[] data = new byte[1024];
	        	iReadLen  = fis.read(data);
	        	if(-1 != iReadLen)
	        	{
	        		buffer.write(data, 0, iReadLen);
	        	}
	        }while(-1 != iReadLen);
	        file.deleteOnExit();
        } catch (Exception e)
        {
	        AlertDialog.show(Utils.getException(e));
        }finally
        {
        	Utils.closeRes(fis);
        }
		try
        {
	        return buffer.toString("UTF-8");
        } catch (UnsupportedEncodingException e)
        {
	        e.printStackTrace();
        }
		return "";
	}
	
	public void alertError(String info)
	{
	    if(ignoreError)
	    {
	       return; 
	    }
		String message = "Sorry, Upgrade internal error(" +info+ "), you can contact the developer or tray again. thks";
		String title = "Upragde error.";
		int iRes = AlertDialog.show(title, message, AlertDialogOptions.OPTION_IGNORE
		        | AlertDialogOptions.OPTION_IGNOREALL
		        | AlertDialogOptions.OPTION_TERMINAL, AlertDialog.ALERT_TYPE_WARNING);
		if(iRes == AlertDialogOptions.OPTION_IGNOREALL)
		{
		    this.ignoreError = true;
		}else if(iRes == AlertDialogOptions.OPTION_TERMINAL)
		{
		    System.exit(0);
		}
	}
	
	@Override
    public void notify(final String fileName, final int iTotalFile, final int iCurrent)
    {
		updateInfo("Upgrade " + fileName + "...");
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				progressBar.setMaximum(iTotalFile);
				progressBar.setValue(iCurrent);
			}
		});
    }

	@Override
    public void notifyException(final String info)
    {
		alertError(info);
    }

	@Override
    public void notifyFinished()
    {
		updateInfo("Upgrade finished");
		cancelBtn.setEnabled(true);
		restartSoftware();
    }
	
	private void updateInfo(final String info)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				infoLabel.setText(info);
			}
		});
	}

	int iWaitSecond = 5;
	private void restartSoftware()
    {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if(iWaitSecond < 0)
				{
					timer.cancel();
					startupSoftWare();
				}else
				{
					updateInfo("<html>Upgrade finished, Automatic restart after <font color='#44aaff' size='5'>" + iWaitSecond + "</font> seconds: </html>");
					iWaitSecond--;
				}
			}
		}, 10, 1000);
    }
	
	private void startupSoftWare()
    {
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.add("-jar");
		cmd.add(startupName);
		ProcessBuilder builder = new ProcessBuilder(cmd);
		try
        {
	        builder.start();
        } catch (IOException e)
        {
	        e.printStackTrace();
	        alertError(e.toString());
        }
		System.exit(0);
    }
	
	
	@Override
    public String getIconRelativePath()
    {
	    return "/com/cats/version/deamon/sync_small.png";
    }


	public static void main(String[] args)
    {
		if(args == null || args.length < 1)
		{
			AlertDialog.show("param error " + args);
		}
		
		int iParentPort = -1;
		if(args.length >= 3)
		{
			try
            {
	            iParentPort = Integer.parseInt(args[2]);
            } catch (NumberFormatException e)
            {
	            e.printStackTrace();
            }
		}
	    new VersionUpgradeDeamonFrame(args[0], iParentPort);
		
	    /*new VersionUpgradeDeamonFrame("abcd", 222);*/
    }
}