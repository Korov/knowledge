import torch
from torch import nn
from torch.nn import init

net = nn.Sequential(nn.Linear(4, 3), nn.ReLU(), nn.Linear(3, 1))  # pytorch已进行默认初始化

print(net)
X = torch.rand(2, 4)
Y = net(X).sum()

print(type(net.named_parameters()))
for name, param in net.named_parameters():
    print(name, param.size())

for name, param in net[0].named_parameters():
    print(name, param.size(), type(param))