package rst2odt

import org.gradle.api.Project
import org.gradle.api.Plugin

public class Rst2OdtPlugin implements Plugin<Project> {

	void apply(Project project){
		
		//project.configurations.maybeCreate('pythonpath')
		project.apply plugin:'com.github.rzabini.gradle-jython'
		
		project.dependencies{
			pythonpath project.files(this.class.getProtectionDomain().getCodeSource().location.path)
			pythonpath 'org.apache.sanselan:sanselan:0.97-incubator'
		}

        def zipFile = project.file(this.class.getProtectionDomain().getCodeSource().location.path)
        def outputDir = project.file("${project.buildDir}/odt")

        project.copy {
            from ({project.zipTree(zipFile).files}){
                include '**/rst2odt.odt'
            }
            into outputDir
        }


	}

}