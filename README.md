# 简介
dubbosupport-generator 是一个用于生成dubbo示例provider的代码生成器，可以自定义项目名称、包名等等信息，生成出来的项目，例如`sample-provider`，可以直接导入Eclipse或Intellij Idea等开发环境，也可以直接利用maven命令来编译生成程序包，如`sample-provider`，这个程序包可以直接放到服务器上运行
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
# empty if you wanna output to current directory
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

