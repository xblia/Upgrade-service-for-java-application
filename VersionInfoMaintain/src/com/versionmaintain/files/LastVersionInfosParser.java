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
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.cats.version.VersionInfo;
import com.cats.version.VersionInfoDetail;

/**
 * @author xiaobolx
 * 2016年1月22日
 */
public class LastVersionInfosParser
{
	protected static final String FILE_NAME = "version_cfg.xml";

	public List<VersionInfo> getVersionInfo()
	{
		SAXBuilder builder = new SAXBuilder();
		List<VersionInfo> infos = new ArrayList<VersionInfo>();
		try
		{
			Document doc = builder.build(new File(System.getProperty("user.dir")
			        + File.separator + FILE_NAME));
			Element root = doc.getRootElement();
			List<Element> softEles = root.getChildren("software");
			for (Element softEle : softEles)
			{
				String appName = softEle.getAttribute("name").getValue();
				String versionCode = softEle.getChildText("latest-version-code");
				String versionName = softEle.getChildText("latest-version");
				
				Element detailEles = softEle.getChild("latest-version-detail");
				List<Element> detailItemEles = detailEles.getChildren("item");
				List<VersionInfoDetail> details = new ArrayList<VersionInfoDetail>();
				for (Element detailItem : detailItemEles)
                {
					String title = detailItem.getAttributeValue("name");
					List<Element> detailEleList = detailItem.getChildren("detail");
					List<String> detailList = new ArrayList<String>();
					for (Element detailEle : detailEleList)
                    {
	                    String strDetail = detailEle.getText();
	                    detailList.add(strDetail);
                    }
					details.add(new VersionInfoDetail(title, detailList));
                }
				
				VersionInfo versionInfo = new VersionInfo();
				versionInfo.setAppName(appName);
				versionInfo.setVersion(versionName);
				versionInfo.setVersionCode(Integer.parseInt(versionCode));
				versionInfo.setDetails(details);
				infos.add(versionInfo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return infos;
	}
}