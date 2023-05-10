# Containerized webapp for deployement on a Kubernetes cluster using a Helm-chart

### The App:

- REST-Api based java application for user accounts for a social tasks software
- mySQL, ElasticSearch and Kafka utility and support


### Container:

- Dockerfile for building a docker container for the app

### Jenkinsfile:

- Jenkins pipeline for creating a dcoker image from repo code and upgrading webapp helm deployment on a kubernetes cluster
*Note: Jenkins server and cluster network should be accessible to each other*

## Usage:

### On local system:

- Pre-requisites:
  - Install and run local elasticsearch server (docker easiest option)
  - Install, configure and run local kafka broker
  - Install, configure and run local mySQL server (to skip configuration, change ddl-mode in application.properties to 'auto')
  - Change values appropriately in application.properties

- To build, run:
```
mvn clean package spring-boot:repackage
```
- Run app:
```
java -jar target/Webapp-0.0.1-SNAPSHOT.jar
```
- Health check available at >localhost:8080/healthy

### On kubernetes cluster (aws)

- Setup kubernetes cluster (Using Terraform and Kops: [repo link](https://github.com/Bh-an/jenkins-ami-build "Kubernetes cluster infra")))
- Add supporting architecture to the cluster (Using preconfigured Helm-Chart: [repo link](https://github.com/Bh-an/jenkins-ami-build "Infrastructure helmchart")) )
- Change application.properties, then build and publish to docker using dockerfile
- Run container in kubernetes cluster (Using Helm to run app in cluster: [repo link](https://github.com/Bh-an/jenkins-ami-build "Webapp helmchart"))
