import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.org.service.Build


class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildJavascriptApp  = null
    //def Map specs = [build : [type: "java", tool: "maven", command: "mvn -version"]]
    //def Map config = [:]
    def setup() {

        buildJavascriptApp = loadPipelineScriptForTest("vars/jenkinsfile")
        buildJavascriptApp.getBinding().setVariable( "build.type", "java" )
    }

    def "[buildJavascriptApp] will run unit test if build.type is java"() {

        when:
            buildJavascriptApp ()
        then:
            1 * getPipelineMock ("buildFunc.call") (_ as Map)
    }

}