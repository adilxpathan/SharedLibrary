package com.org.service

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

class Build implements Serializable{
Script mainScript
Map specs
Map config
Logger logger

  def Build(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Build")
  }
  def buildFunc(Map specs, Map config){
    if (specs.containsKey("build")) {
        if (specs.build.type == "java" && specs.build.tool == "maven") {
          if (specs.build.containsKey("command")){
            mainScript.sh specs.build.build.command 
          } else {
            mainScript.sh config.java.build.maven.command 
          }
        }
        else {
        logger.warn "unsupported tool. Please use Maven."
            }  
    }
    else {
    logger.warn "Skipping build stage as specs are missing."
    throw new Exception("stopping the pipeline since build is skipped.")
        }
      }
    }
