import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;

public class ReduceImageSize {

    public static void main(String[] args) {
        // Create a Docker client
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerClient client = DockerClientBuilder.getInstance(config).build();

        // Build the Docker image
        String imageName = "myimage:latest";
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            public void onNext(BuildResponseItem item) {
                System.out.println(item.getStream());
            }
        };
        File dockerfile = new File("path/to/Dockerfile");
        client.buildImageCmd().withDockerfile(dockerfile).withTag(imageName).exec(callback).awaitImageId();

        // Show the size of the image before compression
        long imageSizeBefore = client.inspectImageCmd(imageName).exec().getSize();
        System.out.println("Image size before compression: " + imageSizeBefore / 1000000 + " MB");

        // Compress the image
        client.pruneCmd().withFilters(new PruneFiltersBuilder().withDangling(true).build()).exec();
        client.saveImageCmd(imageName).withTag(imageName).exec(new PullImageResultCallback()).awaitCompletion();

        // Show the size of the image after compression
        long imageSizeAfter = client.inspectImageCmd(imageName).exec().getSize();
        System.out.println("Image size after compression: " + imageSizeAfter / 1000000 + " MB");

        // Clean up
        client.removeImageCmd(imageName).exec();
    }
}
