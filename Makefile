BASEDIR=$(CURDIR)/src/main/java
OUTPUTDIR=$(CURDIR)/docs# you may change docs with custom folder name
PACKAGE=com# write the package name here
html:
	javadoc -subpackages "$(PACKAGE)" -d "$(OUTPUTDIR)" -encoding UTF-8
.PHONY: html
