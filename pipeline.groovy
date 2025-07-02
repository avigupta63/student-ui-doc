pipeline {
    agent any
    stages {
        stage ('git_checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/avigupta63/student-ui-app.git'

            }
            }
            stage ('build') {
                steps {
                    sh '/opt/maven/bin/mvn  clean package'
                }
            }
            stage ('test') {
                steps {
                    withSonarQubeEnv(installationName: 'sonarqube', credentialsId: 'sonar-cred') {
    // some block
                    sh '/opt/maven/bin/mvn sonar:sonar' 
                    }
                }
            }
             stage('Sonar Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                    }
            }
            stage ('deploy') {
                steps {
                    deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: '79fb3a07-3783-4419-86b6-3a79e9fb7a82', path: '', url: 'http://13.59.38.113:8080/')], contextPath: 'studentapp', war: '**/*.war'
                }
            }
        }
    }
