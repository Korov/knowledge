import datetime

from log import log
import torch
from torchvision import transforms, datasets
from torch import nn, optim
import torch.nn.functional as F
import matplotlib.pyplot as plt


class Network(nn.Module):
    def __init__(self):
        super().__init__()
        self.fc1 = nn.Linear(784, 128)
        self.fc2 = nn.Linear(128, 64)
        self.fc3 = nn.Linear(64, 10)

    def forward(self, x):
        x = x.view(x.shape[0], -1)
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = F.log_softmax(self.fc3(x), dim=1)
        return x


if __name__ == "__main__":
    transform = transforms.Compose([transforms.ToTensor(), transforms.Normalize((0.5,), (0.5,))])

    train_set = datasets.MNIST('./trainset', download=True, train=True, transform=transform)
    train_loader = torch.utils.data.DataLoader(train_set, batch_size=64, shuffle=True)

    test_set = datasets.MNIST('./testset', download=True, train=False, transform=transform)
    test_loader = torch.utils.data.DataLoader(test_set, batch_size=64, shuffle=True)

    model = Network()
    criterion = nn.NLLLoss()
    optimizer = optim.SGD(model.parameters(), lr=0.003)

    epochs = 5
    start_time = datetime.datetime.now().microsecond / 1000
    train_losses = []
    log.info("start train")

    for e in range(epochs):
        running_loss = 0
        for images, labels in train_loader:
            output = model(images)
            loss = criterion(output, labels)

            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

            running_loss += loss.item()
        else:
            train_losses.append(running_loss / len(train_loader))

    plt.plot(train_losses, label='Training loss')
    plt.title('Loss Metrics')
    plt.xlabel('Epochs')
    plt.ylabel('Loss')
    plt.legend(frameon=False)
    plt.show()

    end_time = datetime.datetime.now().microsecond / 1000
    log.info(f"end train cost:{end_time - start_time}ms")

    show_count = 5
    correct_count, all_count = 0, 0
    for images, labels in test_loader:
        with torch.no_grad():
            logps = model(images)

        ps = torch.exp(logps)
        top_p, top_class = ps.topk(1, dim=1)

        log.info(labels)
        num_image = images.numpy().squeeze()
        if show_count > 0:
            plt.figure(figsize=(10, 2))
            for j in range(20):  # 打印每个批次前 20 张图片
                plt.subplot(2, 10, j + 1)
                plt.imshow(num_image[j], cmap='gray')
                plt.axis('off')
            plt.show()
            show_count = show_count - 1

        equals = top_class == labels.view(*top_class.shape)

        correct_count += equals.sum().item()
        all_count += test_loader.batch_size
        log.info(f"correct_count:{correct_count}, all_count:{all_count}")

    log.info(f"Number Of Images Tested ={all_count}")
    log.info(f"Model Accuracy ={(correct_count / all_count)}")
