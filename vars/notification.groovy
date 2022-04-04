#!/usr/bin/env groovy 
import com.org.log.Logger
import com.org.log.LogLevel
import groovy.transform.Field

@Field logger = new Logger(this, "email", LogLevel.fromString(env.LOG_LEVEL))

def emailPipelineStatus(){
    try{ 
        def templatename = null

        logger.info ('Preparing subject line')
        //sh "echo '${EmailSubject}' > ${config.props.emailsubhtml}" 
        //templatename = config.props.iOSTemplateName
        //logger.info ('Preparing email body ')
        //sh "envsubst < 'template.txt'> 'email.html'"
        //  preparing the the email body
        //logger.info "subject=${EmailSubject}"

        
        //html_body = sh(script: "cat ${config.props.mailbodyhtml}", returnStdout: true).trim()
        //html_subject = sh(script: "cat ${config.props.emailsubhtml}", returnStdout: true).trim()
        def String EmailSubject = "Build " + env.BUILD_NUMBER + "-" + currentBuild.currentResult + "(" + (currentBuild.fullDisplayName) + ")"
        sh "echo '${EmailSubject}' > emailsub.html" 

        def html_body = sh(script: "cat index.html", returnStdout: true).trim()
        html_subject = sh(script: "cat emailsub.html", returnStdout: true).trim()

        emailext attachmentsPattern: 'LTI*.png',
        mimeType: 'text/html',
        body: html_body,
        from: "ltipoctest@gmail.com",
        subject: html_subject,
        to: "vvspraveen28@gmail.com, rautakshay231@gmail.com"


    }catch(emailEx){
        logger.exception(emailEx, "failed to send email")
        throw new Exception("failed to send email: " + emailEx.getMessage())
        continuePipeline = false
    }
} 

