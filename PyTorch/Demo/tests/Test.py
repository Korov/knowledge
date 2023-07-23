import torch

from log import log

x = torch.rand(5, 3)
log.info(f"version:{torch.__version__}, result:{x}")

arrange = torch.arange(12)
print(f"arrange:{arrange}")
print(f"arrange size:{arrange.shape}")
print(f"arrange number:{arrange.numel()}")
# 改变向量的形状，改为3行4列的矩阵
result = arrange.reshape(3, 4)
print(f"arrange reshape:{result}")

# 初始化一个2，3，4的向量，所有数据为0
result = torch.zeros((2, 3, 4))
print(f"result:{result}")
result = torch.ones((2, 3, 4))
print(f"result:{result}")
