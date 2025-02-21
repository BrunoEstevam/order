pipeline {
	agent any

	 tools {
	    jdk 'Java 21'
        maven 'Maven 3.9.9'
     }

	stages {

		stage('Build'){
			steps {
			    sh "java --version"
				sh "mvn clean install -DskipTests"
			}
		}

		stage('Test'){
			steps{
				sh "mvn test"
			}
		}

	}
}