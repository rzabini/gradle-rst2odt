/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rst2odt.integration

import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult
import org.joda.time.LocalDateTime

class Rst2odtPluginIntegrationSpec extends IntegrationSpec{

    def setup() {
        setupGradleWrapper()
    }

    def buildscript() {
        def lines = file('.gradle-test-kit/init.gradle').readLines()
        lines.remove(0)

        if (isWindows()) {
            String buildDir = toUnixPath(new File('build').canonicalPath)

            [ 'resources/main', 'classes/main', 'resources/test', 'classes/test' ].each { dir -> lines.add(2, "classpath files('${buildDir}/${dir}')") }
            lines.add(2, "classpath fileTree(dir: '${buildDir}/tmp/expandedArchives', include: 'jacocoagent.jar')")
        }

        lines.remove(lines.size() - 1)
        lines.join(System.getProperty("line.separator")).stripIndent()
    }

    def "can convert rst to odt"(){
        when:
        directory('rst')
        copyResources('rst', 'rst')
        buildFile << buildscript()
        buildFile << """
apply plugin:'com.github.rzabini.gradle-rst2odt'

version 'abc'

task doc(type:rst2odt.Rst2Odt){

stylesheet 'rst/style/masterStyles.odt'
sourceFile 'rst/rest_master.rst'
outputFile "$buildDir/rest_master.odt"
language "it"

    sofficeTrustedLocation $gradle.user
    updateIndex true
    exportPdf true


properties {
        title "User manual"
        logo image('img/logo2.png', scale:'25%')
        author 'John Doe'
        creationdate '---'
        customer "ACME Inc."
        system 'Example'
        lastUpdate 'now'
        docref project.name
        version version
   }

}
        """.stripIndent()

        ExecutionResult result = runTasksSuccessfully('doc')

        then:
        notThrown(Exception)
    }

    boolean isWindows() {
        System.properties['os.name'].toLowerCase().contains('windows')
    }

    def toUnixPath(String path){
        path.replaceAll('\\\\','/')
    }

    def setupGradleWrapper() {
        runTasksSuccessfully(':wrapper')
    }

    def execute(File dir = projectDir, String... args) {
        println "========"
        println "executing ${args.join(' ')}"
        println "--------"
        def lines=[]
        def process = new ProcessBuilder(args)
                .directory(dir)
                .redirectErrorStream(true)
                .start()
        process.inputStream.eachLine {
            lines << it
        }
        def exitValue = process.waitFor()
        if (exitValue != 0) {
            throw new RuntimeException("failed to execute ${args.join(' ')}\nOutput was:\n" + lines.join('\n'))
        }
        return lines
    }

    def runGradleTask(String task){
        execute "${isWindows() ? 'gradlew.bat' : './gradlew'}", task
    }
}
