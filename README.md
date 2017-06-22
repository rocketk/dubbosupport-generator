# 简介
dubbosupport-generator 是一个用于生成dubbo示例provider的代码生成器，可以自定义项目名称、包名等等信息，生成出来的项目，例如`sample-provider`，可以直接导入Eclipse或Intellij Idea等开发环境，也可以直接利用maven命令来编译生成程序包，如`sample-provider-1.0-full.tar.gz`，这个程序包可以直接放到服务器上运行，后面的章节会详细介绍
# 代码生成器
## 下载并解压
下载release版本程序包，例如`generator-1.0.zip`，解压缩

## 自定义配置文件
进入`generator-1.0`，新建`customConfig.properties`文件

`$ touch customConfig.properties`  
`$ vim customConfig.properties`  
编辑文件，将下面的内容拷贝到文件中  
```properties
groupId=com.your.company
artifactId=sample-provider
version=1.0
projectName=sample-provider
applicationName=sample-provider
basePackageName=com.your.company.sample
# empty if you wanna output the project to the current directory
outputDir=
providerPort=20890
zookeeper=localhost:2181
# maven settings
javaVersion=1.6
dubboVersion=2.5.3
springVersion=4.0.8.RELEASE
zookeeperVersion=3.3.1
# empty if you haven't any nexus repository
nexusReleaseUrl=
nexusSnapshotUrl=
nexusRepoId=
```

根据自己的需求，修改相应的配置项，其中除了`outputDir, nexusReleaseUrl, nexusSnapshotUrl, nexusRepoId`可为空外，其他均为必填项

## 运行
运行`run.sh`(unix/linux/osx)或`run.bat`(windows)

`$ cd generator-1.0`

`$ ./run.sh` or ` .\run.bat`

# 生成的项目
例如`sample-provider`，是一个符合标准maven结构的java工程

## 本地调试
代码生成器生成了一个示例service类`com.your.company.sample.SimpleService`，可以参照此类来编写自己的service类。

你也可以先调试`com.your.company.sample.SimpleService`类来查看项目是否正常。

首先你需要确认`src/main/resources/application.properties`里的配置是否正确，尤其是zookeeper，如果配置成本地的地址，那么你的机器需要安装zookeeper软件并运行起来。  
然后你需要运行`com.your.company.sample.Main`，此类中包含了一个main方法，会将provider服务注册到zookeeper中。  
然后运行`com.your.company.sample.SimpleServiceTest`类中的junit测试用例，这个测试用例会通过`src/test/resources/consumer.xml`来获得你刚刚注册的provider，并完成调用，如果不出意外的话，测试用例会运行成功。


## 由生成的项目通过Maven打包而成的程序包
在生成出来的项目`sample-provider`中，执行`mvn clean package -Dmaven.test.skip=true`命令打包，生成出来有2个包，`sample-provider-1.0-full.tar.gz` 和 `sample-provider-1.0-interface-only.jar`

### 完整包
`sample-provider-1.0-full.tar.gz`，此程序包包含了程序所需的二进制类文件、依赖的jar包、运行脚本，你可以将其上传到服务器上运行，以启动dubbo provider程序。

dir | description
-- | --
classes/ | 用于存放项目的类文件以及配置文件
lib/ | 用于存放依赖的jar文件
bin/ | 用于存放可执行文件
bin/log.sh | 查看日志
bin/start.sh | 启动应用
bin/status.sh | 查看运行状态
bin/stop.sh | 关闭应用

### 接口包
例如`sample-provider-1.0-interface-only.jar`，此包只包含接口类，不包含实现类以及其他无关的类，你可以将其发布到nexus上，或者直接发给需要调用你的dubbo服务的人。

### 发布接口包
如果你在`customConfig.properties`中配置了nexus相关的信息，那么现在你可以使用`src/main/mvn-scripts/deploy-interface-release.bat`或`src/main/mvn-scripts/deploy-interface-snapshot.bat`来发布你的接口包（interface-only）到nexus服务器上，前提是先运行`mvn clean package`打包。

在发布你的接口包到nexus服务器之前，请先确认你是否有相应的权限，如果你不清楚怎么做，可参考下面的建议来配置你的maven settings文件

### 配置Maven Settings文件以添加用户名密码等信息
找到你正在使用的`settings.xml`文件，添加以下信息

```xml
<servers>
    <server>
        <id>your-nexus-repo-id</id>
        <username>admin</username>
        <password>****</password>
    </server>
</servers>
```

在`pom.xml`文件中添加以下信息（注意修改你的nexus-url信息）：
以release库为例，snapshot与release的url通常会不一样
```xml
<distributionManagement>
   <repository>
      <id>your-nexus-repo-id</id>
      <name>Your Nexus Repository</name>
      <url>http://your-nexus-ip:port/nexus/content/repositories/Release</url>
   </repository>
</distributionManagement>
```

