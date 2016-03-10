#coding=utf-8
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