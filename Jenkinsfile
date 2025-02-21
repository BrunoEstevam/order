pipeline {
	agent any

	 tools {
        maven 'Maven 3.9.9'  // Nome definido na configuração do Maven
     }

	stages {

		stage('Build'){
			steps {
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