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
 package com.versionmaintain.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import com.cats.utils.IOUtils;
import com.cats.version.VersionInfo;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class VersionBinFile extends IVersionInfoFile
{
	public VersionBinFile(File file)
    {
	    super(file);
    }

	@SuppressWarnings("unchecked")
	@Override
    public boolean loadFile()
	{
		if(null == file || !file.exists())
		{
			return false;
		}
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		boolean loadSucc = true;
		try
        {
	        fis = new FileInputStream(file);
	        ois = new ObjectInputStream(fis);
	        data = (Map<String, List<VersionInfo>>)ois.readObject();
        } catch (FileNotFoundException e)
        {
	        e.printStackTrace();
	        loadSucc = false;
        } catch (ClassNotFoundException e)
        {
	        e.printStackTrace();
	        loadSucc = false;
        } catch (IOException e)
        {
	        e.printStackTrace();
	        loadSucc = false;
        }catch(Exception e)
		{
        	e.printStackTrace();
        	loadSucc = false;
		}finally
		{
			IOUtils.closeResource(fis);
			IOUtils.closeResource(ois);
		}
		return loadSucc;
	}
	
	@Override
	public boolean saveData()
	{
		if(null == file || !file.exists())
		{
			return false;
		}
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean saveSucc = true;
		try
        {
	        fos = new FileOutputStream(file);
	        oos = new ObjectOutputStream(fos);
	        oos.writeObject(data);
        } catch (Exception e)
        {
        	e.printStackTrace();
        	saveSucc = false;
        }finally
        {
        	IOUtils.closeResource(fos);
        	IOUtils.closeResource(oos);
        }
		
		return saveSucc;
	}
}
