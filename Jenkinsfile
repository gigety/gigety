pipeline {
  agent none
  stages {
    stage('build') {
      agent any 
      steps {
        
        echo 'build'
        echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
        withCredentials([string(credentialsId: 'DEV_GIGETY_PW', variable: 'DEV_GIGETY_PW')]) {
          sh 'cd gigety-web-api; pwd; whoami; env; echo $PATH; echo $DEV_GIGETY_PW; gradle clean build'
        }
      }
    }
    stage('test') {
      agent any
      steps {
        echo 'test'
      }
    }
    stage('deploy') {
      agent any
      steps {
        echo 'deploy'
        sh ''
      }
    }
  }
}
