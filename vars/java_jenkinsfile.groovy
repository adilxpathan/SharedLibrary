//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

def call(){
  node('master') {
    Logger logger = new Logger(this, "Java-Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
    def specs = [:]
    try {
    stage('Specs Checkout'){
      cleanWs()
      ciFunc.checkoutVarFunc([
      repo: Repo,
      branch: Branch
      ])
        stage('reading GlobalConfig & Specs'){ 
            try {
            logger.info "reading the specs from Specs repository"
            def specsDir = "./$Version"
            logger.debug "specs version" + specsDir
                if(fileExists(specsDir + "/ci_template.yaml")){
                ci_template = readYaml file : specsDir + "/ci_template.yaml"
                specs = specs + ci_template
                logger.debug "reading specs file" + specs
                }
            
                logger.info "reading the global config from resources"
                def request = libraryResource "com/org/service/globalConfig/globalConfig.yaml"
                config = readYaml text: request
                logger.debug "reading config file" + config
                }
            catch(Exception e) {
                logger.error "Error in reading specs file : " + e.getMessage()
            throw e
                }
        }
     
        stage('Code Checkout'){
            ciFunc.checkoutVarFunc([
            repo: specs.scm.repo,
            branch: specs.scm.branch  
            ])
            }
        }   
        stage('Build'){
        ciFunc.build(specs, config)
        }
        
        stage('UnitTest'){
            ciFunc.unittest(specs, config)
        }

        stage('CodeCoverage'){
        ciFunc.codecoverage(specs, config)
        }

        stage('CodeQuality'){
        ciFunc.codequality(specs, config)
        }
    }   
    catch(Exception e) {
      logger.error "Error in build stage : " + e.getMessage()
    throw e
      }
    }
  }
