pipeline {
	agent any

	environment {
		mavenHome = tool 'jenkins-maven'
	}

	stages {

		stage('Build'){
			steps {
				bat "mvn clean install -DskipTests"
			}
		}

		stage('Test'){
			steps{
				bat "mvn test"
			}
		}

	}
}