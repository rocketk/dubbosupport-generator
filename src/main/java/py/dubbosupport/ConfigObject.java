package py.dubbosupport;

/**
 * Created by pengyu on 2017/6/16.
 */
public class ConfigObject {
    String groupId = null;
    String artifactId = null;
    String version = null;
    String projectName = null;
    String applicationName = null;
    String packageName = null;
    String outputDir = null;
    String providerPort = null;
    String zookeeper = null;
    String nexusUrl = null;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getProviderPort() {
        return providerPort;
    }

    public void setProviderPort(String providerPort) {
        this.providerPort = providerPort;
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public String getNexusUrl() {
        return nexusUrl;
    }

    public void setNexusUrl(String nexusUrl) {
        this.nexusUrl = nexusUrl;
    }
}
