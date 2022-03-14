//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

def call(Map specs, Map config){
  node('master') {
    Logger logger = new Logger(this, "Java-Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
    try {
     
        stage('Code Checkout'){
            ciFunc.checkoutVarFunc([
            repo: specs.scm.repo,
            branch: specs.scm.branch  
            ])
            }
    stage('Build'){
      ciFunc.build(specs, config)
      }

    if (specs.unitTest.isUnittestRequired && specs.containsKey("unitTest")){
        stage('UnitTest'){
            ciFunc.unittest(specs, config)
        }
    }
      else {
      logger.warn "Skipping unit test stage because unit Test templates are missing or Unit Test stage is disabled."
      }

    if (specs.codeCoverage.isCodecoverageRequired && specs.containsKey("codeCoverage")){  
    stage('CodeCoverage'){
      ciFunc.codecoverage(specs, config)
      }
    } 
    else {
    logger.warn "Skipping code coverage stage because code coverage templates are missing or code coverage stage is disabled." 
      }
      
    if (specs.codeQuality.isCodeQualityRequired && specs.containsKey("codeQuality")){  
    stage('CodeQuality'){
      ciFunc.codequality(specs, config)
      }
    } 
    else {
    logger.warn "Skipping code quality stage because code quality templates are missing or code quality stage is disabled." 
      }  
    }   
    catch(Exception e) {
      logger.error "Error in build stage : " + e.getMessage()
    throw e
      }
    }
  }
