pipeline {
    agent any

    tools {
        jdk 'jdk-12.0.2'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean test-compile'
            }
        }
        stage('10-programming-fundamentals-java') {
            steps {
                sh 'cd 10-programming-fundamentals-java && mvn -Dmaven.test.failure.ignore=true test'
            }
        }

        stage('20-programming-advanced-java') {
            steps {
                sh 'cd 20-programming-advanced-java && mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
