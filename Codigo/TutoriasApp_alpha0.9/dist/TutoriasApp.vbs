strBatFile ="Launcher.bat" 
dim fso, fullPathToBat
set fso = CreateObject("Scripting.FileSystemObject")
fullPathToBat = fso.GetAbsolutePathName(strBatFile)
Set WshShell = CreateObject("WScript.Shell") 
WshShell.Run chr(34) & fullPathToBat & Chr(34), 0
Set WshShell = Nothing