pipeline {
	agent any

	 tools {
	    jdk 'Java 21'
        maven 'Maven 3.9.9'
     }



	stages {

		stage('build'){
			steps {
			    def branchName = env.GIT_BRANCH ?: ''
                echo "Branch: ${branchName}"

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