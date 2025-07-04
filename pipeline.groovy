  pipeline {
    agent any
  stages {
     stage('git_checkout') {
      steps {
      git branch: 'main', url: 'https://github.com/avigupta63/student-ui-doc.git'

     }
    }
   stage('build') {
   steps {
   sh 'mvn clean package'
   }
   }
   stage('dcoker_push') {
   steps {
   sh '''docker build -t avigupta63/student-ui:latest
docker push avigupta63/student-ui:latest
docker rmi avigupta63/student-ui:latest'''
   }
   }
   stage('deploy') {
   step {
    sh '''

                docker pull avigupta63/student-ui:latest

                docker run -d --name student-ui -p 8081:8080 avigupta63/student-ui:latest
                '''
  }
  }
  }
}