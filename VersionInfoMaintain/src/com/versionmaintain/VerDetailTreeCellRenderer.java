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

import java.awt.Component;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.cats.version.VersionInfoDetail;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月21日
 */
public class VerDetailTreeCellRenderer extends DefaultTreeCellRenderer
{

    private static final long serialVersionUID = 1L;
	
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus)
    {
    	
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);
        if(value instanceof List)
        {
        	this.setIcon(Resource.ICON_TREE_ROOT);
        }else if(value instanceof VersionInfoDetail)
        {
        	this.setIcon(Resource.ICON_TREE_DETAIL);
        }else if(value instanceof String)
        {
        	this.setIcon(Resource.ICON_TREE_INFO);
        }
        return this;
    }
}
