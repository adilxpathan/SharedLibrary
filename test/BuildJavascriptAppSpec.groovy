import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import com.org.service.Build


class BuildJavascriptAppSpec extends JenkinsPipelineSpecification {
    def buildJavascriptApp  = null
    def Map specs = [build: [type: "java", tool: "maven", command: "mvn -version"]]
    def Map config = [:]
    def setup() {

        buildJavascriptApp  = new com.org.service.Build(this, specs, config)
          
    }

    def "[buildJavascriptApp] will run unit test if build.type is java"() {

        when:
            buildJavascriptApp specs.build.type = "java"
        then:
            1 * getPipelineMock ("buildFunc(specs, config)") 
}
    }

}