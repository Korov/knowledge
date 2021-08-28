from __future__ import print_function

import torch

empty = torch.empty(5, 3)
print("empty:{0}".format(empty))

rand = torch.rand(5, 3)
print("rand:{0}".format(rand))

# 创建一个0填充的矩阵，数据类型为long
x = torch.zeros(5, 3, dtype=torch.long)
print(x)


