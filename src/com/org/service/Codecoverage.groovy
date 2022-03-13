package com.org.service

//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

class Codecoverage implements Serializable{
Script mainScript
Map specs
Map config
Logger logger

  def Codecoverage(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Codecoverage")
  }
  def codecoverageCheckFunc(Map specs, Map config){
    if (specs.containsKey("codeCoverage")) {
      if (specs.codeCoverage.tool == "jacoco") {
        mainScript.sh config.java.codecoverage.jacoco.command 
        mainScript.publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/site/jacoco/', reportFiles: 'index.html', reportName: 'Code Coverage Report', reportTitles: ''])
        } else {
          logger.warn "unsupported tool. Please use jacoco."
        }
      } else {
          logger.warn "Skipping Codecoverage stage as specs are missing."
        }
    }
}
