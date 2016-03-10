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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.cats.version.msg.IMessageVersionUpdateRsp;
import com.cats.version.perference.UserPreference;

/**
 * @author xblia2 Jun 11, 2015
 */
public class HttpDownloadFiles extends Thread
{

	private static final int CACHE = 10 * 1024;
	private static int iMaxTry = 3;
	private IMessageVersionUpdateRsp updateRsp;
	private IUpdateStatusListener statusListener;
	private String lastError = "";
	
	public HttpDownloadFiles(IMessageVersionUpdateRsp updateRsp, IUpdateStatusListener statusListener)
    {
	    super();
	    this.updateRsp = updateRsp;
	    this.statusListener = statusListener;
    }

	@Override
	public void run()
	{
		List<String> fileList = updateRsp.getUpdateFilePath();
		String baseUrl = UserPreference.getInstance().getUrl();
		if(fileList != null && !fileList.isEmpty())
		{
			for (int i = 0; i < fileList.size(); i++)
			{
				String fileFullName = fileList.get(i);
				statusListener.notify(fileFullName, fileList.size(), i+1);
				int iTry = 0;
				boolean isDownSucc = false;
				lastError = "";
				while(!(isDownSucc = download(baseUrl + "/"+fileFullName, parseFileName(fileFullName))))
				{
					if(iTry >= iMaxTry)
					{
						break;
					}
					iTry++;
				}
				if(!isDownSucc)
				{
					statusListener.notifyException(lastError);
				}
			}
			statusListener.notifyFinished();
		}else
		{
			statusListener.notifyException("Not found upgrade file.");
		}
	}
	
	private String parseFileName(String fileFullName)
    {
		File file = new File(fileFullName);
	    return file.getName();
    }

	public boolean download(String url, String fileName)
	{
		try
		{
			HttpClient client = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			if(response.getStatusLine().getStatusCode() != 200)
			{
				lastError = "Error status code: " + response.getStatusLine().getStatusCode();
				return false;
			}
			Header []headers = response.getHeaders("file_flag");
			if(headers == null || headers.length == 0 || !headers[0].getValue().equals("yes"))
			{
				lastError = "parse httpdown response header fail";
				return false;
			}
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			File file = new File(System.getProperty("user.dir")
			        + File.separator + fileName);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			byte[] buffer = new byte[CACHE];
			int iLen = 0;
			while ((iLen = is.read(buffer)) != -1)
			{
				fileout.write(buffer, 0, iLen);
			}
			is.close();
			fileout.flush();
			fileout.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			lastError = e.getMessage();
			return false;
		}
		return true;
	}
}
