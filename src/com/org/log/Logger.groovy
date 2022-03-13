package com.org.log

import com.cloudbees.groovy.cps.NonCPS

/*
* This is the concrete implementation for the Log framework 
* It would be instantiated as a stateless regular class in every file whereever needed
* Attributes injected will decide the behaviour of the class
*/

import java.text.SimpleDateFormat
import java.io.StringWriter
import java.io.PrintWriter

class Logger implements Serializable {

    Script script
    String component
    LogLevel logLevel
    LogLevel defaultLogLevel = LogLevel.INFO
    String defaultLogLevelName = defaultLogLevel.toString()
    StringWriter sw

    /*
    * This is the default constructor to instantiate the Logger class 
    * @input script groovy execution context
    * @input component identifier/ unique name of the groovy script
    * @ipnut LogLevel application log level to be set
    */
    Logger(Script script, String component, LogLevel thatLogLevel = LogLevel.INFO){
        this.logLevel = thatLogLevel
        this.script = script
        this.component = component
    }
    /*
    * This method prints the info messages  
    */
    void info(String message) {
       doPrint(message, LogLevel.INFO)
    }
    /*
    * This method prints the warning messages  
    */     
    void warn(String message) {
       doPrint(message, LogLevel.WARN)
    }
    /*
    * This method prints the error messages  
    */       
    void error(String message) {
       doPrint(message, LogLevel.ERROR)
    }
    /*
    * This method prints the trace messages  
    */     
    void trace(String message) {
       doPrint(message, LogLevel.TRACE)
    }
    /*
    * This method prints the debug messages  
    */     
    void debug(String message) {
       doPrint(message, LogLevel.DEBUG)
    }
    /*
    * This method prints the deprecated messages  
    */ 
    void deprecated(String message) {
       doPrint(message, LogLevel.DEPRECATED)
    }
    /*
    * This method prints the fatal error messages
    */   
    void fatal(String message) {
       doPrint(message, LogLevel.FATAL)
    }

    /*
    * This method would print the exception with some customized message as received from the user
    * Depending on the Log Level it will also print the stack trace if the application Log Level is above WARN 
    * @input exception as generated
    * @input message as wanted by the user to be printed along with the error message
    */
    void exception(Exception ex, String message) {

        if(message != null){
            error(message + "-" + ex.getMessage())
        }else{
            error(message + "-" + ex.getMessage())
        }
        //printingExStack(ex.getStackTrace())
    }
    
    
  /*
    * This method would prepare the conent to get ready to print the normal message as requested by the application
    * @input message as wanted by the user to be printed
    * @input thatLogLevel is the log level requested by the user; INFO if not specified
    */
   private void doPrint(message, LogLevel thatLogLevel){
        if(doLog(thatLogLevel)){
            printing(message, thatLogLevel)
        }
    }
    
  /*
    * This method would print the normal message as requested by the application
    * @input message as wanted by the user to be printed
    * @input thatLogLevel is the log level requested by the user; INFO if not specified; 
    *        Unability to initiate the Logger to print the Logs in Level INFO, the errors as encountered, would be captured using level SYSTEM 
    */
    private void printing(String message, LogLevel thatLogLevel = LogLevel.SYSTEM){
        if(!(thatLogLevel.getLevel() == LogLevel.SYSTEM.getLevel() && logLevel.getLevel() < LogLevel.DEBUG.getLevel())){
            //[AFA-6073] Arindam : replaced printf with println
            script.println(getCurrentTime() + wrapColor(thatLogLevel) + "[${component}] ${message}")
        }
    }
        
    /*
    * This method would wrap the normal log message into nice colors
    * @input thatLogLevel is the log level requested by the user; INFO if not specified; 
    */
    private String wrapColor(LogLevel thatLogLevel){
        return " \033["+ thatLogLevel.getColorCode() +"m ["+ thatLogLevel.toString() +"] \033[0m "
    }

    /*
    * This method would print the normal message as requested by the application 
    * by checking whether the requested app user log level is in scope compared to the level as set in the environment
    * @input thatLogLevel is the log level requested by the user; INFO if not specified; 
    */
    private boolean doLog(LogLevel thatLogLevel) {
        LogLevel appLogLevel=getAppLogLevel()
        if (appLogLevel.getLevel() >= thatLogLevel.getLevel()) {
          return true
        }
        return false
     }
    
    /*
    * This method would get the application log level as set by the application in the environemtn variable by reading the specs
    * Default, INFO
    */
    private getAppLogLevel(){
        LogLevel appLogLevel=defaultLogLevel
        try{
            printing("application log level found in script:"+script.env.APP_LOG_LEVEL, LogLevel.SYSTEM)
            appLogLevel=logLevel.fromString(script.env.APP_LOG_LEVEL)
            if(appLogLevel == null){
                throw new Exception("application log level is not valid")
            }
        }catch(Exception logLevelEx){
            appLogLevel=defaultLogLevel
            printing("encountered issue while getting application log level, setting app log level to default [${defaultLogLevelName}]-> "+ logLevelEx.getMessage(), , LogLevel.SYSTEM)
            def sw = new StringWriter()
            logLevelEx.printStackTrace(new PrintWriter(sw))
            printing(sw.toString(), LogLevel.SYSTEM)
        }
        return appLogLevel
    }
    
    private String getCurrentTime(){
        def date = new Date()
        def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        return sdf.format(date)
    }
    
    /*
    * This method prints the exception stack trace 
    */
    private void printingExStack(Exception ex){
        def sw = new StringWriter()
        ex.printStackTrace(new PrintWriter(sw))
        debug(sw.toString())
    }
}

