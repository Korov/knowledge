import numpy as np
import matplotlib.pylab as plt


def numerical_diff(func, x):
    h = 1e-4 # 另delta=0.0001
    #为避免计算的舍入误差，使用中心差分法计算导数
    return (func(x + h) - func(x - h)) / (2 * h)


def function_1(x):
    return x**2 + x

#切线函数
def tangent_line(func, x):
    k = numerical_diff(func, x)
    print("x = ", x, "时，导数为：", k)
    b = func(x) - k * x
    return lambda t: k * t + b

#返回一个有终点和起点的固定步长(0.1)的数列
x = np.arange(0.0, 20.0, 0.1)
y = function_1(x)

#设置绘图的x和y标签
plt.xlabel("x")
plt.ylabel("f(x)")

tf = tangent_line(function_1, 8)
y2 = tf(x)
#绘制曲线
plt.plot(x, y)
#绘制切线
plt.plot(x, y2)
plt.show()