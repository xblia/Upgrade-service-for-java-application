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

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.versionmaintain.JMenuItemEx;
import com.versionmaintain.menuactions.AboutUsMenuAction;
import com.versionmaintain.menuactions.CloseFileMenuAction;
import com.versionmaintain.menuactions.CreateSoftwareInfoMenuAction;
import com.versionmaintain.menuactions.DeleteSoftwareInfoMenuAction;
import com.versionmaintain.menuactions.ExitMenuAction;
import com.versionmaintain.menuactions.CMDMLauncherenuAction;
import com.versionmaintain.menuactions.NewFileMenuAction;
import com.versionmaintain.menuactions.OpenFileMenuAction;
import com.versionmaintain.menuactions.SaveFileMenuAction;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class MenuBarPanel
{
	private JMenuBar menuBar = new JMenuBar();
	private VersionMaintainMainFrame mainFrame;
	@SuppressWarnings("unused")
    private JPanel parent;
	
	private JMenu fileMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	public MenuBarPanel(VersionMaintainMainFrame mainFrame, JPanel parent)
    {
		this.mainFrame = mainFrame;
		this.parent = parent;
    }
	
	public void initMenuBar()
    {
		initFileMenu();
		initTooMenu();
		initHelpMenu();
		
		menuBar.add(fileMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
		
		Font font = new Font("Segoe UI", Font.BOLD, 13);
		fileMenu.setFont(font);
		toolMenu.setFont(font);
		helpMenu.setFont(font);
		mainFrame.setJMenuBar(menuBar);
    }


	private void initFileMenu()
    {
		fileMenu = new JMenu("File");
		JMenuItem openFileMenuItem = new JMenuItemEx("Open File", KeyEvent.VK_F);
		openFileMenuItem.setIcon(Resource.ICON_FILE_OPEN);
		openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		openFileMenuItem.addActionListener(new OpenFileMenuAction(mainFrame));
		
		JMenuItem newFileMenuItem = new JMenuItemEx("New File", KeyEvent.VK_F);
		newFileMenuItem.setIcon(Resource.ICON_FILE_NEW);
		newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		newFileMenuItem.addActionListener(new NewFileMenuAction(mainFrame));
		
		JMenuItem closeFileMenuItem = new JMenuItemEx("Close File", KeyEvent.VK_C);
		closeFileMenuItem.setIcon(Resource.ICON_FILE_CLOSE);
		closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
		closeFileMenuItem.addActionListener(new CloseFileMenuAction(mainFrame));
		
		JMenuItem saveFileMenuItem = new JMenuItemEx("Save File", KeyEvent.VK_S);
		saveFileMenuItem.setIcon(Resource.ICON_FILE_SAVE);
		saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		saveFileMenuItem.addActionListener(new SaveFileMenuAction(mainFrame));

		JMenuItem createSoftInfoMenuItem = new JMenuItemEx("Create Software Info", KeyEvent.VK_I);
		createSoftInfoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK|KeyEvent.SHIFT_MASK));
		createSoftInfoMenuItem.addActionListener(new CreateSoftwareInfoMenuAction(mainFrame));
		
		JMenuItem deleteSoftInfoMenuItem = new JMenuItemEx("Delete Software Info", KeyEvent.VK_I);
		deleteSoftInfoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK|KeyEvent.SHIFT_MASK));
		deleteSoftInfoMenuItem.addActionListener(new DeleteSoftwareInfoMenuAction(mainFrame));
		
		JMenuItem exitMenuItem = new JMenuItemEx("Exit", KeyEvent.VK_ESCAPE);
		exitMenuItem.setIcon(Resource.ICON_EXIT);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE));
		exitMenuItem.addActionListener(new ExitMenuAction(mainFrame));
		
		fileMenu.add(openFileMenuItem);
		fileMenu.add(newFileMenuItem);
		fileMenu.add(closeFileMenuItem);
		fileMenu.add(saveFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(createSoftInfoMenuItem);
		fileMenu.add(deleteSoftInfoMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
    }
	
	private void initTooMenu()
    {
		helpMenu = new JMenu("Help");
		JMenuItem aboutUsMenuItem = new JMenuItemEx("About Us");
		aboutUsMenuItem.setIcon(Resource.ICON_ABOUT);
		aboutUsMenuItem.addActionListener(new AboutUsMenuAction(mainFrame));
		helpMenu.add(aboutUsMenuItem);
    }
	
	private void initHelpMenu()
    {
		toolMenu = new JMenu("Tool");
		JMenuItem cmdLauncherMenuItem = new JMenuItemEx("Launch CMD");
		cmdLauncherMenuItem.setIcon(Resource.ICON_CMD);
		cmdLauncherMenuItem.addActionListener(new CMDMLauncherenuAction(mainFrame));
		cmdLauncherMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		toolMenu.add(cmdLauncherMenuItem);
    }
	
	
}
