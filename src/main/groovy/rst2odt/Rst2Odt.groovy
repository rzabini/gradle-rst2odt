package rst2odt

import jython.JythonTask

import org.gradle.api.tasks.TaskAction

public class Rst2Odt extends JythonTask{

	def stylesheet
	def sourceFile
	def outputFile
	def language

    boolean updateIndex=true
    boolean exportPdf=true

    def docProperties=[:]

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

    void setUpdateIndex(updateIndex){
        this.updateIndex=updateIndex
    }

    void setExportPdf(exportPdf){
        this.exportPdf=exportPdf
    }





    String getSubstitutions(){
        docProperties.collect {key, value -> ".. |$key| ${translate(value)}\n\n"}.join()
    }

    def translate(val){

        if (val =~ /\w::/)
            val
        else
        if (val =~ /\.png$/)
            "image:: $val"
            else
                "replace:: $val"
    }

    def properties(Closure closure){
        closure.call()
    }

    def methodMissing(String name, args) {
        docProperties.put(name, args[0])
    }





    @TaskAction
	void exec(){
		
		assert stylesheet!=null: "please set stylesheet property on task $name"
		assert sourceFile!=null: "please set sourceFile property on task $name"
		assert outputFile!=null: "please set outputFile property on task $name"

        def intermediate= new File(project.file(sourceFile).parentFile, project.file(sourceFile).name + '.tmp')
        intermediate.text = getSubstitutions() + project.file(sourceFile).text

		args '-c', buildOdtCommand, "--traceback","--title=$docProperties.title","--stylesheet=$stylesheet", "-l$language", intermediate, outputFile
		
		super.exec()

        updateIndex()

        exportPdf()

	}

    private void exportPdf() {
        if (exportPdf)
            if (project.hasProperty('SOFFICE'))
                project.exec {
                    executable project.property('SOFFICE')
                    args '--invisible', '--headless', '--norestore',
                            '--convert-to', 'pdf', "${outputFile}", '--outdir', project.file(outputFile).parentFile

                }
            else
                print "If you want to export to pdf you need to set the project property SOFFICE"
    }

    private void updateIndex() {
        if (updateIndex)
            if (project.hasProperty('SOFFICE'))
                project.exec {
                    executable project.property('SOFFICE')
                    args '--invisible', '--headless', '--norestore',
                            "${project.buildDir}/odt/rst2odt.odt", "macro://rst2odt/Standard.rst2odt.updateIndex(\"${outputFile}\")"

                }
            else
                print "If you want to automatically update document index you need to set the project property SOFFICE"
    }

    static final String buildOdtCommand='''
from docutils.core import publish_cmdline_to_binary
from docutils.writers.odf_odt import Writer, Reader
publish_cmdline_to_binary(reader=Reader(), writer=Writer())
'''
	
}