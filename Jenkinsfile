properties([pipelineTriggers([githubPush()])])

pipeline {
    agent any

    environment {
        GITHUB_TOKEN_SECRET = credentials('gh-token-secret')
        GITHUB_TOKEN = "$GITHUB_TOKEN_SECRET_PSW"
        
        imageId = 'csye7125group01/java_image'
        docker_registry = 'docker.io/csye7125group01/java_image'
        docker_creds = credentials('docker_creds')
    }

    stages {
        stage('Clone Web App Repository') {
            steps {
                git([url: 'https://github.com/csye7125-fall2022-group01/webapp.git', branch: 'main', credentialsId: 'gh-token-secret'])
            }
        }

        stage('Build Web App Docker Container') {
            steps {
                sh "docker build --no-cache --force-rm -t ${imageId} ."
            }
        }
        
        stage('Push Web App Docker Container') {
            steps {
                sh'''
                docker login $docker_registry --username $docker_creds_USR --password $docker_creds_PSW
                docker push $imageId
                docker logout
                '''
            }
        }

        stage('Clean Web App Docker Container') {
            steps{
                sh "docker rmi ${imageId}"
            }
        }

        stage('Get Helm Release') {
            steps{
                sh'''
                URL=$(curl \
                -H "Authorization: Bearer $GITHUB_TOKEN" \
                -s https://api.github.com/repos/csye7125-fall2022-group01/helm-chart/releases/latest \
                | jq -r ".assets[] | select(.name | test(\"${spruce_type}\")) | .url")

                curl -LJ $URL \
                -H "Authorization: Bearer $GITHUB_TOKEN" \
                -H 'Accept: application/octet-stream' \
                -o webapp.tgz
                '''
            }
        }

        stage('Helm Release') {
            withKubeConfig([credentialsId: 'jenkins-agent', serverUrl: 'https://api.kops.prod.applicationbhan.me']) {
            sh 'helm list'
    }
        }
    }
}

properties([pipelineTriggers([githubPush()])])

pipeline {
    agent any

    environment {
        GITHUB_TOKEN_SECRET = credentials('gh-token-secret')
        GITHUB_TOKEN = "$GITHUB_TOKEN_SECRET_PSW"
        
        IMAGEID = 'csye7125group01/java_image'
        DOCKER_REGISTRY = 'docker.io/csye7125group01/java_image'
        DOCKER_CREDS = credentials('docker_creds')
    }

    stages {
        stage('Clone Web App Repository') {
            steps {
                git([url: 'https://github.com/csye7125-fall2022-group01/webapp.git', branch: 'main', credentialsId: 'gh-token-secret'])
            }
        }

        stage('Build Web App Docker Container') {
            steps {
                sh "docker build --no-cache --force-rm -t ${IMAGEID} ."
            }
        }
        
        stage('Push Web App Docker Container') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
                sh'''
                docker login --username $USERNAME --password $PASSWORD
                docker push $IMAGEID
                docker logout
                '''
                }
            }
        }

        stage('Clean Web App Docker Container') {
            steps{
                sh "docker rmi ${IMAGEID}"
            }
        }


         stage('Get Helm Release') {

            steps{
                sh'''
                latest_version_url=$(curl -H "Authorization: Bearer $GITHUB_TOKEN" -s https://api.github.com/repos/csye7125-fall2022-group01/helm-chart/releases/latest | grep "zipball_url" | cut -d : -f 2,3 | cut -c 3- | rev | cut -c 3- | rev )
                echo $latest_version_url
            
                curl -X GET $latest_version_url -LO \
                -H "Accept: application/vnd.github+json" \
                -H "Authorization: Bearer $GITHUB_TOKEN"\
                -H "X-GitHub-Api-Version: 2022-11-28"
                mkdir -p webapp
                
                version_number="${latest_version_url##*/}"
                echo $version_number
                unzip $version_number -d webapp
                
                '''
            }
        }

       

        stage('Upgrade cluster Helm Installation') {
            steps{
                withKubeConfig([credentialsId: 'jenkins-agent', serverUrl: 'https://api.kops.prod.applicationbhan.me']) {
                sh 'helm upgrade webapp webapp/csye7125-*/ -i -n apps --create-namespace'
                //sh 'helm uninstall webapp'
                sh 'helm list -n apps'
                sh 'ls'
                sh 'rm -rf webapp'
                }
            }
        }
    }
}
