import torch
import torchvision.transforms as transforms
from PIL import Image

# Load the pre-trained model
model = torch.hub.load('pytorch/vision', 'resnet18', pretrained=True)
model.eval()

# Define the image transformation pipeline
transform = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(
        mean=[0.485, 0.456, 0.406],
        std=[0.229, 0.224, 0.225]
    )
])

# Load the image to be classified
image = Image.open("C:\\Users\\korov\\Pictures\\bear.jpg")

# Apply the transformation pipeline to the image
input_tensor = transform(image)
input_batch = input_tensor.unsqueeze(0)

# Move the input batch to the device (GPU or CPU)
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
input_batch = input_batch.to(device)

# Run the input batch through the model
with torch.no_grad():
    output = model(input_batch)

# Get the predicted class by finding the index of the highest score
_, predicted = torch.max(output, 1)

# Print the predicted class label
print(predicted.item())