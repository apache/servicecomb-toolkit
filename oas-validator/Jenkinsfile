pipeline {
  agent any

  triggers {
    // 每30分钟检查一下GIT仓库
    pollSCM '*/30 * * * *'
  }

  options {
    // 禁止因Multibranch pipeline index动作触发构建
    overrideIndexTriggers false
    // 禁止并发构建
    disableConcurrentBuilds()
    // 构建结果保留天数10天，最多保留10个
    buildDiscarder logRotator(daysToKeepStr: '30', numToKeepStr: '10')
    // 控制台打出时间戳
    // timestamps()
    // 构建超时设置：15分钟
    timeout(30)
  }

  stages {

    stage('Build & Test') {

      steps {
        withMaven(
          // Jenkins全局工具设置的Maven的名字
          maven: 'Maven3',
          // Jenkins全局工具设置的JDK的名字，有JDK6，JDK7，JDK8可选
          jdk: 'JDK8',
          // 指定maven本地仓库路径为项目working dir下的.local-m2-reop，可以避免因并发构建导致本地仓库混乱的问题
          mavenLocalRepo: '.local-m2-repo'
        ) {
          sh 'mvn -f ./ clean install'
        }
      }

    }


  }

  post {
    always {
      // 发送邮件到本次change所包含的committer
      emailext recipientProviders: [developers()],
        subject: "Pipeline [${currentBuild.fullDisplayName}] built ${currentBuild.currentResult}",
        body: "More details: ${currentBuild.absoluteUrl}"

    }
    cleanup {
      // 清空workspace，节省Jenkins服务器磁盘空间
      echo 'Cleanup workspace'
      withMaven(maven: 'Maven3', jdk: 'JDK8', mavenLocalRepo: '.local-m2-repo', publisherStrategy: 'EXPLICIT') {
        sh 'mvn -f ./ clean'
      }

    }
  }
}
