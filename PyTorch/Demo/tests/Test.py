import torch

from log import log

x = torch.rand(5, 3)
log.info(f"version:{torch.__version__}, result:{x}")

arrange = torch.arange(12)
print(f"arrange:{arrange}")
print(f"arrange size:{arrange.shape}")
print(f"arrange number:{arrange.numel()}")
print(f"arrange size:{arrange.size()}")
# 改变向量的形状，改为3行4列的矩阵
result = arrange.reshape(3, 4)
print(f"arrange reshape:{result}")

# 初始化一个2，3，4的向量，所有数据为0
result = torch.zeros((2, 3, 4))
print(f"result:{result}")
result = torch.ones((2, 3, 4))
print(f"result:{result}")

x = torch.arange(1, 3).view(1, 2)
print(x)
y = torch.arange(1, 4).view(3, 1)
print(y)
print(x + y)

# 指定结果到原来的y的内存
x = torch.tensor([1, 2])
y = torch.tensor([3, 4])
id_before = id(y)
y[:] = y + x
print(id(y) == id_before) # True
