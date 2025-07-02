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
                    sh '''/opt/maven/bin/mvn sonar:sonar \\
  -Dsonar.projectKey=studentapp \\
  -Dsonar.host.url=http://18.226.104.205:9000 \\
  -Dsonar.login=dc92b1d4165982f6fd7d9c260e643594e0fa7a0f'''
                }
            }
            stage ('deploy') {
                steps {
                    echo 'deploy success'
                }
            }
        }
    }
