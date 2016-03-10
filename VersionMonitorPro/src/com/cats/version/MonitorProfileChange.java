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
 package com.cats.version;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.res.Resource;

public class MonitorProfileChange extends Thread
{
	private String fileName;
	private IProfileChangeListener profileChangeListener;
	
	public MonitorProfileChange(String fileName,
            IProfileChangeListener profileChangeListener)
    {
	    super();
	    this.fileName = fileName;
	    this.profileChangeListener = profileChangeListener;
    }
	
	public void startMonitor()
	{
		this.start();
	}
	
	@Override
	public void run()
	{
		monitorProfle();
	}
	public void monitorProfle()
	{
		WatchService watchService = null;
		try
		{
			watchService = FileSystems.getDefault().newWatchService();

			Path dir = Paths.get(fileName);

			dir.register(watchService,

			StandardWatchEventKinds.ENTRY_CREATE,

			StandardWatchEventKinds.ENTRY_DELETE,

			StandardWatchEventKinds.ENTRY_MODIFY,

			StandardWatchEventKinds.OVERFLOW);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		while (true)

		{

			WatchKey key = null;
			try
			{
				key = watchService.take();

				key.reset();

				for (WatchEvent<?> event : key.pollEvents())
				{
					if(event.context().toString().contains(VersionCfgParseAndSave.FILE_NAME))
					{
						profileChangeListener.notifyChange(event.context(), event.kind());
					}
					else if(event.context().toString().contains(Resource.BIN_FILE_NAME))
					{
						synchronized(FileDataMgr.getInstance())
						{
							FileDataMgr.getInstance().closeFile();
						}
					}
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	}
}
