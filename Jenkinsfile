pipeline {
	agent any

	 tools {
	    jdk 'Java 21'
        maven 'Maven 3.9.9'
     }

    environment {
        BRANCH = env.GIT_BRANCH
    }

	stages {

		stage('build'){
			steps {
			    sh "env.BRANCH"
			    sh "java --version"
				sh "mvn clean install -DskipTests"
			}
		}

		stage('test'){
			steps{
				sh "mvn test"
			}
		}

	}
}