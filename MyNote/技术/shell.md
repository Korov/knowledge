# 1 基础教程

```shell
#!/bin/bash
echo "Hello World !"
```

 \#! 是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行，即使用哪一种 Shell。 

 echo 命令用于向窗口输出文本。  

```bash
chmod +x ./test.sh  #使脚本具有执行权限
./test.sh  #执行脚本
bash test.sh  #使用解释器执行脚本
```

## 1.1 Shell变量

```Shell
your_name="runoob.com"
```

-  变量名和等号之间不能有空格 
-  命名只能使用英文字母，数字和下划线，首个字符不能以数字开头 
-  中间不能有空格，可以使用下划线（_） 
-  不能使用标点符号 
-  不能使用bash里的关键字（可用help命令查看保留关键字） 

 除了显式地直接赋值，还可以用语句给变量赋值，如： 

```Shell
#将 /etc 下目录的文件名循环出来
for file in `ls /etc`
或
for file in $(ls /etc)
```

使用变量，在变量名之前加美元符号

```Shell
your_name="qinjx"
echo $your_name
echo ${your_name} #推荐
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

## 3.2 关系运算符

| 运算符 | 说明                                                  | 举例                       |
| ------ | ----------------------------------------------------- | -------------------------- |
| -eq    | 检测两个数是否相等，相等返回 true。                   | [ $a -eq $b ] 返回 false。 |
| -ne    | 检测两个数是否不相等，不相等返回 true。               | [ $a -ne $b ] 返回 true。  |
| -gt    | 检测左边的数是否大于右边的，如果是，则返回 true。     | [ $a -gt $b ] 返回 false。 |
| -lt    | 检测左边的数是否小于右边的，如果是，则返回 true。     | [ $a -lt $b ] 返回 true。  |
| -ge    | 检测左边的数是否大于等于右边的，如果是，则返回 true。 | [ $a -ge $b ] 返回 false。 |
| -le    | 检测左边的数是否小于等于右边的，如果是，则返回 true。 | [ $a -le $b ] 返回 true。  |

## 3.3 布尔运算符

| 运算符 | 说明                                                | 举例                                     |
| ------ | --------------------------------------------------- | ---------------------------------------- |
| !      | 非运算，表达式为 true 则返回 false，否则返回 true。 | [ ! false ] 返回 true。                  |
| -o     | 或运算，有一个表达式为 true 则返回 true。           | [ $a -lt 20 -o $b -gt 100 ] 返回 true。  |
| -a     | 与运算，两个表达式都为 true 才返回 true。           | [ $a -lt 20 -a $b -gt 100 ] 返回 false。 |

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
    1)  echo '你选择了 1'
    ;;
    2)  echo '你选择了 2'
    ;;
    3)  echo '你选择了 3'
    ;;
    4)  echo '你选择了 4'
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

# 7 输入输出重定向

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

# 8 其他

## 8.1 睡眠

```shell
sleep 1    睡眠1秒
sleep 1s    睡眠1秒
sleep 1m   睡眠1分
sleep 1h   睡眠1小时
```

