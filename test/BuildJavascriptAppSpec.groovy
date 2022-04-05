import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.org.service.Build


class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildTest = null
    def Map specs = [(build.type): "java", (build.tool): "maven", (build.command): "mvn -version"]
    def Map config = [:]
    def setup() {

        //BuildJavascriptAppSpec build = new com.org.service.Build(this, specs, config)
        //buildJavascriptApp = loadPipelineScriptForTest("vars/jenkinsfile.groovy")
        def buildTest = loadPipelineScriptForTest("com/org/service/Build.groovy")
    }

    def "[buildTest] will run unit test if build.type is java"() {

        when:
            buildTest.buildFunc (specs, config)
        then:
            1 * getPipelineMock("buildFunc.call")(specs, config)
    }

}