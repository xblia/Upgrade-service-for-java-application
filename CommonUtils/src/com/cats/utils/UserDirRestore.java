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
 package com.cats.utils;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author xiaobolx
 * 2015年10月30日
 */
public class UserDirRestore
{
	private static String getRealUserDir()
	{
		URL url = UserDirRestore.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;
		try
		{
			filePath = URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (null != filePath && filePath.endsWith(".jar"))
		{
			try
            {
	            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
	            File file = new File(filePath);
	            filePath = file.getAbsolutePath();// 得到windows下的正确路径
	            return filePath;
            } catch (Exception e)
            {
	            e.printStackTrace();
            }
		}
		
		return System.getProperty("user.dir");
	}
	
	public static void restoreUserDir()
	{
		System.setProperty("user.dir", getRealUserDir());
	}
}
