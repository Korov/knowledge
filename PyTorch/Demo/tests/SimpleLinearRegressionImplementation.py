import random

import matplotlib_inline
import torch
import numpy as np
from matplotlib import pyplot as plt
import torch.utils.data as Data
from torch import nn, optim
from torch.nn import init


class LinearNet(nn.Module):
    def __init__(self, n_feature):
        super(LinearNet, self).__init__()
        self.linear = nn.Linear(n_feature, 1)
    # forward 定义前向传播
    def forward(self, x):
        y = self.linear(x)
        return y

if __name__ == "__main__":
    num_inputs = 2
    num_examples = 1000
    true_w = [2, -3.4]
    true_b = 4.2
    features = torch.tensor(np.random.normal(0, 1, (num_examples, num_inputs)), dtype=torch.float)
    labels = true_w[0] * features[:, 0] + true_w[1] * features[:, 1] + true_b
    labels += torch.tensor(np.random.normal(0, 0.01, size=labels.size()), dtype=torch.float)

    batch_size = 10
    # 将训练数据的特征和标签组合
    dataset = Data.TensorDataset(features, labels)
    # 随机读取小批量
    data_iter = Data.DataLoader(dataset, batch_size, shuffle=True)

    for X, y in data_iter:
        print(X, y)
        break

    net = LinearNet(num_inputs)
    print(net)  # 使用print可以打印出网络的结构

    net = nn.Sequential(
        nn.Linear(num_inputs, 1)
        # 此处还可以传入其他层
    )

    # 查看模型所有的可学习参数
    for param in net.parameters():
        print(param)

    init.normal_(net[0].weight, mean=0, std=0.01)
    init.constant_(net[0].bias, val=0)  # 也可以直接修改bias的data: net[0].bias.data.fill_(0)

    loss = nn.MSELoss()

    optimizer = optim.SGD(net.parameters(), lr=0.03)
    print(optimizer)

    # 调整学习率
    for param_group in optimizer.param_groups:
        param_group['lr'] *= 0.1  # 学习率为之前的0.1倍

    num_epochs = 4
    for epoch in range(1, num_epochs + 1):
        for X, y in data_iter:
            output = net(X)
            l = loss(output, y.view(-1, 1))
            optimizer.zero_grad()  # 梯度清零，等价于net.zero_grad()
            l.backward()
            optimizer.step()
        print('epoch %d, loss: %f' % (epoch, l.item()))

    dense = net[0]
    print(true_w, dense.weight)
    print(true_b, dense.bias)