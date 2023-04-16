from docker import APIClient

client = APIClient(base_url='unix://var/run/docker.sock')

# Build the Docker image
image, _ = client.build(
    path='./',
    dockerfile='Dockerfile',
    tag='myimage:latest',
    rm=True
)

# Show the size of the image before compression
print(f'Image size before compression: {client.inspect_image(image)["Size"] / 1000000} MB')

# Compress the image
client.prune_images(filters={'dangling': False})

# Show the size of the image after compression
print(f'Image size after compression: {client.inspect_image(image)["Size"] / 1000000} MB')
