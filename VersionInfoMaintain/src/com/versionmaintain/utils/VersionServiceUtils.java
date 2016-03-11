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
 package com.versionmaintain.utils;

import java.awt.Font;
import java.io.File;
import java.util.Enumeration;
import java.util.List;

import javax.swing.UIManager;

import com.cats.utils.IColorFontUtil;
import com.versionmaintain.files.EnFileType;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public class VersionServiceUtils
{
	public static EnFileType getFileType(File file)
	{
		if(file == null)
		{
			return null;
		}
		if(file.getName().endsWith("xml"))
		{
			return EnFileType.enXmlFile;
		}else if(file.getName().endsWith("bin"))
		{
			return EnFileType.enBinFile;
		}
		return null;
	}
	
	public static void initGracefulStyle(Font font)
	{
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//windows风格
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}*/
		
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		Object key = null;
		Object value = null;
		while (keys.hasMoreElements())
		{
			key = keys.nextElement();
			value = UIManager.get(key);
			/*if (key instanceof String)
			{
				if (((String) key).endsWith(".background"))
				{
					UIManager.put(key, new Color(0xeeeeee));
				}
			}*/

			if (value instanceof Font)
			{
				UIManager.put(key, font);
			}
		}
		UIManager.put("MenuItem.selectionBackground",IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN);
	}
	
	public static String getDiffInfo(List<String> infoList)
	{
		int iIndex = 1;
		boolean isFind = false;
		String newItem = "NewItem";
		do{
			isFind = false;
			for (String info : infoList)
			{
				if(info.equals(newItem))
				{
					isFind = true;
					newItem = "NewItem"+iIndex;
					iIndex++;
				}
			}
		}while(isFind);
		return newItem;
	}
}
