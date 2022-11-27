properties([pipelineTriggers([githubPush()])])

pipeline {
    agent any

    environment {
        GITHUB_TOKEN_SECRET = credentials('gh-token-secret')
        GITHUB_TOKEN = "$GITHUB_TOKEN_SECRET_PSW"
    }

    stages {
        
        stage('release') {
            steps {
        sh "npm install @semantic-release/git -D"
        sh "npm install semantic-release-github -D"
        sh "npm install @semantic-release/exec -D"
        sh  "npm install semantic-release-yaml -D"
        sh "npm install semantic-release/changelog -D"
        sh "npm install @semantic-release-plus/docker -D"
        sh  "npx semantic-release --debug"
            }
        }
    }
}
