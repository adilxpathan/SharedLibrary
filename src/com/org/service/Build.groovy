package com.hexaware.service
class Build implements Serializable{
Script mainScript
Map specs
Map config

  def Build(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  }
  def buildFunc(Map specs, Map config){
    if (specs.containsKey("build")) {
        if (specs.build.type == "java" && specs.build.tool == "maven") {
          mainScript.sh config.java.build.maven.command 
        }
        else {
        println "unsupported tool. Please use Maven."
            }  
    }
    else {
    println "Skipping build stage as specs are missing."
    throw new Exception("stopping the pipeline since build is skipped.")
        }
      }
    }
