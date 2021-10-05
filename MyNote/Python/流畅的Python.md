# 自我总结

## 创建虚拟环境

```bash
# 在指定文件夹下创建虚拟环境，此处为当前文件夹的venv文件下创建虚拟环境
python -m venv ./venv
# 激活
chmod 744 ./venv/bin/activate
source ./venv/bin/activate
# 取消激活
deactivate

# windows
python -m venv .\venv
.\venv\Scripts\Activate.ps1
deactivate
```

## pip使用

```bash
# 将项目中的依赖写入requirements.txt中
pip freeze > requirements.txt
# 从指定文件中安装依赖
pip install -r requirements.txt
```

## pipenv使用

```bash
# 安装和更新pipenv
pip install pipenv
pip install --user --upgrade pipenv
# 项目初始化
pipenv install
# 安装包  pipenv install requests==2.13.0
pipenv install <package>
# 卸载
pipenv uninstall <package>
# 更新所有需要更新的包
pipenv update --outdated
# 更新所有包
pipenv update
# 更新指定包
pipenv update <package>
# 从requirements.txt导入
pipenv install -r path/to/requirements.txt
# 导出requirements.txt  --dev导出开发用的包
pipenv lock -r --dev
```

## 测试

 一个全功能的测试框架pytest，pip install pytest-html生成html报告，coverage测试代码覆盖率

```bash
pip install pytest
pip install pytest-html
# pytest-cov中依赖coverage
pip install pytest-cov
```

## 日志框架loguru

使用地方框架的日志无法获取自带的日志内容，相比自带的日志框架只能获得很小部分日志，无法获取所有的

安装：`pip install loguru==0.5.3`

最简单的使用就是：`from loguru import logger`，这个只会把日志打印到console，并且是彩色的

配置：

```
from loguru import logger

# 去掉所有的handler，即sys.stderr
logger.remove(handler_id=None)
logger.add('test.log', rotation="100 MB", format="{time:YYYY-MM-DD HH:mm:ss.SSS} {level} {name}:{function}:{line} {message}", level="INFO")
```



## 自带的日志框架

以下配置只会把日志保存到文件中，需要同时输出到console还需要另外配置

```python
import logging

logging.basicConfig(level=logging.INFO, filename="test.log", filemode="a",
                    format="%(asctime)s.%(msecs)03d - %(threadName)s - %(name)s:%(funcName)s - %(levelname)s - %(filename)s:%(lineno)s - %(message)s",
                    datefmt="%Y-%m-%d %H:%M:%S")

logger = logging.getLogger(__name__)

# 结果
2021-10-05 18:48:09.965 - MainThread - kafka.coordinator._send_join_group_request - INFO - base.py:450 - (Re-)joining group test_group1
```

# python版本管理工具

## pyenv

```bash
# 查看当前版本
pyenv version

# 查看所有版本
pyenv versions

# 查看所有可安装的版本
pyenv install --list

# 安装指定版本
pyenv install 3.6.5
# 安装新版本后rehash一下
pyenv rehash

# 删除指定版本
pyenv uninstall 3.5.2

# 指定全局版本
pyenv global 3.6.5

# 指定多个全局版本, 3版本优先
pyenv global 3.6.5 2.7.14

# 实际上当你切换版本后, 相应的pip和包仓库都是会自动切换过去的
```

## 包管理插件pyenv-virtualenv

```bash
# 创建一个3.6.5版本的虚拟环境, 命名为v365env, 然后激活虚拟环境
pyenv virtualenv 3.6.5 v365env
pyenv activate v365env
# 关闭虚拟环境
pyenv deactivate v365env
# 删除虚拟环境
pyenv virtualenv-delete v365env
# 显示所有的虚拟环境
pyenv virtualenvs
```

当切换python解释器的时候对应的pip和包库也会一并切换过去, 而且可以为指定版本的解释器创建项目所需的虚拟环境, 切换的时候也异常简单,  个人常用的做法是为每个项目创建不同的虚拟环境, 当进入该环境的时候就可以随便浪而不用担心影响到其它项目, 搭配Pycharm使用效果更佳.

## 配置

需要将启动文件添加到shell的启动文件中

```bash
export PYENV_ROOT="$HOME/.pyenv"
export PATH="$PYENV_ROOT/bin:$PATH"
eval "$(pyenv init --path)"
eval "$(pyenv virtualenv-init -)"
```

