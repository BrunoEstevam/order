pipeline {
	agent any

	 tools {
        maven 'Maven 3.9.9'
     }

	stages {

		stage('Build'){
			steps {
			    echo java --version
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