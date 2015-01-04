package rst2odt

import jython.JythonTask

import org.gradle.api.tasks.TaskAction

public class Rst2Odt extends JythonTask{

	def stylesheet
	def sourceFile
	def outputFile
	def language
	
	
	void setStylesheet(stylesheet){
		this.stylesheet=project.file(stylesheet).absolutePath
		assert project.file(stylesheet).exists(), "stylesheet $stylesheet not found"

	}
	
	void setSourceFile(sourceFile){
		this.sourceFile=project.file(sourceFile).absolutePath
		assert project.file(sourceFile).exists(), "sourceFile $sourceFile not found"
	}
	
	void setOutputFile(outputFile){
		this.outputFile=project.file(outputFile).absolutePath
		def outputDir=project.file(outputFile).parentFile
		project.mkdir(outputDir)
		assert outputDir.exists(), "output directory $outputDir not found"
	}
	
	void setLanguage(language){
		this.language=language
	}
	
	@TaskAction
	void exec(){
		
		assert stylesheet!=null: "please set stylesheet property on task $name"
		assert sourceFile!=null: "please set sourceFile property on task $name"
		assert outputFile!=null: "please set outputFile property on task $name"
		args '-c', buildOdtCommand, "--stylesheet=$stylesheet", "-l$language", sourceFile, outputFile
		
		super.exec()
	}
	
	static final String buildOdtCommand='''
from docutils.core import publish_cmdline_to_binary
from docutils.writers.odf_odt import Writer, Reader
publish_cmdline_to_binary(reader=Reader(), writer=Writer())
'''
	
}