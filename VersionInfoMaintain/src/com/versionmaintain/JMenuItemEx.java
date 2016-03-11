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
 package com.versionmaintain;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class JMenuItemEx extends JMenuItem
{

    private static final long serialVersionUID = 1L;
    
    public JMenuItemEx(Icon icon) {
        super(null, icon);
        initView();
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text.
     *
     * @param text the text of the <code>JMenuItem</code>
     */
    public JMenuItemEx(String text) {
    	super(text, (Icon)null);
    	initView();
    }
    
    /**
     * Creates a <code>JMenuItem</code> with the specified text and icon.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public JMenuItemEx(String text, Icon icon) {
    	super(text, icon);
    	initView();
    }
    
    public JMenuItemEx(String text, int mnemonic) {
    	super(text, mnemonic);
    	initView();
    }
    
    private void initView()
    {
    	this.setBorder(new EmptyBorder(8, 8, 8, 8));
    }
}
