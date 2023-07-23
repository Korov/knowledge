import os

import numpy as np
import pandas as pd
import torch

from log import log

x = torch.tensor([1.0, 2, 4, 8])
y = torch.tensor([2, 2, 2, 2])
log.info(f"{x + y}")
log.info(f"{x - y}")
log.info(f"{x * y}")
log.info(f"{x / y}")
log.info(f"{x ** y}")
log.info(f"{torch.exp(x)}")
log.info(f"{torch.exp(x)}")

os.makedirs(os.path.join("..", "data"), exist_ok=True)
data_file = os.path.join("..", "data", "house_tiny.csv")
with open(data_file, "w") as f:
    f.write("NumRooms,Alley,Price\n")
    f.write("NA,Pave,127500\n")
    f.write("2,NA,106000\n")
    f.write("4,NA,178100\n")
    f.write("NA,NA,140000\n")

data = pd.read_csv(data_file, na_values='NA')

log.info(data.iloc[1:, 0:2])
log.info(data.iloc[:, 0:2])

# 使用插值法来补充缺失值，此处用同一列的均值替换NaN项
# inputs, outputs = data.iloc[:, 0:2], data.iloc[:, 2]
# inputs = inputs.fillna(np.nan)
# log.info(inputs.mean())
# inputs = inputs.fillna(inputs.mean())
# log.info(inputs)
