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
```

## pip使用

```bash
# 将项目中的依赖写入requirements.txt中
pip freeze > requirements.txt
# 从指定文件中安装依赖
pip install -r requirements.txt
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

```bash
pip install loguru
```

