# ProGit

## 1.起步

git直接记录快照，而非差异比较，git像是把数据看作是对小型文件系统的一组快照。每次你提交更新，或在git中保存项目状态时，它主要对当时的全部文件制作一个快照并保存这个快照的索引。为了高效，如果文件没有修改，git不再重新存储该文件，而是只保留一个链接指向之前存储的文件。git对待数据更像是一个快照流。

![1569691862806](picture\1569691862806.png)

git中所有数据在存储前都计算校验和，然后以检验和来引用。git使用SHA-1来计算校验和。

### 1.1git一般只添加数据

你的操作几乎只往git数据库中增加数据，这保证你很难执行任何不可逆操作。

### 1.2三种状态

git有三种状态，你的文件可能处于其中之一：已提交（committed），已修改（modified），已暂存（staged）。已提交表示数据已经安全的保存在本地数据库中。已修改表示修改了文件，但还没有保存到数据库中。已暂存表示对一个已修改文件的当前版本做了标记，使之包含在下次提交的快照中。

由此引入git项目的三个工作区域的概念：git仓库、工作目录以及暂存区域

![1569692284314](picture\1569692284314.png)

Git 仓库目录是 Git 用来保存项目的元数据和对象数据库的地方。 这是 Git 中最重要的部分，从其它计算机克隆仓库时，拷贝的就是这里的数据。
工作目录是对项目的某个版本独立提取出来的内容。 这些从 Git 仓库的压缩数据库中提取出来的文件，放在磁盘上供你使用或修改。
暂存区域是一个文件，保存了下次将提交的文件列表信息，一般在 Git 仓库目录中。 有时候也被称作`‘索引’'，不过一般说法还是叫暂存区域。
基本的 Git 工作流程如下：

1. 在工作目录中修改文件。
2. 暂存文件，将文件的快照放入暂存区域。
3. 提交更新，找到暂存区域的文件，将快照永久性存储到 Git 仓库目录。

如果 Git 目录中保存着特定版本的文件，就属于已提交状态。 如果作了修改并已放入暂存区域，就属于已暂存状态。 如果自上次取出后，作了修改但还没有放到暂存区域，就是已修改状态。 在Git 基础一章，你会进一步了解这些状态的细节，并学会如何根据文件状态实施后续操作，以及怎样跳过暂存直接提交。

### 1.3安装git

#### 初次运行前的配置

Git 自带一个 git config 的工具来帮助设置控制 Git 外观和行为的配置变量。 这些变量存储在三个不同的位置：

1. /etc/gitconfig 文件: 包含系统上每一个用户及他们仓库的通用配置。 如果使用带有 --system 选项的git config 时，它会从此文件读写配置变量。
2. ~/.gitconfig 或 ~/.config/git/config 文件：只针对当前用户。 可以传递 --global 选项让 Git读写此文件。
3. 当前使用仓库的 Git 目录中的 config 文件（就是 .git/config）：针对该仓库。

每一个级别覆盖上一级别的配置，所以 .git/config 的配置变量会覆盖 /etc/gitconfig 中的配置变量

#### 用户信息

当安装完 Git 应该做的第一件事就是设置你的用户名称与邮件地址。 这样做很重要，因为每一个 Git 的提交都会使用这些信息，并且它会写入到你的每一次提交中，不可更改：

```bash
$ git config --global user.name "John Doe"
$ git config --global user.email johndoe@example.com
```

#### 文本编辑器

Git 会使用操作系统默认的文本编辑器，通常是 Vim。 如果你想使用不同的文本编辑器，例如 Emacs，可以这样做：

```bash
$ git config --global core.editor emacs
```

#### 检查配置信息

如果想要检查你的配置，可以使用 git config --list 命令来列出所有 Git 当时能找到的配置

```bash
$ git config --list
user.name=John Doe
user.email=johndoe@example.com
color.status=auto
color.branch=auto
color.interactive=auto
color.diff=auto
...
```

你可能会看到重复的变量名，因为 Git 会从不同的文件中读取同一个配置（例如：/etc/gitconfig 与~/.gitconfig）。 这种情况下，Git 会使用它找到的每一个变量的最后一个配置。你可以通过输入 git config <key>： 来检查 Git 的某一项配置

```bash
$ git config user.name
John Doe
```

#### 获取帮助

```bash
$ git help <verb>
$ git <verb> --help
$ man git-<verb>
#获取config的帮助
$ git help config
```

## 2.git基础

### 2.1获取Git仓库

两种取得Git项目仓库的方法。在现有项目或目录下导入所有文件到Git中；从一个服务器服务器克隆一个现有的Git仓库。

#### 2.1.1在现有目录中初始化仓库

```bash
$ git init
```

该命令将创建一个名为 .git 的子目录，这个子目录含有你初始化的 Git 仓库中所有的必须文件，这些文件是Git 仓库的骨干。 但是，在这个时候，我们仅仅是做了一个初始化的操作，你的项目里的文件还没有被跟踪。

如果你是在一个已经存在文件的文件夹（而不是空文件夹）中初始化 Git 仓库来进行版本控制的话，你应该开始跟踪这些文件并提交。 你可通过 git add 命令来实现对指定文件的跟踪，然后执行 git commit 提交：

```bash
#用空格分开不同的文件
$ git add *.c *.java
$ git add LICENSE
$ git commit -m 'initial project version'
```

#### 2.1.2克隆现有的仓库

```bash
#git clone [url]
$ git clone https://github.com/libgit2/libgit2
$ git clone https://github.com/libgit2/libgit2 mylibgit
```

工作目录下的每一个文件都不外乎两种状态：已跟踪或未跟踪。已跟踪的文件是指那些被纳入了版本控制的文件，在上一次快照中有他们的记录，在工作一段时间后，他们的状态可能处于未修改、已修改或已放入暂存区。工作目录中除已跟踪文件以外的所有其他文件都属于未跟踪文件，他们既不存在于上次快照的记录中，也没有放入暂存区。初次克隆某个仓库的时候，工作目录中的所有文件都属于已跟踪文件，并处于未修改状态。

#### 2.1.3检查当前文件状态

```bash
#查看哪些文件处于什么状态
$ git status
On branch master
nothing to commit, working directory clean

```

On branch master显示当前所在的分支为master。nothing to commit, working directory clean说明当前工作目录很干净，没有做过修改。

#### 2.1.4跟踪文件

```bash
#把未跟踪的和已修改的文件添加到暂存区域
$ git add <文件名称>

```

```bash
$ git status
On branch master
Changes to be committed:
(use "git reset HEAD <file>..." to unstage)
new file: README
modified: CONTRIBUTING.md
Changes not staged for commit:
(use "git add <file>..." to update what will be committed)
(use "git checkout -- <file>..." to discard changes in working
directory)
modified: CONTRIBUTING.md

```

现在 CONTRIBUTING.md 文件同时出现在暂存区和非暂存区。 这怎么可能呢？ 好吧，实际上 Git 只不过暂存了你运行 git add 命令时的版本， 如果你现在提交，CONTRIBUTING.md 的版本是你最后一次运行git add 命令时的那个版本，而不是你运行 git commit 时，在工作目录中的当前版本。 所以，运行了 git add 之后又作了修订的文件，需要重新运行 git add 把最新版本重新暂存起来

如果你使用 git status -s 命令或 git status --short 命令，你将得到一种更为紧凑的格式输出

```bash
$ git status -s
 M README
MM Rakefile
A lib/git.rb
M lib/simplegit.rb
?? LICENSE.txt

```

新添加的未跟踪文件前面有 ?? 标记，新添加到暂存区中的文件前面有 A 标记，修改过的文件前面有 M 标记。 你可能注意到了 M 有两个可以出现的位置，出现在右边的 M 表示该文件被修改了但是还没放入暂存区，出现在靠左边的 M 表示该文件被修改了并放入了暂存区。 例如，上面的状态报告显示： README 文件在工作区被修改了但是还没有将修改后的文件放入暂存区,lib/simplegit.rb 文件被修改了并将修改后的文件放入了暂存区。 而Rakefile 在工作区被修改并提交到暂存区后又在工作区中被修改了，所以在暂存区和工作区都有该文件被修改了的记录。

#### 2.1.5忽略文件

我们可以创建一个名为 .gitignore的文件，列出要忽略的文件模式

```bash
$ cat .gitignore
*.[oa]
*~

```

第一行告诉 Git 忽略所有以 .o 或 .a 结尾的文件。一般这类对象文件和存档文件都是编译过程中出现的。 第二行告诉 Git 忽略所有以波浪符（~）结尾的文件，许多文本编辑软件（比如 Emacs）都用这样的文件名保存副本

文件.gitignore的格式规范如下：

1. 所有空行或者以#开头的行都会被git忽略
2. 可以使用标准的glob模式匹配
3. 匹配模式可以以（/）开头防止递归
4. 匹配模式可以以（/）结尾指定目录
5. 要忽略指定模式以外的文件或目录，可以在模式前加上惊叹号（!）取反

```
所谓的 glob 模式是指 shell 所使用的简化了的正则表达式。 星号（*）匹配零个或多个任意字符；[abc] 匹配任何一个列在方括号中的字符（这个例子要么匹配一个 a，要么匹配一个 b，要么匹配一个 c）；问号（?）只匹配一个任意字符；如果在方括号中使用短划线分隔两个字符，表示所有在这两个字符范围内的都可以匹配（比如 [0-9] 表示匹配所有 0 到 9 的数字）。 使用两个星号（*) 表示匹配任意中间目录，比如 a/**/z 可以匹配 a/z , a/b/z 或 a/b/c/z 等。

```

了解更多https://github.com/github/gitignore

#### 2.1.6查看已暂存和未暂存的修改

你想知道具体修改了什么地方，可以用 git diff 命令，此命令比较的是工作目录中当前文件和暂存区域快照之间的差异， 也就是修改之后还没有暂存起来的变化内容。

若要查看已暂存的将要添加到下次提交里的内容，可以用 git diff --cached 命令。（Git 1.6.1 及更高版本还允许使用 git diff --staged，效果是相同的，但更好记些。）

**使用第三方软件进行比较**(配置未成功)

```bash
git config --global diff.tool bc4
git config --global difftool.bc4.path "E:\\Install\\Beyond Compare 4\\BComp.exe"

git config --global merge.tool bc4
git config --global mergetool.bc4.path "E:\\Install\\Beyond Compare 4\\BComp.exe"

```

#### 2.1.7提交更新

为确保提交前所有的文件都已经存放到暂存区，需要使用git status查看当前工作目录的所有文件的状态，再使用git commit提交文件

```bash
$ git commit
#后面直接添加提交说明
$ git commit -m "Story 182: Fix benchmarks for speed"
[master 463dc4f] Story 182: Fix benchmarks for speed
2 files changed, 2 insertions(+)
#将所有已跟踪的文件暂存起来一并提交，从而跳过git add步骤
$ git commit -a

```

提交后它会告诉你，当前是在哪个分支（**master**）提交的，本次提交的完整 SHA-1 校验和是什么（**463dc4f**），以及在本次提交中，有多少文件修订过，多少行添加和删改过。

提交时记录的是放在暂存区域的快照。 任何还未暂存的仍然保持已修改状态，可以在下次提交时纳入版本管理。 **每一次运行提交操作，都是对你项目作一次快照**，以后可以回到这个状态，或者进行比较

这种方式会启动文本编辑器以便输入本次提交的说明。 (默认会启用 shell 的环境变量 $EDITOR 所指定的软件，一般都是 vim 或 emacs。当然也可以按照 起步 介绍的方式，使用 git config --global core.editor 命令设定你喜欢的编辑软件。）

#### 2.1.8移除文件

git rm之后git commit就可以了

如果删除之前修改过并且已经放到暂存区域的话，则必须要用强制删除选项 -f（译注：即 force 的首字母）。 这是一种安全特性，用于防止误删还没有添加到快照的数据，这样的数据不能被 Git 恢复。

```bash
#我们想把文件从 Git 仓库中删除（亦即从暂存区域移除），但仍然希望保留在当前工作目录中。 换句话说，你想让文件保留在磁盘，但是并不想让 Git 继续跟踪。 当你忘记添加 .gitignore 文件，不小心把一个很大的日志文件或一堆 .a 这样的编译生成文件添加到暂存区时，这一做法尤其有用。 为达到这一目的，使用 --cached 选项：
$ git rm --cached README

```

#### 2.1.9移动文件

```bash
$ git mv file_from file_to

```

相当于运行了三条命令

```bash
$ mv README.md README
$ git rm README.md
$ git add README

```

#### 2.1.10查看提交记录

```bash
$ git log
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date: Mon Mar 17 21:52:11 2008 -0700
changed the version number
commit 085bb3bcb608e1e8451d4b2432f8ecbe6306e7e7
Author: Scott Chacon <schacon@gee-mail.com>
Date: Sat Mar 15 16:40:33 2008 -0700
removed unnecessary test
commit a11bef06a3f659402fe7563abf99ad00de2209e6
Author: Scott Chacon <schacon@gee-mail.com>
Date: Sat Mar 15 10:31:28 2008 -0700
first commit

```

git log 会按提交时间列出所有的更新，最近的更新排在最上面。 正如你所看到的，这个命令会列出每个提交的 SHA-1 校验和、作者的名字和电子邮件地址、提交时间以及提交说明。

git log 有许多选项可以帮助你搜寻你所要找的提交， 接下来我们介绍些最常用的。一个常用的选项是 -p，用来显示每次提交的内容差异。 你也可以加上 -2 来仅显示最近两次提交：

```bash
$ git log --stat -1
commit e1c359fe6ce93463215fddd4d03a2f0c84afef32 (HEAD -> master, origin/master, origin/HEAD)
Author: korov <korov9@163.com>
Date:   Tue Oct 1 15:28:36 2019 +0800

    kip git add test

 "\346\212\200\346\234\257/git.md" | 19 +++++++++++++++++++
 1 file changed, 19 insertions(+)

```

--stat 选项在每次提交的下面列出所有被修改过的文件、有多少文件被修改了以及被修改过的文件的哪些行被移除或是添加了。 在每次提交的最后还有一个总结。

另外一个常用的选项是 --pretty。 这个选项可以指定使用不同于默认格式的方式展示提交历史。 这个选项有一些内建的子选项供你使用。 比如用 oneline 将每个提交放在一行显示，查看的提交数很大时非常有用。 另外还有 short，full 和 fuller 可以用，展示的信息或多或少有些不同，请自己动手实践一下看看效果如何。

```bash
#自定义显示格式
$ git log --pretty=format:"%h - %an, %ar : %s"
ca82a6d - Scott Chacon, 6 years ago : changed the version number
085bb3b - Scott Chacon, 6 years ago : removed unnecessary test
a11bef0 - Scott Chacon, 6 years ago : first commit

```

| 选项 | 说明                                        |
| ---- | ------------------------------------------- |
| %H   | 提交对象（commit）的完整哈希字串            |
| %h   | 提交对象的简短哈希字串                      |
| %T   | 树对象（tree）的完整哈希字串                |
| %t   | 树对象的简短哈希字串                        |
| %P   | 父对象（parent）的完整哈希字串              |
| %p   | 父对象的简短哈希字串                        |
| %an  | 作者（author）的名字                        |
| %ae  | 作者的电子邮件地址                          |
| %ad  | 作者修订日期（可以用 --date= 选项定制格式） |
| %ar  | 作者修订日期，按多久以前的方式显示          |
| %cn  | 提交者（committer）的名字                   |
| %ce  | 提交者的电子邮件地址                        |
| %cd  | 提交日期                                    |
| %cr  | 提交日期，按多久以前的方式显示              |
| %s   | 提交说明                                    |

| 选项            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| -p              | 按补丁格式显示每个更新之间的差异。                           |
| --stat          | 显示每次更新的文件修改统计信息。                             |
| --shortstat     | 只显示 --stat 中最后的行数修改添加移除统计。                 |
| --name-only     | 仅在提交信息后显示已修改的文件清单。                         |
| --name-status   | 显示新增、修改、删除的文件清单。                             |
| --abbrev-commit | 仅显示 SHA-1 的前几个字符，而非所有的 40 个字符。            |
| --relative-date | 使用较短的相对时间显示（比如，“2 weeks ago”）。              |
| --graph         | 显示 ASCII 图形表示的分支合并历史。                          |
| --pretty        | 使用其他格式显示历史提交信息。可用的选项包括 oneline，short，full，fuller 和format（后跟指定格式）。 |

**限制输出长度**

-<n> 选项的写法，其中的 n 可以是任何整数，表示仅显示最近的若干条提交。另外还有按照时间作限制的选项，比如 --since 和 --until 也很有用

还可以给出若干搜索条件，列出符合的提交。 用 --author 选项显示指定作者的提交，用 --grep 选项搜索提交说明中的关键字。 （请注意，如果要得到同时满足这两个选项搜索条件的提交，就必须用 --all-match 选项。否则，满足任意一个条件的提交都会被匹配出来）。另一个非常有用的筛选选项是 -S，可以列出那些添加或移除了某些字符串的提交。 比如说，你想找出添加或移除了某一个特定函数的引用的提交，你可以这样使用：

```bash
$ git log -Sfunction_name

```

| 选项              | 说明                               |
| ----------------- | ---------------------------------- |
| -(n)              | 仅显示最近的 n 条提交              |
| --since, --after  | 仅显示指定时间之后的提交。         |
| --until, --before | 仅显示指定时间之前的提交。         |
| --author          | 仅显示指定作者相关的提交。         |
| --committer       | 仅显示指定提交者相关的提交。       |
| --grep            | 仅显示含指定关键字的提交           |
| -S                | 仅显示添加或移除了某个关键字的提交 |

来看一个实际的例子，如果要查看 Git 仓库中，2008 年 10 月期间，Junio Hamano 提交的但未合并的测试文件，可以用下面的查询命令：

```bash
$ git log --pretty="%h - %s" --author=gitster --since="2008-10-01" \
--before="2008-11-01" --no-merges -- t/
5610e3b - Fix testcase failure when extended attributes are in use
acd3b9e - Enhance hold_lock_file_for_{update,append}() API
f563754 - demonstrate breakage of detached checkout with symbolic link
HEAD
d1a43f2 - reset --hard/read-tree --reset -u: remove unmerged new paths
51a94af - Fix "checkout --track -b newbranch" on detached HEAD
b0ad11e - pull: allow "git pull origin $something:$current_branch" into an
unborn branch

```

#### 2.1.11撤销操作

有时候我们提交完了才发现漏掉了几个文件没有添加，或者提交信息写错了。 此时，可以运行带有 --amend 选项的提交命令尝试重新提交。这个命令会将暂存区中的文件提交。 如果自上次提交以来你还未做任何修改（例如，在上次提交后马上执行了此命令），那么快照会保持不变，而你所修改的只是提交信息。

**取消暂存的文件**

git reset HEAD <file>...将暂存区中的文件移除到未暂存的区域

**撤销对文件的修改**

git checkout -- [file]，这是一个危险的命令，你对这个文件做的任何修改都消失，你只是拷贝了另一个来覆盖它。除非你确实清楚不想要那个文件了，否则不要使用这个命令。

### 2.2远程仓库的使用

#### 2.2.1查看远程仓库

```bash
#列出你指定的每一个远程服务器的简写
$ git remote
origin
```

```bash
#显示需要读写远程仓库使用的 Git 保存的简写与其对应的 URL
$ git remote -v
origin https://github.com/schacon/ticgit (fetch)
origin https://github.com/schacon/ticgit (push)
```

#### 2.2.2添加远程仓库

```bash
$ git remote add pb https://github.com/paulboone/ticgit
$ git remote -v
origin https://github.com/schacon/ticgit (fetch)
origin https://github.com/schacon/ticgit (push)
pb https://github.com/paulboone/ticgit (fetch)
pb https://github.com/paulboone/ticgit (push)
```

#### 2.2.3从远程仓库中抓取与拉取

```bash
# git fetch [remote-name]
$ git fetch origin
# git fetch [remote-name] [branch-name]
$ git fetch origin master
```

这个命令会访问远程仓库，从中拉取所有你还没有的数据。 执行完成后，你将会拥有那个远程仓库中所有分支的引用，可以随时合并或查看。如果你使用 clone 命令克隆了一个仓库，命令会自动将其添加为远程仓库并默认以 “origin” 为简写。 所
以，git fetch origin 会抓取克隆（或上一次抓取）后新推送的所有工作。 必须注意 git fetch 命令会将数据拉取到你的本地仓库 - 它并不会自动合并或修改你当前的工作。 当准备好时你必须手动将其合并入你的工作。

如果你有一个分支设置为跟踪一个远程分支（阅读下一节与 Git 分支 了解更多信息），可以使用 git pull 命令来自动的抓取然后合并远程分支到当前分支。 这对你来说可能是一个更简单或更舒服的工作流程；默认情况下，git clone 命令会自动设置本地 master 分支跟踪克隆的远程仓库的 master 分支（或不管是什么名字的默
认分支）。 运行 git pull 通常会从最初克隆的服务器上抓取数据并自动尝试合并到当前所在的分支。

```bash
# 查看fetch后的文件与本地文件之间的差异
$ git log -p FETCH_HEAD
# 将fetch下来的文件与本地文件进行合并
$ git merge
```



#### 2.2.4推送到远程仓库

```bash
#git push [remote-name] [branch-name]
$ git push origin master

```

只有当你有所克隆服务器的写入权限，并且之前没有人推送过时，这条命令才能生效。 当你和其他人在同一时间克隆，他们先推送到上游然后你再推送到上游，你的推送就会毫无疑问地被拒绝。 你必须先将他们的工作拉取下来并将其合并进你的工作后才能推送。

#### 2.2.5查看远程仓库

```bash
#git remote show [remote-name]
$ git remote show origin
* remote origin
Fetch URL: https://github.com/schacon/ticgit
Push URL: https://github.com/schacon/ticgit
HEAD branch: master
Remote branches:
master tracked
dev-branch tracked
Local branch configured for 'git pull':
master merges with remote master
Local ref configured for 'git push':
master pushes to master (up to date)

```

#### 2.2.6远程仓库的移除与重命名

```bash
#重命名
$ git remote rename pb paul
$ git remote
origin
paul
#删除
$ git remote rm paul
$ git remote
origin

```

### 2.3打标签

git可以给历史中的某一个提交打上标签，以示重要。比较有代表性的是人们会使用这个功能来标记发布节点（v1.0等）。

#### 2.3.1列出标签

```bash
$ git tag
v0.1
v1.3

#只显示1.8.5系列的标签
$ git tag -l 'v1.8.5*'
v1.8.5
v1.8.5-rc0
v1.8.5-rc1
v1.8.5-rc2
v1.8.5-rc3
v1.8.5.1
v1.8.5.2
v1.8.5.3
v1.8.5.4
v1.8.5.5

```

#### 2.3.2创建标签

Git 使用两种主要类型的标签：轻量标签（lightweight）与附注标签（annotated）。

一个轻量标签很像一个不会改变的分支 - 它只是一个特定提交的引用。然而，附注标签是存储在 Git 数据库中的一个完整对象。 它们是可以被校验的；其中包含打标签者的名字、电子邮件地址、日期时间；还有一个标签信息；并且可以使用 GNU Privacy Guard （GPG）签名与验证。 通常建议创建附注标签，这样你可以拥有以上所有信息；但是如果你只是想用一个临时的标签，或者因为某些原因不想要保存那些信息，轻量标签也是可用的。

```bash
#-a说明创建的是一个附注表标签。-m指定了一条将会存储在标签中的信息
$ git tag -a v1.0 -m 'tag test 1.0'
$ git tag
v1.0
#输出显示了打标签者的信息、打标签的日期时间、附注信息，然后显示具体的提交信息
$ git show v1.4
tag v1.4
Tagger: Ben Straub <ben@straub.cc>
Date: Sat May 3 20:19:12 2014 -0700
my version 1.4
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date: Mon Mar 17 21:52:11 2008 -0700
changed the version number

#轻量标签不要使用任何选项只使用tag
$ git tag v1.4-lw
#只会显示出提交信息
$ git show v1.4-lw
commit ca82a6dff817ec66f44342007202690a93763949
Author: Scott Chacon <schacon@gee-mail.com>
Date: Mon Mar 17 21:52:11 2008 -0700
changed the version number

```

后期打标签

```bash
$ git log --pretty=oneline
15027957951b64cf874c3557a0f3547bd83b3ff6 Merge branch 'experiment'
a6b4c97498bd301d84096da251c98a07c7723e65 beginning write support
0d52aaab4479697da7686c15f77a3d64d9165190 one more thing
6d52a271eda8725415634dd79daabbc4d9b6008e Merge branch 'experiment'
0b7434d86859cc7b8c3d5e1dddfed66ff742fcbc added a commit function
4682c3261057305bdd616e23b64b0857d832627b added a todo file
166ae0c4d3f420721acbb115cc33848dfcc2121a started write support
9fceb02d0ae598e95dc970b74767f19372d61af8 updated rakefile
964f16d36dfccde844893cac5b347e7b3d44abbc commit the todo
8a5cbc430f1a9c3d00faaeffd07798508422908a updated readme
#要在那个提交上打标签，你需要在命令的末尾指定提交的校验和（或部分校验和）
$ git tag -a v1.2 9fceb02

```

#### 2.3.3共享标签

git push 命令并不会传送标签到远程仓库服务器上。 在创建完标签后你必须显式地推送标签到共享服务器上。 这个过程就像共享远程分支一样 - 你可以运行 git push origin [tagname]。

```bash
$ git push origin v1.5
#一次性推送很多标签
$ git push origin --tags

```

现在，当其他人从仓库中克隆或拉取，他们也能得到你的那些标签

#### 2.3.4删除标签

```bash
#git tag -d <tagname>
$ git tag -d v1.4-lw
#git push <remote> :refs/tags/<tagname> 同步删除远程仓库标签
$ git push origin :refs/tags/v1.4-lw

```

#### 2.3.5检出标签

如果你想查看某个标签所指向的文件版本，可以使用 git checkout 命令，虽然说这会使你的仓库处于“分离头指针（detacthed HEAD）”状态——这个状态有些不好的副作用：

```bash
$ git checkout 2.0.0
Note: checking out '2.0.0'.
You are in 'detached HEAD' state. You can look around, make experimental
changes and commit them, and you can discard any commits you make in this
state without impacting any branches by performing another checkout.
If you want to create a new branch to retain commits you create, you may
do so (now or later) by using -b with the checkout command again. Example:
git checkout -b <new-branch>
HEAD is now at 99ada87... Merge pull request #89 from schacon/appendixfinal
$ git checkout 2.0-beta-0.1
Previous HEAD position was 99ada87... Merge pull request #89 from
schacon/appendix-final
HEAD is now at df3f601... add atlas.json and cover image

```

在“分离头指针”状态下，如果你做了某些更改然后提交它们，标签不会发生变化，但你的新提交将不属于任何分支，并且将无法访问，除非确切的提交哈希。因此，如果你需要进行更改——比如说你正在修复旧版本的错误——这通常需要创建一个新分支：

```bash
$ git checkout -b version2 v2.0.0
Switched to a new branch 'version2'

```

当然，如果在这之后又进行了一次提交，version2 分支会因为这个改动向前移动，version2 分支就会和v2.0.0 标签稍微有些不同，这时就应该当心了。

### 2.4Git别名

可以通过 git config 文件来轻松地为每一个命令设置一个别名。 这里有一些例子你可以试试：

```bash
$ git config --global alias.co checkout
$ git config --global alias.br branch
$ git config --global alias.ci commit
$ git config --global alias.st status

```

这意味着，当要输入 git commit 时，只需要输入 git ci。

## 3.Git分支

### 3.1分支简介

在进行提交操作时，Git 会保存一个提交对象（commit object）。知道了 Git 保存数据的方式，我们可以很自然的想到——该提交对象会包含一个指向暂存内容快照的指针。 但不仅仅是这样，该提交对象还包含了作者的姓名和邮箱、提交时输入的信息以及指向它的父对象的指针。首次提交产生的提交对象没有父对象，普通提交操作产生的提交对象有一个父对象，而由多个分支合并产生的提交对象有多个父对象，

Git 的分支，其实本质上仅仅是指向提交对象的可变指针。 Git 的默认分支名字是 master。 在多次提交操作之后，你其实已经有一个指向最后那个提交对象的 master 分支。 它会在每次的提交操作中自动向前移动。

```bash
#显示所有的分支
korov@korov-PC:~/git/github/seata$ git branch --all
* 1.0.0     # 左侧的*代表当前所在分支
  develop
  master
  remotes/origin/0.7.1
  remotes/origin/0.8.0
  remotes/origin/0.8.1
  remotes/origin/0.9.0
  remotes/origin/1.0.0
  remotes/origin/HEAD -> origin/develop
  remotes/origin/at_plugin
  remotes/origin/develop
  remotes/origin/feature_spring_support
  remotes/origin/fix_repeated_rollback
  remotes/origin/master
  remotes/origin/multi-primar
```



### 3.2分支创建

```bash
$ git branch testing
```

此时testing分支指向的文件和master指向的文件相同

Git 又是怎么知道当前在哪一个分支上呢？ 也很简单，它有一个名为 HEAD 的特殊指针。请注意它和许多其它版本控制系统（如 Subversion 或 CVS）里的 HEAD 概念完全不同。 在 Git 中，它是一个指针，指向当前所在的本地分支（译注：将 HEAD 想象为当前分支的别名）。 在本例中，你仍然在 master 分支上。 因为 git branch 命令仅仅 创建 一个新分支，并不会自动切换到新分支中去。

```bash
#查看当前HEAD指向的分支
$ git log --oneline --decorate
11e4f51 (HEAD -> master, tag: v1.0, origin/master, origin/HEAD, testing) test
#当前master和testing分支均指向校验和以11e4f51开头的提交对象

```

### 3.3分支切换

```bash
$ git checkout testing
#修改之后使用如下命令将数据push到远端，才会在远端同步创建一个分支
$ git push --set-upstream origin testing
```

### 3.4分支的新建与合并

#### 3.4.1新建分支

```bash
#创建iss53分支并切换到该分支上
$ git checkout -b iss53
#上面的命令相当于这两条命令的简写
$ git branch iss53
$ git checkout iss53
```

将问题修改完成之后将数据push到iss53分支上，切换会master分支然后创建一个新分支去处理紧急问题

```bash
$ git checkout -b hotfix
```

修改完成后将数据push到hotfix，当前状态

![1569953946167](picture\1569953353677.png)

```bash
#切换到master分支
$ git checkout master
#讲hotfix分支上面的修改合并到master分支，merge的作用是将目标分支合并到当前分支中
$ git merge hotfix
#问题修复完成后将hotfix删除之后切回iss53分支继续工作
$ git branch -d hotfix
```

![1569954125196](picture\1569954125196.png)

**分支的合并**

现在将iss53合并到master

这和你之前合并 hotfix 分支的时候看起来有一点不一样。 在这种情况下，你的开发历史从一个更早的地方开始分叉开来（diverged）。 因为，master 分支所在提交并不是 iss53 分支所在提交的直接祖先，Git 不得不做一些额外的工作。 出现这种情况的时候，Git 会使用两个分支的末端所指的快照（C4 和 C5）以及这两个分支的工作祖先（C2），做一个简单的三方合并。

![1569954295920](picture\1569954295920.png)

和之前将分支指针向前推进所不同的是，Git 将此次三方合并的结果做了一个新的快照并且自动创建一个新的提交指向它。 这个被称作一次合并提交，它的特别之处在于他有不止一个父提交。

![1569954330384](picture\1569954330384.png)

#### 3.4.2遇到冲突时的分支合并

```bash
#使用git status查看哪些文件冲突了
$ git status
On branch master
You have unmerged paths.
(fix conflicts and run "git commit")
Unmerged paths:
(use "git add <file>..." to mark resolution)
both modified: index.html
no changes added to commit (use "git add" and/or "git commit -a")
```

任何因包含合并冲突而有待解决的文件，都会以未合并状态标识出来。

```bash
<<<<<<< HEAD:index.html
<div id="footer">contact : email.support@github.com</div>
=======
<div id="footer">
please contact us at support@github.com
</div>
>>>>>>> iss53:index.html
#HEAD 所指示的版本（也就是你的 master 分支所在的位置，因为你在运行 merge 命令的时候已经检出到了这个分支）在这个区段的上半部分（======= 的上半部分），而 iss53 分支所指示的版本在 ======= 的下半部分。 为了解决冲突，你必须选择使用由 =======分割的两部分中的一个，或者你也可以自行合并这些内容。 例如，你可以通过把这段内容换成下面的样子来解决冲突：
<div id="footer">
please contact us at email.support@github.com
</div>
```

在你解决了所有文件里的冲突之后，对每个文件使用 git add 命令来将其标记为冲突已解决。 一旦暂存这些原本有冲突的文件，Git 就会将它们标记为冲突已解决。

--merged 与 --no-merged 这两个有用的选项可以过滤这个列表中已经合并或尚未合并到当前分支的分支。如果要查看哪些分支已经合并到当前分支

### 3.5远程分支

远程引用是对远程仓库的引用（指针），包括分支、标签等等。 你可以通过 git ls-remote (remote) 来显式地获得远程引用的完整列表，或者通过 git remote show (remote) 获得远程分支的更多信息。 然而，一个更常见的做法是利用远程跟踪分支。

远程跟踪分支是远程分支状态的引用。 它们是你不能移动的本地引用，当你做任何网络通信操作时，它们会自动移动。 远程跟踪分支像是你上次连接到远程仓库时，那些分支所处状态的书签。

它们以 (remote)/(branch) 形式命名。 例如，如果你想要看你最后一次与远程仓库 origin 通信时 master分支的状态，你可以查看 origin/master 分支。 你与同事合作解决一个问题并且他们推送了一个 iss53 分支，你可能有自己的本地 iss53 分支；但是在服务器上的分支会指向 origin/iss53 的提交。
这可能有一点儿难以理解，让我们来看一个例子。 假设你的网络里有一个在 git.ourcompany.com 的 Git 服务器。 如果你从这里克隆，Git 的 clone 命令会为你自动将其命名为 origin，拉取它的所有数据，创建一个指向它的 master 分支的指针，并且在本地将其命名为 origin/master。 Git 也会给你一个与 origin 的 master分支在指向同一个地方的本地 master 分支，这样你就有工作的基础。

如果你在本地的 master 分支做了一些工作，然而在同一时间，其他人推送提交到 git.ourcompany.com 并更新了它的 master 分支，那么你的提交历史将向不同的方向前进。 也许，只要你不与 origin 服务器连接，你的 origin/master 指针就不会移动。

如果要同步你的工作，运行 git fetch origin 命令。 这个命令查找 “origin” 是哪一个服务器（在本例中，它是 git.ourcompany.com），从中抓取本地没有的数据，并且更新本地数据库，移动 origin/master指针指向新的、更新后的位置。

为了演示有多个远程仓库与远程分支的情况，我们假定你有另一个内部 Git 服务器，仅用于你的 sprint 小组的开发工作。 这个服务器位于 it.team1.ourcompany.com。 你可以运行 git remote add 命令添加一个新的远程仓库引用到当前的项目，这个命令我们会在 Git 基础 中详细说明。 将这个远程仓库命名为 teamone，将其作为整个 URL 的缩写。

现在，可以运行 git fetch teamone 来抓取远程仓库 teamone 有而本地没有的数据。 因为那台服务器上现有的数据是 origin 服务器上的一个子集，所以 Git 并不会抓取数据而是会设置远程跟踪分支teamone/master 指向 teamone 的 master 分支。

#### 3.5.1推送

如果希望和别人一起在名为 serverfix 的分支上工作，你可以像推送第一个分支那样推送它。 运行 git push (remote) (branch):

```bash
$ git push origin serverfix
Counting objects: 24, done.
Delta compression using up to 8 threads.
Compressing objects: 100% (15/15), done.
Writing objects: 100% (24/24), 1.91 KiB | 0 bytes/s, done.
Total 24 (delta 2), reused 0 (delta 0)
To https://github.com/schacon/simplegit
* [new branch] serverfix -> serverfix
```

这里有些工作被简化了。 Git 自动将 serverfix 分支名字展开为refs/heads/serverfix:refs/heads/serverfix，那意味着，“推送本地的 serverfix分支来更新远程仓库上的 serverfix 分支。”**你也可以运行 git push origin serverfix:serverfix，它会做同样的事 - 相当于它说，“推送本地的 serverfix 分支，将其作为远程仓库的 serverfix 分支” 可以通过这种格式来推送本地分支到一个命名不相同的远程分支。** 如果并不想让远程仓库上的分支叫做 serverfix，可以运行 git push origin serverfix:awesomebranch 来将本地的 serverfix 分支推送到远程仓库上的 awesomebranch分支

下一次其他协作者从服务器上抓取数据时，他们会在本地生成一个远程分支 origin/serverfix，指向服务器的 serverfix 分支的引用。要特别注意的一点是当抓取到新的远程跟踪分支时，本地不会自动生成一份可编辑的副本（拷贝）。 换一句话说，这种情况下，不会有一个新的 serverfix 分支 - 只有一个不可以修改的 origin/serverfix 指针。

```bash
#这会给你一个用于工作的本地分支，并且起点位于 origin/serverfix。
$ git checkout -b serverfix origin/serverfix
Branch serverfix set up to track remote branch serverfix from origin.
Switched to a new branch 'serverfix'
```

#### 3.5.2跟踪分支

从一个远程跟踪分支检出一个本地分支会自动创建所谓的 “跟踪分支””。 跟踪分支是与远程分支有直接关系的本地分支。 如果在一个跟踪分支上输入 git pull，Git 自动地识别去哪个服务器上抓取、合并到那个分支

当克隆一个仓库时，它通常会自动地创建一个跟踪 origin/master 的 master 分支。 然而，如果你愿意的话可以设置其他的跟踪分支 - 其他远程仓库上的跟踪分支，或者不跟踪 master 分支。  这是一个十分常用的操作所以 Git 提供了 --track 快捷方式：

```bash
$ git checkout --track origin/serverfix
Branch serverfix set up to track remote branch serverfix from origin.
Switched to a new branch 'serverfix'
```

```bash
#设置本地分支sf跟踪远程serverfix分支
$ git checkout -b sf origin/serverfix
Branch sf set up to track remote branch serverfix from origin.
Switched to a new branch 'sf'
```

设置已有的本地分支跟踪一个刚刚拉取下来的远程分支，或者想要修改正在跟踪的上游分支，你可以在任意时间使用 -u 或 --set-upstream-to 选项运行 git branch 来显式地设置。

```bash
$ git branch -u origin/serverfix
Branch serverfix set up to track remote branch serverfix from origin.
```

```bash
#如果想要查看设置的所有跟踪分支，可以使用 git branch 的 -vv 选项
$ git branch -vv
iss53 7e424c3 [origin/iss53: ahead 2] forgot the brackets
master 1ae2a45 [origin/master] deploying index fix
* serverfix f8674d9 [teamone/server-fix-good: ahead 3, behind 1] this
should do it
testing 5ea463a trying something new
#这里可以看到 iss53 分支正在跟踪 origin/iss53 并且 “ahead” 是 2，意味着本地有两个提交还没有推送到服务器上。 也能看到 master 分支正在跟踪 origin/master 分支并且是最新的。 接下来可以看到serverfix 分支正在跟踪 teamone 服务器上的 server-fix-good 分支并且领先 3 落后1，意味着服务器上有一次提交还没有合并入同时本地有三次提交还没有推送。 最后看到 testing 分支并没有跟踪任何远程分支。
```

#### 3.5.3拉取

git fetch命令从服务器上抓取本地没有的数据时，它并不会修改工作目录中的内容。它只会获取数据然后让你自己合并。git pull在大多数情况下的含义是一个git fetch紧接着一个git merge命令。git pull会查找当前分支所跟踪的服务器与分支，从服务器上抓取数据然后尝试合并入那个远程分支。

由于git pull的魔法经常令人困惑所以通常单独显示的使用fetch与merge命令更好一些。

#### 3.5.4删除远程分支

```bash
#从服务器上删除serverfix分支
$ git push origin --delete serverfix
To https://github.com/schacon/simplegit
- [deleted] serverfix
```

基本上这个命令做的只是从服务器上移除这个指针。 Git 服务器通常会保留数据一段时间直到垃圾回收运行，所以如果不小心删除掉了，通常是很容易恢复的。

### 3.6变基

git中整合来自不同分支的修改主要有两种方法：merge和rebase。

#### 3.6.1变基的基本操作

之前介绍过，整合分支最容易的方法是 merge 命令。 它会把两个分支的最新快照（C3 和 C4）以及二者最近的共同祖先（C2）进行三方合并，合并的结果是生成一个新的快照（并提交）。

其实，还有一种方法：你可以提取在 C4 中引入的补丁和修改，然后在 C3 的基础上应用一次。 在 Git 中，这种操作就叫做 变基。 你可以使用 rebase 命令将提交到某一分支上的所有修改都移至另一分支上，就好像“重新播放”一样。

```bash
$ git checkout experiment
$ git rebase master
First, rewinding head to replay your work on top of it...
Applying: added staged command
```

它的原理是首先找到这两个分支（即当前分支 experiment、变基操作的目标基底分支 master）的最近共同祖先 C2，然后对比当前分支相对于该祖先的历次提交，提取相应的修改并存为临时文件，然后将当前分支指向目标基底 C3, 最后以此将之前另存为临时文件的修改依序应用。

![1569998340923](picture\1569998340923.png)

```bash
#现在回到 master 分支，进行一次快进合并。
$ git checkout master
$ git merge experiment
```

![1569999512451](picture\1569999512451.png)

merge与rebase两种整合方法的最终结果没有任何区别，但是变基使得提交历史更加整洁。无论是通过变基，还是通过三方合并，整合的最终结果所指向的快照始终是一样的，只不过提交历史不同罢了。变基是将一系列提交按照原有次序依次应用到另一分支上，而合并时把最终结果合在一起。

#### 3.6.2更有趣的变基例子

![1570000224155](picture\1570000224155.png)

假设你希望将 client 中的修改合并到主分支并发布，但暂时并不想合并 server 中的修改，因为它们还需要经过更全面的测试。 这时，你就可以使用 git rebase 命令的 --onto 选项，选中在 client 分支里但不在server 分支里的修改（即 C8 和 C9），将它们在 master 分支上重放：

```bash
#取出 client 分支，找出处于 client 分支和 server 分支的共同祖先之后的修改，然后把它们在 master 分支上重放一遍
$ git rebase --onto master server client
#master进行快速合并
$ git checkout master
$ git merge client
```

![1570000326213](picture\1570000326213.png)

```bash
#git rebase [basebranch] [topicbranch] 将特性分支topicbranch变基到basebranch分支
$ git rebase master server
```

#### 3.6.3变基的风险

使用变基必须遵守一条准则：**不要对在你的仓库外有副本的分支执行变基**

变基操作实质是丢弃一些现有的提交，然后相应的新建一些内容一样但实际上不同的提交。如果你已经将提交推送至某个仓库，而其他人已经从该仓库拉取提交并进行了后续工作，此时，如果你用git rebase命令重新整理了提交并再次推送，你的同伴因此将不得不再次将他们手头的工作与你的提交进行整合，如果接下来还要拉取并整合他们修改过的提交，事情就会变得一团糟。

#### 3.6.4用变基解决变基

如果遇到前面提到的 有人推送了经过变基的提交，并丢弃了你的本地开发所基于的一些提交 那种情境，如果我们不是执行合并，而是执行 git rebase teamone/master, Git 将会：

• 检查哪些提交是我们的分支上独有的（C2，C3，C4，C6，C7）
• 检查其中哪些提交不是合并操作的结果（C2，C3，C4）
• 检查哪些提交在对方覆盖更新时并没有被纳入目标分支（只有 C2 和 C3，因为 C4 其实就是 C4'）
• 把查到的这些提交应用在 teamone/master 上面

在本例中另一种简单的方法是使用 git pull --rebase 命令而不是直接 git pull。 又或者你可以自己手动完成这个过程，先 git fetch，再 git rebase teamone/master。

#### 3.6.5变基vs合并

总的原则是，只对尚未推送或分享给别人的本地修改执行变基操作清理历史，从不对已推送至别处的提交执行变基操作，这样，你才能享受到两种方式带来的便利。



## 4.问题处理

### 4.1 git bash中文被转义

在git bash界面右击Options...->Text 将Locale设置为zh_CN，将Character set设置为UTF-8。

git bash中设置全局变量 `git config --global core.quotepath false` 