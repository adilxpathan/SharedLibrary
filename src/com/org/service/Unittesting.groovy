package com.org.service

//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

class Unittesting implements Serializable{
Script mainScript
Map specs
Map config
Logger logger

  def Unittesting(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Unittesting")
  }
  def unitTestFunc(Map specs, Map config) {
    if (specs.unitTest.isUnittestRequired && specs.containsKey("unitTest")){
        if (specs.unitTest.tool == 'junit') {
            mainScript.sh config.java.unittest.junit.command 
            mainScript.sh config.java.unittest.junit.surefire
            mainScript.publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/site/', reportFiles: 'surefire-report.html', reportName: 'UnitTest Report', reportTitles: '']) 
          } else {
                logger.warn "unsupported tool. Please use junit."
            } 
    } 
    else {
    logger.warn "Skipping unit test stage because unit Test templates are missing or Unit Test stage is disabled."
        } 
  }
}
