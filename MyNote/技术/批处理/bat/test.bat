echo test.bat start time:%date:~0,4%/%date:~5,2%/%date:~8,2% %time%  >> batlog.txt
ipconfig >> batlog.txt
whoami >> batlog.txt

rem 复制并提示是否覆盖
rem 加上/y不提示，并默认覆盖
xcopy a.txt c.txt /y
xcopy a.txt b.txt /y

rem 复制并强制覆盖
copy a.txt b.txt /y

mkdir test

copy a.txt test/a.txt /y

Bandizip\Bandizip.exe c test.zip test\

rem 强制删除文件
del /f/a/q b.txt
rem 强制删除文件夹
rd /s/q test

echo test.bat end time:%date:~0,4%/%date:~5,2%/%date:~8,2% %time%  >> batlog.txt