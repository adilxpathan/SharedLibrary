//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

def call(){
  node('master') {
    ansiColor('xterm'){
        Logger logger = new Logger(this, "Master-Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
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
                
                env.LOG_LEVEL = "INFO"
                println "WARNING: log level is not set defaulting to: " + env.LOG_LEVEL
                
                env.AppLogLevel = "ERROR" 
                println "WARNING: Application log level is not set defaulting to: " + env.AppLogLevel
                
                withEnv(['APP_LOG_LEVEL=' + env.AppLogLevel ]) {
                    if(specs.build.type == "java")){
                        java_jenkinsfile(specs, config)
                        } else {
                        logger.warn "unsupported application type. As of now we only support the Java application."
                    }
                }
            }   
        }   
        catch(Exception e) {
        logger.error "Error in build stage : " + e.getMessage()
        throw e
        }
        }
    }
  }
