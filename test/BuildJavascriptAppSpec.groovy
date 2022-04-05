import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.org.service.Build


class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildJavascriptApp = null
    def Map specs = [(build.type): "java", (build.tool): "maven", (build.command): "mvn -version"]
    def Map config = [:]
    def setup() {

        BuildJavascriptAppSpec build = new new com.org.service.Build(this, specs, config)
        //buildJavascriptApp = loadPipelineScriptForTest("vars/jenkinsfile.groovy")
    }

    def "[build] will run unit test if build.type is java"() {
        when:
            build build.type = 'java'
        then:
            1 * getPipelineMock("build.buildFunc.call")(specs, config)
    }

}