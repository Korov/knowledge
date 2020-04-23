# 1 基础教程

```shell
#!/bin/bash
echo "Hello World !"
```

 \#! 是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行，即使用哪一种 Shell。 

```bash
#!/usr/bin/env java
#表示从当前的环境变量中找到java可执行命令，，推荐使用这种方式。
```

echo 命令用于向窗口输出文本。  

```bash
chmod +x ./test.sh  #使脚本具有执行权限
./test.sh  #执行脚本
bash test.sh  #使用解释器执行脚本
```

## 1.1 Shell变量

```Shell
my_name="korov"
```

-  变量名和等号之间不能有空格 
-  命名只能使用英文字母，数字和下划线，首个字符不能以数字开头 
-  中间不能有空格，可以使用下划线（_） 
-  不能使用标点符号 
-  不能使用bash里的关键字（可用help命令查看保留关键字） 

 除了显式地直接赋值，还可以用语句给变量赋值，如： 

```Shell
#将 /etc 下目录的文件名循环出来
for file in `ls /etc`; do
    echo ${file}
done
或
for file in $(ls /etc); do
    echo ${file}
done
```

使用变量，在变量名之前加美元符号

```Shell
my_name="korov"
echo $my_name
echo ${my_name} #推荐
```

 变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界，比如下面这种情况：  

```Shell
for skill in Ada Coffe Action Java; do
    echo "I am good at ${skill}Script"
done
```

```Shell
#只读变量
readonly variable_name
#删除变量
unset variable_name
```

**变量类型**

-  **局部变量** 局部变量在脚本或命令中定义，仅在当前shell实例中有效，其他shell启动的程序不能访问局部变量 
-  **环境变量** 所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。 
-  **shell变量** shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行 

## 1.1 Shell字符串

 单引号字符串的限制：  

-  单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的 
-  单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用 

双引号：

-  双引号里可以有变量 
-  双引号里可以出现转义字符 

```Shell
#获取字符串长度
string="abcd"
echo ${#string} #输出 4
#提取子字符串
string="runoob is a great site"
echo ${string:1:4} # 输出 unoo
#查找子字符串
string="runoob is a great site"
echo `expr index "$string" io`  # 输出 4
```

## 1.2 数组

 bash支持一维数组（不支持多维数组），并且没有限定数组的大小。  数组元素的下标由 0 开始编号。获取数组中的元素要利用下标，下标可以是整数或算术表达式，其值应大于或等于 0。  

**定义数组：数组名=(值1 值2 ... 值n)**

可以单独定义数组的各个分量

```Shell
array_name[0]=value0
array_name[1]=value1
array_name[n]=valuen
```

读取数组

```Shell
valuen=${array_name[n]}
#使用@符号可以获取数组中所有元素
echo ${array_name[@]}

# 取得数组元素的个数
length=${#array_name[@]}
# 或者
length=${#array_name[*]}
# 取得数组单个元素的长度
lengthn=${#array_name[n]}
```

## 1.3 注释

单行注释：#

多行注释：

```Shell
:<<EOF
注释内容...
注释内容...
注释内容...
EOF


#EOF 也可以使用其他符号:
:<<'
注释内容...
注释内容...
注释内容...
'

:<<!
注释内容...
注释内容...
注释内容...
!
```

# 2 Shell传递参数

 我们可以在执行 Shell 脚本时，向脚本传递参数，脚本内获取参数的格式为：**$n**。**n** 代表一个数字，1 为执行脚本的第一个参数，2 为执行脚本的第二个参数，以此类推…… 

脚本：

```Shell
#!/bin/bash
# author:菜鸟教程
# url:www.runoob.com

echo "Shell 传递参数实例！";
echo "执行的文件名：$0";
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";
```

执行结果：

```Shell
$ chmod +x test.sh 
$ ./test.sh 1 2 3
Shell 传递参数实例！
执行的文件名：./test.sh
第一个参数为：1
第二个参数为：2
第三个参数为：3
```

# 3 Shell基本运算符

## 3.1 算术运算符

| 运算符 | 说明                                          | 举例                          |
| ------ | --------------------------------------------- | ----------------------------- |
| +      | 加法                                          | `expr $a + $b` 结果为 30。    |
| -      | 减法                                          | `expr $a - $b` 结果为 -10。   |
| *      | 乘法                                          | `expr $a \* $b` 结果为  200。 |
| /      | 除法                                          | `expr $b / $a` 结果为 2。     |
| %      | 取余                                          | `expr $b % $a` 结果为 0。     |
| =      | 赋值                                          | a=$b 将把变量 b 的值赋给 a。  |
| ==     | 相等。用于比较两个数字，相同则返回 true。     | [ $a == $b ] 返回 false。     |
| !=     | 不相等。用于比较两个数字，不相同则返回 true。 | [ $a != $b ] 返回 true。      |

```sh
#!/bin/bash

a=10
b=20

val=`expr $a + $b`
echo "a + b : $val"

val=`expr $a - $b`
echo "a - b : $val"

val=`expr $a \* $b`
echo "a * b : $val"

val=`expr $b / $a`
echo "b / a : $val"

val=`expr $b % $a`
echo "b % a : $val"

if [ $a == $b ]
then
   echo "a 等于 b"
fi
if [ $a != $b ]
then
   echo "a 不等于 b"
fi
```

## 3.2 关系运算符

| 运算符 | 说明                                                  | 举例                       |
| ------ | ----------------------------------------------------- | -------------------------- |
| -eq    | 检测两个数是否相等，相等返回 true。                   | [ $a -eq $b ] 返回 false。 |
| -ne    | 检测两个数是否不相等，不相等返回 true。               | [ $a -ne $b ] 返回 true。  |
| -gt    | 检测左边的数是否大于右边的，如果是，则返回 true。     | [ $a -gt $b ] 返回 false。 |
| -lt    | 检测左边的数是否小于右边的，如果是，则返回 true。     | [ $a -lt $b ] 返回 true。  |
| -ge    | 检测左边的数是否大于等于右边的，如果是，则返回 true。 | [ $a -ge $b ] 返回 false。 |
| -le    | 检测左边的数是否小于等于右边的，如果是，则返回 true。 | [ $a -le $b ] 返回 true。  |

```sh
#!/bin/bash

a=10
b=20

if [ $a -eq $b ]
then
   echo "$a -eq $b : a 等于 b"
else
   echo "$a -eq $b: a 不等于 b"
fi
if [ $a -ne $b ]
then
   echo "$a -ne $b: a 不等于 b"
else
   echo "$a -ne $b : a 等于 b"
fi
if [ $a -gt $b ]
then
   echo "$a -gt $b: a 大于 b"
else
   echo "$a -gt $b: a 不大于 b"
fi
if [ $a -lt $b ]
then
   echo "$a -lt $b: a 小于 b"
else
   echo "$a -lt $b: a 不小于 b"
fi
if [ $a -ge $b ]
then
   echo "$a -ge $b: a 大于或等于 b"
else
   echo "$a -ge $b: a 小于 b"
fi
if [ $a -le $b ]
then
   echo "$a -le $b: a 小于或等于 b"
else
   echo "$a -le $b: a 大于 b"
fi
```

## 3.3 布尔运算符

| 运算符 | 说明                                                | 举例                                     |
| ------ | --------------------------------------------------- | ---------------------------------------- |
| !      | 非运算，表达式为 true 则返回 false，否则返回 true。 | [ ! false ] 返回 true。                  |
| -o     | 或运算，有一个表达式为 true 则返回 true。           | [ $a -lt 20 -o $b -gt 100 ] 返回 true。  |
| -a     | 与运算，两个表达式都为 true 才返回 true。           | [ $a -lt 20 -a $b -gt 100 ] 返回 false。 |

```sh
#!/bin/bash

a=10
b=20

if [ $a != $b ]
then
   echo "$a != $b : a 不等于 b"
else
   echo "$a == $b: a 等于 b"
fi
if [ $a -lt 100 -a $b -gt 15 ]
then
   echo "$a 小于 100 且 $b 大于 15 : 返回 true"
else
   echo "$a 小于 100 且 $b 大于 15 : 返回 false"
fi
if [ $a -lt 100 -o $b -gt 100 ]
then
   echo "$a 小于 100 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 100 或 $b 大于 100 : 返回 false"
fi
if [ $a -lt 5 -o $b -gt 100 ]
then
   echo "$a 小于 5 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 5 或 $b 大于 100 : 返回 false"
fi
```

## 3.4 逻辑运算符

| 运算符 | 说明       | 举例                                       |
| ------ | ---------- | ------------------------------------------ |
| &&     | 逻辑的 AND | [[ $a -lt 100 && $b -gt 100 ]] 返回 false  |
| \|\|   | 逻辑的 OR  | [[ $a -lt 100 \|\| $b -gt 100 ]] 返回 true |

## 3.5 字符串运算符

| 运算符 | 说明                                      | 举例                     |
| ------ | ----------------------------------------- | ------------------------ |
| =      | 检测两个字符串是否相等，相等返回 true。   | [ $a = $b ] 返回 false。 |
| !=     | 检测两个字符串是否相等，不相等返回 true。 | [ $a != $b ] 返回 true。 |
| -z     | 检测字符串长度是否为0，为0返回 true。     | [ -z $a ] 返回 false。   |
| -n     | 检测字符串长度是否为0，不为0返回 true。   | [ -n "$a" ] 返回 true。  |
| $      | 检测字符串是否为空，不为空返回 true。     | [ $a ] 返回 true。       |

## 3.6 文件测试运算符

| 操作符  | 说明                                                         | 举例                      |
| ------- | ------------------------------------------------------------ | ------------------------- |
| -b file | 检测文件是否是块设备文件，如果是，则返回 true。              | [ -b $file ] 返回 false。 |
| -c file | 检测文件是否是字符设备文件，如果是，则返回 true。            | [ -c $file ] 返回 false。 |
| -d file | 检测文件是否是目录，如果是，则返回 true。                    | [ -d $file ] 返回 false。 |
| -f file | 检测文件是否是普通文件（既不是目录，也不是设备文件），如果是，则返回 true。 | [ -f $file ] 返回 true。  |
| -g file | 检测文件是否设置了 SGID 位，如果是，则返回 true。            | [ -g $file ] 返回 false。 |
| -k file | 检测文件是否设置了粘着位(Sticky Bit)，如果是，则返回 true。  | [ -k $file ] 返回 false。 |
| -p file | 检测文件是否是有名管道，如果是，则返回 true。                | [ -p $file ] 返回 false。 |
| -u file | 检测文件是否设置了 SUID 位，如果是，则返回 true。            | [ -u $file ] 返回 false。 |
| -r file | 检测文件是否可读，如果是，则返回 true。                      | [ -r $file ] 返回 true。  |
| -w file | 检测文件是否可写，如果是，则返回 true。                      | [ -w $file ] 返回 true。  |
| -x file | 检测文件是否可执行，如果是，则返回 true。                    | [ -x $file ] 返回 true。  |
| -s file | 检测文件是否为空（文件大小是否大于0），不为空返回 true。     | [ -s $file ] 返回 true。  |
| -e file | 检测文件（包括目录）是否存在，如果是，则返回 true。          | [ -e $file ] 返回 true。  |

# 4 printf

printf类似于C语言中

**printf的转义序列**

| 序列  | 说明                                                         |
| ----- | ------------------------------------------------------------ |
| \a    | 警告字符，通常为ASCII的BEL字符                               |
| \b    | 后退                                                         |
| \c    | 抑制（不显示）输出结果中任何结尾的换行字符（只在%b格式指示符控制下的参数字符串中有效），而且，任何留在参数里的字符、任何接下来的参数以及任何留在格式字符串中的字符，都被忽略 |
| \f    | 换页（formfeed）                                             |
| \n    | 换行                                                         |
| \r    | 回车（Carriage return）                                      |
| \t    | 水平制表符                                                   |
| \v    | 垂直制表符                                                   |
| \\    | 一个字面上的反斜杠字符                                       |
| \ddd  | 表示1到3位数八进制值的字符。仅在格式字符串中有效             |
| \0ddd | 表示1到3位的八进制值字符                                     |

```Shell
$ printf "a string, no processing:<%s>\n" "A\nB"
a string, no processing:<A\nB>

$ printf "a string, no processing:<%b>\n" "A\nB"
a string, no processing:<A
B>

$ printf "www.runoob.com \a"
www.runoob.com $                  #不换行
```

# 5 Shell控制流

## 5.1 if else 

```Shell
# if语法格式
if condition
then
    command1 
    command2
    ...
    commandN 
fi

#写成一行
if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi

#if else 
if condition
then
    command1 
    command2
    ...
    commandN
else
    command
fi

#if else-if else
if condition1
then
    command1
elif condition2 
then 
    command2
else
    commandN
fi
```

## 5.2 for循环

```Shell
for var in item1 item2 ... itemN
do
    command1
    command2
    ...
    commandN
done

#写成一行
for var in item1 item2 ... itemN; do command1; command2… done;
```

## 5.3 while语句

```Shell
while condition
do
    command
done
```

## 5.4 无限循环

```Shell
while :
do
    command
done

while true
do
    command
done

for (( ; ; ))
do
    command
done
```

## 5.5 until循环

```Shell
#condition为true的时候才停止循环
until condition
do
    command
done
```

## 5.6 case

```Shell
case 值 in
模式1)
    command1
    command2
    ...
    commandN
    ;;
模式2）
    command1
    command2
    ...
    commandN
    ;;
esac

#示例
echo '输入 1 到 4 之间的数字:'
echo '你输入的数字为:'
read aNum
case $aNum in
    1)
    echo "Hello"
    echo '你选择了 1'
    ;;
    2)
    echo "Hello"
    echo '你选择了 2'
    ;;
    3)
    echo "Hello"
    echo '你选择了 3'
    ;;
    4)
    echo "Hello"
    echo '你选择了 4'
    ;;
    *)  echo '你没有输入 1 到 4 之间的数字'
    ;;
esac
```

## 5.7 跳出循环

break：跳出所有命令

continue：跳出当前循环

# 6 Shell函数

```Shell
[ function ] funname [()]
{
    action;
    [return int;]
}
```

可以带有入参，和shell脚本给的入参一致

# 7 标准输入输出

标准输入stdin fd=0使用<或<<

标准输入stdout fd=1使用>或>>

标准错误stderr fd=2使用2>或者2>>

##  输入输出重定向

| 命令            | 说明                                               |
| --------------- | -------------------------------------------------- |
| command > file  | 将输出重定向到 file。                              |
| command < file  | 将输入重定向到 file。                              |
| command >> file | 将输出以追加的方式重定向到 file。                  |
| n > file        | 将文件描述符为 n 的文件重定向到 file。             |
| n >> file       | 将文件描述符为 n 的文件以追加的方式重定向到 file。 |
| n >& m          | 将输出文件 m 和 n 合并。                           |
| n <& m          | 将输入文件 m 和 n 合并。                           |
| << tag          | 将开始标记 tag 和结束标记 tag 之间的内容作为输入。 |

 如果希望执行某个命令，但又不希望在屏幕上显示输出结果，那么可以将输出重定向到 /dev/null： 

```bash
cat > 1.txt <<EOF
 > temp       
 > oher
 > EOF
```

首先生命一个结束标志EOF,接着输入字符一直等到你输入EOF的时候结束输入,cat会将你的输入重定向到1.txt中,在修改脚本的时候有用.

如果想要将标准输出和错误输出同时输出需要使用如下命令

```bash
{command} > file 2>$1
```

bash的执行顺序是从右向左,首先会执行2>$1将错误输出和标准输出合并,然后将标准输出重定向到文件中.

# 管道

将一个命令的标准输出作为另一个命令的标准输入

![image-20200423235734697](picture/shell.md)

# sed批量替换字符串

```sh
sed -i "s/原字符串/新字符串/g" `grep 原字符串 -rl 所在目录`
#例如：我要把 charset=gb2312 替换为 charset=UTF-8，执行命令：
sed -i "s/charset=gb2312/charset=UTF-8/g" `grep charset=gb2312 -rl /www`

-i 表示inplace edit，就地修改文件
-r 表示搜索子目录
-l 表示输出匹配的文件名
```

## 删除：d命令

```sh
#删除example文件的第二行。
sed '2d' example
#删除example文件的第二行到末尾所有行
sed '2,$d' example
#删除example文件的最后一行。
sed '$d' example
#删除example文件所有包含test的行
sed '/test/'d example
```

## 替换：s命令

```sh
# 在整行范围内把test替换为mytest。如果没有g标记，则只有每行第一个匹配的test被替换成mytest。
sed 's/test/mytest/g' example
# (-n)选项和p标志一起使用表示只打印那些发生替换的行。也就是说，如果某一行开头的test被替换成mytest，就打印它。
sed -n 's/^test/mytest/p' example
# &符号表示替换换字符串中被找到的部份。所有以192.168.0.1开头的行都会被替换成它自已加localhost，变成192.168.0.1localhost
sed 's/^192.168.0.1/&localhost/'example
#love被标记为1，所有loveable会被替换成lovers，而且替换的行会被打印出来 和正则替换很类似
sed -n 's/\(love\)able/\1rs/p' example
# 不论什么字符，紧跟着s命令的都被认为是新的分隔符，所以，“#”在这里是分隔符，代替了默认的“/”分隔符。表示把所有10替换成100
 sed 's#10#100#g' example
```

## 选定行的范围：逗号

`$ sed -n '/test/,/check/p' exampl`
所有在模板test和check所确定的范围内的行都被打印。

`$ sed -n '5,/^test/p' example`
打印从第五行开始到第一个包含以test开始的行之间的所有行。

`$ sed '/test/,/check/s/$/sed test/' example`
对于模板test和west之间的行，每行的末尾用字符串sed test替换。

## 多点编辑：e命令

`$ sed -e '1,5d' -e 's/test/check/'example`
(-e)选项允许在同一行里执行多条命令。如例子所示，第一条命令删除1至5行，第二条命令用check替换test。命令的执行顺序对结果有影响。如果两个命令都是替换命令，那么第一个替换命令将影响第二个替换命令的结果。

`$ sed --expression='s/test/check/' --expression='/love/d' example`
一个比-e更好的命令是–expression。它能给sed表达式赋值。 

## 从文件读入：r命令

`$ sed '/test/r file' example`
file里的内容被读进来，显示在与test匹配的行后面，如果匹配多行，则file的内容将显示在所有匹配行的下面。

## 写入文件：w命令

`$ sed -n '/test/w file' example`
在example中所有包含test的行都被写入file里。 

## 追加命令：a命令

`$ sed '/^test/a\\--->this is a example' example<`
‘this is a example’被追加到以test开头的行后面，sed要求命令a后面有一个反斜杠。

## 插入：i命令

```
$ sed '/test/i\\new line-------------------------' example
```

如果test被匹配，则把反斜杠后面的文本插入到匹配行的前面。

## 下一个：n命令

`$ sed '/test/{ n; s/aa/bb/; }' example`
如果test被匹配，则移动到匹配行的下一行，替换这一行的aa，变为bb，并打印该行，然后继续。

## 变形：y命令

`$ sed '1,10y/abcde/ABCDE/' example`
把1–10行内所有abcde转变为大写，注意，正则表达式元字符不能使用这个命令。

## 退出：q命令

`$ sed '10q' example`
打印完第10行后，退出sed。

## 保持和获取：h命令和G命令

`$ sed -e '/test/h' -e '$Gexample`
在sed处理文件的时候，每一行都被保存在一个叫模式空间的临时缓冲区中，除非行被删除或者输出被取消，否则所有被处理的行都将打印在屏幕上。接着模式空间被清空，并存入新的一行等待处理。在这个例子里，匹配test的行被找到后，将存入模式空间，h命令将其复制并存入一个称为保持缓存区的特殊缓冲区内。第二条语句的意思是，当到达最后一行后，G命令取出保持缓冲区的行，然后把它放回模式空间中，且追加到现在已经存在于模式空间中的行的末尾。在这个例子中就是追加到最后一行。简单来说，任何包含test的行都被复制并追加到该文件的末尾。

## 保持和互换：h命令和x命令

`$ sed -e '/test/h' -e '/check/x' example`
互换模式空间和保持缓冲区的内容。也就是把包含test与check的行互换。

# 命令的四要素

如果四要素完全相同，那么命令一定可以重现

四要素：

1. 可执行程序
2. 参数
3. 工作路径
4. 环境变量

## 环境变量

是和进程相绑定的一组键值对，子进程可以继承父进程的所有环境变量，但是不能逆向传递。

### 常用的环境变量

PATH，存放系统中常用命令

PS1,命令前面提示符的格式

```bash
┌─[korov@korov-pc] - [~/Desktop/temp] - [2020-04-23 10:04:10]
└─[0] <> 
```

LC_ALL指明当前区域

LANG语言

### 如何设置全局变量

要根据你启动的窗口是什么种类，如果启动的是bash窗口，那么启动bash的时候会读取~/.bashrc文件，zsh会读取~/.zshrc文件。

### 可执行程序

- 什么是可执⾏程序？ 
  -  Windows: exe/bat/com 
  - UNIX: +x 
- Shell如何找到可执⾏程序？ 
  - 内置命令 
  - alias 
  - 挨个在PATH⾥找，能找到则执⾏，找不到就报错 
  - 额外的，Windows会在当前⽬录⾥找

## 参数

参数完全有对应的可执行程序负责解析

但是Shell可能偷偷的在参数中动手脚

- 通配符展开

  >例如你传递一个*.sh并且当前文件夹下有可以匹配到的文件则会传递匹配的文件而不是 *.sh

- 变量展开

  - Shell中的变量
  - $(),``

  >例如你传递一个A$B并且B是一个环境变量值为Value,那么`echo "A$B"`的值为`AValue`,`$B`被当作参数展开了

- 双引号与单引号

规避展开:使用`\`转义,可以使用单引号

# 个人总结

## 睡眠

```shell
sleep 1    睡眠1秒
sleep 1s    睡眠1秒
sleep 1m   睡眠1分
sleep 1h   睡眠1小时
```

## pstree

查看当前的所有进程

## source和exec

source在当前进程中执行命令，例如在当前进程中使用source执行了一个shell脚本里面有export设置变量，执行完成之后本进程中可以使用此变量，如果使用shell执行此脚本则会新开启一个shell进程执行此脚本，执行完成后shell进程退出export设置的变量也会失效。

exec强制替换当前线程为其exec之后的线程，exec之后的执行命令将会被抛弃：

```bash
#脚本文件
exec echo "Hello"
echo "Hello1"


#执行结果
Hello
```

