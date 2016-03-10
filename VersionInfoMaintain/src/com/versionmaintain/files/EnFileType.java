package com.versionmaintain.files;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public enum EnFileType
{
	enBinFile(".bin"),
	enXmlFile(".xml");
	
	String extendName;
	private EnFileType(String name)
    {
		this.extendName = name;
    }
	
	public String getExtendName()
	{
		return this.extendName;
	}
}