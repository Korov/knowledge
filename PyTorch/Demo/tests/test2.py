import logging

import torch

x = torch.tensor([1.0, 2, 4, 8])
y = torch.tensor([2, 2, 2, 2])
print(f"{x + y}")
print(f"{x - y}")
print(f"{x * y}")
print(f"{x / y}")
print(f"{x ** y}")
print(f"{torch.exp(x)}")

