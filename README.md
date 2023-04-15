Ways to do using Python
-----------------------
Use a smaller base image: Choose a smaller base image for your Dockerfile, such as Alpine Linux, which is lightweight and efficient.

Use multi-stage builds: Use multi-stage builds to reduce the size of your final image. This involves using multiple Docker images in your Dockerfile to build your application, then copying only the necessary files to the final image.

Minimize the number of layers: Each line in a Dockerfile creates a new layer, which can increase the size of the image. Try to minimize the number of layers in your Dockerfile by combining multiple commands into a single line.

Remove unnecessary files: Remove any unnecessary files or directories in your Docker image to reduce its size.

----------------------------------------------------------------------------------------------------------------------------------------------------------
