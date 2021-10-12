BASEDIR=$(CURDIR)/src/main/java
OUTPUTDIR=$(CURDIR)/docs# you may change docs with custom folder name
PACKAGE=com.severalcircles# write the package name here
html:
	javadoc -subpackages $(PACKAGE) -sourcepath $(BASEDIR) -d $(OUTPUTDIR) -encoding UTF-8
.PHONY: html
