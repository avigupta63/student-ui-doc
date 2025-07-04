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

        stage('docker_push') {
            steps {
                sh '''
                docker build -t avigupta63/student-ui:latest .
                docker push avigupta63/student-ui:latest
                docker rmi avigupta63/student-ui:latest
                '''
            }
        }

        stage('deploy') {
            steps {
                sh '''
                # Stop and remove previous container if running
                docker rm -f student-ui || true

                # Pull the latest image
                docker pull avigupta63/student-ui:latest

                # Run container on port 8081 -> 8080
                docker run -d --name student-ui -P avigupta63/student-ui:latest
                '''
            }
        }
    }
}
