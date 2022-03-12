package com.hexaware.service
class Codequality implements Serializable{
Script mainScript
Map specs
Map config

  def Codequality(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  }
  def codequalityFunc(Map specs, Map config){
    mainScript.sh """ mvn sonar:sonar -Dsonar.projectKey=${specs.codeQuality.projectKey} -Dsonar.host.url=${config.java.codequality.sonarqube.url} -Dsonar.login=${config.java.codequality.sonarqube.login} -Dsonar.projectName=${specs.codeQuality.projectName} -Dsonar.organization=${config.java.codequality.sonarqube.organization} """
     }
  
}
