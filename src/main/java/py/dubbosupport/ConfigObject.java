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
    String basePackageName = null;
    String basePackageNamePath = null;
    String outputDir = null;
    String providerPort = null;
    String zookeeper = null;
    String nexusReleaseUrl = null;
    String nexusSnapshotUrl = null;
    String javaVersion = null;
    String dubboVersion = null;
    String springVersion = null;
    String zookeeperVersion = null;
    String nexusRepoId = null;

    public String getDubboVersion() {
        return dubboVersion;
    }

    public void setDubboVersion(String dubboVersion) {
        this.dubboVersion = dubboVersion;
    }

    public String getSpringVersion() {
        return springVersion;
    }

    public void setSpringVersion(String springVersion) {
        this.springVersion = springVersion;
    }

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

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
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

    public String getNexusReleaseUrl() {
        return nexusReleaseUrl;
    }

    public void setNexusReleaseUrl(String nexusReleaseUrl) {
        this.nexusReleaseUrl = nexusReleaseUrl;
    }

    public String getBasePackageNamePath() {
        return basePackageNamePath;
    }

    public void setBasePackageNamePath(String basePackageNamePath) {
        this.basePackageNamePath = basePackageNamePath;
    }

    public String getNexusSnapshotUrl() {
        return nexusSnapshotUrl;
    }

    public void setNexusSnapshotUrl(String nexusSnapshotUrl) {
        this.nexusSnapshotUrl = nexusSnapshotUrl;
    }

    public String getNexusRepoId() {
        return nexusRepoId;
    }

    public void setNexusRepoId(String nexusRepoId) {
        this.nexusRepoId = nexusRepoId;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getZookeeperVersion() {
        return zookeeperVersion;
    }

    public void setZookeeperVersion(String zookeeperVersion) {
        this.zookeeperVersion = zookeeperVersion;
    }
}
