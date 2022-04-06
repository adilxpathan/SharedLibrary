import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.org.service.Build


class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildJavascriptApp  = null
    def Map specs = [build : [type: "java"]]
    def Map config = [:]
    def setup() {

        buildJavascriptApp = loadPipelineScriptForTest("vars/jenkinsfile.groovy")
          
    }

    def "[buildJavascriptApp] will run unit test if build.type is java"() {

        when:
            buildJavascriptApp specs.build.type = "java"
        then:
            1 * getPipelineMock ("logger.info") ("Calling java_jenkinsfile")
    }

}