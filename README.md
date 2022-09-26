# AWS Reporter

Toy program to fetch all lambda function versions from AWS.

## 🛠 Built with

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![OpenJDK](https://img.shields.io/badge/OpenJDK-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![EditorConfig](https://img.shields.io/badge/Editor%20Config-E0EFEF?style=for-the-badge&logo=editorconfig&logoColor=000)
![IntelliJ](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

## 🏃‍♀️ Running

1. Install SDKMan!.
   ```shell
   curl -s "https://get.sdkman.io" | bash
   ```
1. Source it.
   ```shell
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```
1. Install JDK and Maven.
   ```shell
   sdk env
   ```
1. Create file with your AWS credentials at `~/.aws/credentials`.
   ```shell
    [default]
    aws_access_key_id = <>
    aws_secret_access_key = <>
   ```
1. Run it.
   ```shell
   mvn compile exec:exec
   ```
1. The report will be available at `./reports/{lambdas,lambda_versions}/<timestamp>.csv`


## TODO
- Logger format.
- picocli.
- jlink.
- JReleaser.
