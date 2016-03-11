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
 package com.versionmaintain.res;

import javax.swing.ImageIcon;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class Resource
{
	public static final String BIN_FILE_NAME = "version_history_info.bin";
	
	public static ImageIcon ICON_ADD = new ImageIcon(Resource.class.getResource("icon_add.gif"));
	public static ImageIcon ICON_DEL = new ImageIcon(Resource.class.getResource("icon_del.gif"));
	public static ImageIcon ICON_EDIT = new ImageIcon(Resource.class.getResource("icon_edit.png"));
	
	public static ImageIcon ICON_FILE_OPEN = new ImageIcon(Resource.class.getResource("icon_file_open.png"));
	public static ImageIcon ICON_FILE_NEW = new ImageIcon(Resource.class.getResource("icon_file_new.png"));
	public static ImageIcon ICON_FILE_CLOSE = new ImageIcon(Resource.class.getResource("icon_file_close.png"));
	public static ImageIcon ICON_FILE_SAVE = new ImageIcon(Resource.class.getResource("icon_file_save.png"));
	public static ImageIcon ICON_EXIT = new ImageIcon(Resource.class.getResource("icon_exit.png"));
	public static ImageIcon ICON_CMD = new ImageIcon(Resource.class.getResource("icon_cmd.gif"));
	public static ImageIcon ICON_ABOUT = new ImageIcon(Resource.class.getResource("icon_about.png"));
	
	public static ImageIcon ICON_TREE_ROOT = new ImageIcon(Resource.class.getResource("icon_tree_root.png"));
	public static ImageIcon ICON_TREE_DETAIL = new ImageIcon(Resource.class.getResource("icon_tree_detail.png"));
	public static ImageIcon ICON_TREE_INFO = new ImageIcon(Resource.class.getResource("icon_tree_info.png"));
}