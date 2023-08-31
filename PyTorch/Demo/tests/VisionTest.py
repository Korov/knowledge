import torch
import torchvision
import torchvision.transforms as transforms
import matplotlib.pyplot as plt
import time
import sys
import torch.nn.functional as F

from tests.LinearRegressionImplementation import use_svg_display


def get_fashion_mnist_labels(labels):
    text_labels = ['t-shirt', 'trouser', 'pullover', 'dress', 'coat',
                   'sandal', 'shirt', 'sneaker', 'bag', 'ankle boot']
    return [text_labels[int(i)] for i in labels]

def show_fashion_mnist(images, labels):
    use_svg_display()
    # 这里的_表示我们忽略（不使用）的变量
    _, figs = plt.subplots(1, len(images), figsize=(12, 12))
    for f, img, lbl in zip(figs, images, labels):
        f.imshow(img.view((28, 28)).numpy())
        f.set_title(lbl)
        f.axes.get_xaxis().set_visible(False)
        f.axes.get_yaxis().set_visible(False)
    plt.show()

# 在这里，我们创建了一个名为 Net 的新类，它继承了 PyTorch 的 torch.nn.Module 类。所有的神经网络模块都应该继承 torch.nn.Module 类，并使用它的方法和属性。
class Net(torch.nn.Module):
    # 我们定义了网络的层。这个网络有两个卷积层（conv1 和 conv2），接着是三个全连接层（ fc1，fc2 和 fc3）。
    # torch.nn.Conv2d() 创建一个2D卷积层，第一个参数是输入通道的数量，第二个参数是输出通道的数量，第三个参数是卷积核的大小。
    # torch.nn.Linear() 创建一个全连接层，第一个参数是输入的数量，第二个参数是输出的数量。
    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = torch.nn.Conv2d(1, 6, 5)
        self.conv2 = torch.nn.Conv2d(6, 16, 5)
        self.fc1 = torch.nn.Linear(16 * 4 * 4, 120)
        self.fc2 = torch.nn.Linear(120, 84)
        self.fc3 = torch.nn.Linear(84, 10)

    # 输入如何通过网络。这个函数的输入 x 会经过两个卷积层（每层后面接着ReLU激活函数和最大池化），然后被拉平，最后经过三个全连接层（前两层后接着ReLU激活函数）。最后的输出 x 是输入在经过网络后的结果
    def forward(self, x):
        x = F.relu(self.conv1(x))
        x = F.max_pool2d(x, 2)
        x = F.relu(self.conv2(x))
        x = F.max_pool2d(x, 2)
        x = x.view(-1, 16 * 4 * 4)
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x

# 在该例子中，conv3是新添加的卷积层，输入通道为16，输出通道为32，卷积核大小为3。请注意，此处设置的数字是随意设置的，实际应用中需要根据你的数据集和任务的需求进行选择。
# 另外也注意到，我们更改了self.fc1 = torch.nn.Linear(32 * 2 * 2, 120)中的输入数，因为在输入全连接层前，数据已经被三个最大池化层降维了，所以需要对应修改。具体的数值依赖于你的卷积核、步长、池化等参数。
# 这个网络结构的变化只是演示如何添加更多的层，但它可能不一定适合 Fashion MNIST 或其他具体的任务，实际应用时，需要根据实际需求和数据来调整网络架构。
class Net3(torch.nn.Module):
    def __init__(self):
        super(Net3, self).__init__()
        self.conv1 = torch.nn.Conv2d(1, 6, 5)
        self.conv2 = torch.nn.Conv2d(6, 16, 5)
        self.conv3 = torch.nn.Conv2d(16, 32, 3)
        self.fc1 = torch.nn.Linear(32 * 2 * 2, 120)
        self.fc2 = torch.nn.Linear(120, 84)
        self.fc3 = torch.nn.Linear(84, 10)

    def forward(self, x):
        x = F.relu(self.conv1(x))
        x = F.max_pool2d(x, 2)
        x = F.relu(self.conv2(x))
        x = F.max_pool2d(x, 2)
        x = F.relu(self.conv3(x))
        x = F.max_pool2d(x, 2)
        x = x.view(-1, 32 * 2 * 2)  # 注意这里需要对应修改
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x

if __name__ == "__main__":
    transform = transforms.Compose(
        [transforms.ToTensor(),
         transforms.Normalize((0.5,), (0.5,))])
    mnist_train = torchvision.datasets.FashionMNIST(root='./trainset', train=True, download=True, transform=transforms.ToTensor())
    mnist_test = torchvision.datasets.FashionMNIST(root='./testset', train=False, download=True, transform=transforms.ToTensor())
    print(type(mnist_train))
    print(len(mnist_train), len(mnist_test))

    feature, label = mnist_train[0]
    print(feature.shape, label)  # Channel x Height x Width

    X, y = [], []
    for i in range(10):
        X.append(mnist_train[i][0])
        y.append(mnist_train[i][1])
    show_fashion_mnist(X, get_fashion_mnist_labels(y))

    batch_size = 256
    if sys.platform.startswith('win'):
        num_workers = 0  # 0表示不用额外的进程来加速读取数据
    else:
        num_workers = 4
    train_iter = torch.utils.data.DataLoader(mnist_train, batch_size=batch_size, shuffle=True, num_workers=num_workers)
    test_iter = torch.utils.data.DataLoader(mnist_test, batch_size=batch_size, shuffle=False, num_workers=num_workers)

    # 读取一遍训练数据需要的时间
    start = time.time()
    for X, y in train_iter:
        continue
    print('%.2f sec' % (time.time() - start))


    model = Net()

    correct = 0
    total = 0
    with torch.no_grad():
        for data in test_iter:
            images, labels = data
            outputs = model(images)
            _, predicted = torch.max(outputs.data, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item()

    print('Accuracy of the network on the 10000 test images: %d %%' % (100 * correct / total))