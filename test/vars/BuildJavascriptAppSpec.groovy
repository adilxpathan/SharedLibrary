import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildJavascriptApp = null

    def setup() {
        buildJavascriptApp = loadPipelineScriptForTest("vars/jenkinsfile.groovy")
    }

    def "[buildJavascriptApp] will run unit test if build.type is java"() {
        when:
            buildJavascriptApp build.type = 'java'
        then:
            1 * getPipelineMock ( "logger.info" )("buid type is: " + build.type)
    }

}