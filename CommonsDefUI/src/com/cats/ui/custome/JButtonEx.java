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
 package com.cats.ui.custome;


import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;

/**
 * @author xblia
 * 2015年10月19日
 */
public class JButtonEx extends JButton
{
    private static final long serialVersionUID = -319192911384600429L;

	public JButtonEx(String title)
	{
		super(title);
		initView();
	}
	
	public JButtonEx(String title, boolean isEnable)
	{
		super(title);
		initView();
		this.setEnabled(isEnable);
	}
	
	private void initView()
	{
		this.setFocusPainted(false);
		this.setBackground(new Color(0xf5,0xf5,0xf5));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
