#!/usr/bin/env groovy 
import com.org.log.Logger
import com.org.log.LogLevel
import groovy.transform.Field
import com.org.constant.GlobalVars

@Field logger = new Logger(this, "email", LogLevel.fromString(env.LOG_LEVEL))

def initPipelineStatus(){   
    env.SCM_STATUS = GlobalVars.STAGE_SKIPPED_STATUS
    env.SCM_COMMENTS = GlobalVars.STAGE_SKIPPED_COMMENTS
    env.BUILD_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.BUILD_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.UNIT_TEST_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.UNIT_TEST_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.CODE_COVERAGE_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.CODE_COVERAGE_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.CODE_QUALITY_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.CODE_QUALITY_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    
    }

def emailPipelineStatus(){
    try{ 
        def templatename = null

        logger.info ('Preparing subject line')

        sh "envsubst < 'template.txt'> 'index.html'"

        def String EmailSubject = "Build " + env.BUILD_NUMBER + "-" + currentBuild.currentResult + "(" + (currentBuild.fullDisplayName) + ")"
        sh "echo '${EmailSubject}' > emailsub.html" 

        def html_body = sh(script: "cat index.html", returnStdout: true).trim()
        html_subject = sh(script: "cat emailsub.html", returnStdout: true).trim()

        emailext attachmentsPattern: 'LTI*.png',
        mimeType: 'text/html',
        body: html_body,
        from: "ltipoctest@gmail.com",
        subject: html_subject,
        to: "adil.pathan@lntinfotech.com"


    }catch(emailEx){
        logger.exception(emailEx, "failed to send email")
        throw new Exception("failed to send email: " + emailEx.getMessage())
        continuePipeline = false
    }
} 

