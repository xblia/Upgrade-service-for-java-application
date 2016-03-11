#coding=utf-8
'''/*
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
 */'''
'''
Created on 2015年12月30日

@author: xiaobolx
'''
import sys

from cx_Freeze import setup, Executable


# Dependencies are automatically detected, but it might need fine tuning.
build_exe_options = {"packages": ["os"], "excludes": ["tkinter"]}
# GUI applications require a different base on Windows (the default is for a
# console application).
base = None
if sys.platform == "win32":
    base = "Win32GUI"
setup(name = "PyUpgradeDeamon",
    targetName="PyUpgradeDeamon.exe",
    version = "0.1",
    author="li xiaobo.",
    description = "PyUpgradeDeamon.!",
    options = {'build_exe': {'init_script':'Console'}},
    executables = [Executable("deamon/UpgradePyDeamon.py", base=base)])