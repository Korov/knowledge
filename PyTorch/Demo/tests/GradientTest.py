import torch


if __name__ == "__main__":
    x = torch.ones(2, 2, requires_grad=True)
    print(x)
    print(x.grad_fn)

    y = x + 2
    print(y)
    print(y.grad_fn)

    print(x.is_leaf, y.is_leaf)  # True False

    z = y * y * 3
    out = z.mean()
    print(z, out)

    a = torch.randn(2, 2)  # 缺失情况下默认 requires_grad = False
    a = ((a * 3) / (a - 1))
    print(a.requires_grad)  # False
    a.requires_grad_(True)
    print(a.requires_grad)  # True
    b = (a * a).sum()
    print(b.grad_fn)

    print(x.grad)