#!/usr/bin/env groovy 
import com.org.log.Logger
import com.org.log.LogLevel
import groovy.transform.Field

@Field logger = new Logger(this, "email", LogLevel.fromString(env.LOG_LEVEL))

def emailPipelineStatus(){
    try{ 
        def templatename = null

        logger.info ('Preparing subject line')
        def String EmailSubject = "Build " + env.BUILD_NUMBER + "-" + currentBuild.currentResult + "(" + (currentBuild.fullDisplayName) + ")"
        //sh "echo '${EmailSubject}' > ${config.props.emailsubhtml}" 
        //templatename = config.props.iOSTemplateName
        //logger.info ('Preparing email body ')
        //sh "envsubst < 'template.txt'> 'email.html'"
        //  preparing the the email body
        //logger.info "subject=${EmailSubject}"

        
        //html_body = sh(script: "cat ${config.props.mailbodyhtml}", returnStdout: true).trim()
        //html_subject = sh(script: "cat ${config.props.emailsubhtml}", returnStdout: true).trim()
        
        
        //emailext attachmentsPattern: 'letsSolve*.png',
        //mimeType: 'text/html',
        //body: '${FILE, path="/resources/com/org/service/email/index.html"}',
        //from: "ltipoctest@gmail.com",
        //subject: 'TEST',
        //to: "ltipoctest@gmail.com"

        emailext 
        body: '''Hello''',
        from: "ltipoctest@gmail.com",
        subject: 'TEST',
        to: "ltipoctest@gmail.com"


    }catch(emailEx){
        logger.exception(emailEx, "failed to send email")
        throw new Exception("failed to send email: " + emailEx.getMessage())
        continuePipeline = false
    }
} 

